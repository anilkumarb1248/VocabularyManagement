package com.vocabulary.learning.app.service;

import com.vocabulary.learning.app.entity.VerbExampleEntity;
import com.vocabulary.learning.app.entity.VerbMeaningEntity;
import com.vocabulary.learning.app.entity.VerbEntity;
import com.vocabulary.learning.app.enums.LearningStatus;
import com.vocabulary.learning.app.exception.DuplicateVerbException;
import com.vocabulary.learning.app.model.Verb;
import com.vocabulary.learning.app.request.VerbSearchRequest;
import com.vocabulary.learning.app.repository.VerbExampleRepository;
import com.vocabulary.learning.app.repository.ImageRepository;
import com.vocabulary.learning.app.repository.VerbMeaningRepository;
import com.vocabulary.learning.app.repository.VerbRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VerbService {

    private final VerbRepository verbRepository;
    private final VerbExampleRepository verbExampleRepository;
    private final VerbMeaningRepository verbMeaningRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public VerbService(
            VerbRepository verbRepository,
            VerbExampleRepository verbExampleRepository,
            VerbMeaningRepository verbMeaningRepository,
            ImageRepository imageRepository
    ) {
        this.verbRepository = verbRepository;
        this.verbExampleRepository = verbExampleRepository;
        this.verbMeaningRepository = verbMeaningRepository;
        this.imageRepository = imageRepository;
    }

    public List<Verb> getAllVerbs(VerbSearchRequest request) {
        List<VerbEntity> verbEntities;
        if ("All".equalsIgnoreCase(request.getSelectedLetter()) && StringUtils.isBlank(request.getSearchInput())) {
            verbEntities = findAllVerbs(StringUtils.isNotBlank(request.getSortOrder()) ? request.getSortOrder() : "ASC");
        } else if (StringUtils.isNotBlank(request.getLearningStatus())) {
            verbEntities = findByLearningStatus(request.getLearningStatus());
        } else {
            verbEntities = findAllWithPagination(request);
        }

        List<Verb> verbList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(verbEntities)) {
            verbEntities.stream().forEach(entity -> {
                Verb verb = mapToDTO(entity, request);
                verbList.add(verb);
            });
        }
        return verbList;
    }

    private List<VerbEntity> findAllVerbs(String order) {
        Sort sort = Sort.by(Sort.Direction.fromString(order), "baseForm");
        return verbRepository.findAll(sort);
    }

    private List<VerbEntity> findAllWithPagination(VerbSearchRequest request) {
        String sortingBy = "baseForm";
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortOrder()), sortingBy);
        Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getPageSize(), sort);

        Page<VerbEntity> page = null;
        if (StringUtils.isBlank(request.getSearchInput())) {
            page = verbRepository.findByBaseFormLike(request.getSelectedLetter() + "%", pageable);
        } else {
            if ("starting".equalsIgnoreCase(request.getSearchType())) {
                page = verbRepository.findByBaseFormIgnoreCaseLikeOrPastTenseFormIgnoreCaseLikeOrPastParticipleFormIgnoreCaseLike(
                        request.getSearchInput() + "%", request.getSearchInput() + "%", request.getSearchInput() + "%", pageable);
            } else if ("ending".equalsIgnoreCase(request.getSearchType())) {
                page = verbRepository.findByBaseFormIgnoreCaseLikeOrPastTenseFormIgnoreCaseLikeOrPastParticipleFormIgnoreCaseLike(
                        "%" + request.getSearchInput(), "%" + request.getSearchInput(), "%" + request.getSearchInput(), pageable);
            } else {
                page = verbRepository.findByBaseFormIgnoreCaseLikeOrPastTenseFormIgnoreCaseLikeOrPastParticipleFormIgnoreCaseLike(
                        "%" + request.getSearchInput() + "%", "%" + request.getSearchInput() + "%", "%" + request.getSearchInput() + "%", pageable);
            }
        }
        if (page != null) {
            return page.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    private List<VerbEntity> findByLearningStatus(String status) {
        if ("Completed".equalsIgnoreCase(status)) {
            return verbRepository.findAllByLearningStatus(LearningStatus.COMPLETED);
        } else if ("InProgress".equalsIgnoreCase(status)) {
            return verbRepository.findAllByLearningStatus(LearningStatus.IN_PROGRESS);
        } else {
            return verbRepository.findAllByLearningStatus(LearningStatus.NOT_STARTED);
        }
    }

    public List<Verb> findAllByVerbIds(List<Integer> verbIds, String order) {
        List<Verb> verbList = new ArrayList<>();

        Sort sort = Sort.by(Sort.Direction.fromString(order), "baseForm");
        List<VerbEntity> verbEntities = verbRepository.findAllByVerbIdIn(verbIds, sort);

        if (CollectionUtils.isNotEmpty(verbEntities)) {
            verbEntities.stream().forEach(entity -> {
                Verb verb = mapToDTO(entity, null);
                verbList.add(verb);
            });
        }
        return verbList;
    }

    public Verb getVerbDetails(Integer verbId) {
        Verb verb = null;
        Optional<VerbEntity> optional = verbRepository.findById(verbId);
        if (optional.isPresent()) {
            verb = mapToDTO(optional.get(), null);
        }
        return verb;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertVerbs(List<Verb> verbList) {
        List<VerbEntity> verbEntities = new ArrayList<>();
        verbList.stream().forEach(verb -> {
            if (!isDuplicateVerb(verb, true)) {
                verbEntities.add(mapToVerbEntity(verb, null));
            }
        });
        if (!CollectionUtils.isEmpty(verbEntities)) {
            verbRepository.saveAll(verbEntities);
        }
    }

    private boolean isDuplicateVerb(Verb verb, boolean newEntryFlag) {
        Optional<VerbEntity> optional = verbRepository.findByBaseForm(verb.getBaseForm());
        if (optional.isEmpty()) {
            return false;
        } else {
            if (newEntryFlag) {
                return true;
            } else {
                if (verb.getVerbId() == optional.get().getVerbId()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertVerb(Verb verb) {
        if (!isDuplicateVerb(verb, true)) {
            VerbEntity verbEntity = mapToVerbEntity(verb, null);
            verbRepository.save(verbEntity);
        } else {
            throw new DuplicateVerbException("Duplicate verb with name: " + verb.getBaseForm());
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateVerb(Verb verb) {
        VerbEntity entity = null;
        if (!isDuplicateVerb(verb, false)) {
            Optional<VerbEntity> optionalVerbEntity = verbRepository.findById(verb.getVerbId());
            if (optionalVerbEntity.isPresent()) {
                entity = optionalVerbEntity.get();
                verbMeaningRepository.deleteByVerbId(entity.getVerbId());
                verbExampleRepository.deleteByVerbId(entity.getVerbId());
            }
            entity = mapToVerbEntity(verb, entity);
            verbRepository.save(entity);
        } else {
            throw new DuplicateVerbException("Duplicate verb with name: " + verb.getBaseForm());
        }

    }

    private VerbEntity mapToVerbEntity(Verb verb, VerbEntity entity) {
        VerbEntity verbEntity = entity != null ? entity : new VerbEntity();
        verbEntity.setBaseForm(verb.getBaseForm());
        verbEntity.setPastTenseForm(verb.getPastTenseForm());
        verbEntity.setPastParticipleForm(verb.getPastParticipleForm());
        verbEntity.setThirdPersonBaseForm(verb.getThirdPersonBaseForm());
        verbEntity.setProgressiveForm(verb.getProgressiveForm());
        verbEntity.setPhonetics(verb.getPhonetics());
        List<VerbMeaningEntity> meaningEntities = new ArrayList<>();
        verb.getMeanings().stream().forEach(meaning -> {
            VerbMeaningEntity verbMeaningEntity = new VerbMeaningEntity();
            verbMeaningEntity.setMeaning(meaning);
            meaningEntities.add(verbMeaningEntity);
        });

        if (!CollectionUtils.isEmpty(meaningEntities)) {
            verbEntity.setMeanings(meaningEntities);
        }

        List<VerbExampleEntity> exampleEntities = new ArrayList<>();
        verb.getExamples().stream().forEach(example -> {
            VerbExampleEntity verbExampleEntity = new VerbExampleEntity();
            verbExampleEntity.setExample(example);
            exampleEntities.add(verbExampleEntity);
        });

        if (!CollectionUtils.isEmpty(exampleEntities)) {
            verbEntity.setExamples(exampleEntities);
        }
        verbEntity.setLearningStatus(verb.getLearningStatus());

        return verbEntity;
    }

    private Verb mapToDTO(VerbEntity entity, VerbSearchRequest request) {
        Verb verb = new Verb();
        verb.setVerbId(entity.getVerbId());
        verb.setBaseForm(entity.getBaseForm());
        verb.setPastTenseForm(entity.getPastTenseForm());
        verb.setPastParticipleForm(entity.getPastParticipleForm());
        verb.setThirdPersonBaseForm(entity.getThirdPersonBaseForm());
        verb.setProgressiveForm(entity.getProgressiveForm());
        verb.setPhonetics(entity.getPhonetics());
        verb.setLearningStatus(entity.getLearningStatus());

        if (request == null || !request.isExcludedChildren()) {
            entity.getMeanings().stream().forEach(meaning -> {
                verb.getMeanings().add(meaning.getMeaning());
            });

            entity.getExamples().stream().forEach(example -> {
                verb.getExamples().add(example.getExample());
            });
        }
//        verb.setCreatedTimeStamp(entity.getCreatedTimeStamp());
//        verb.setUpdatedTimeStamp(entity.getUpdatedTimeStamp());

        return verb;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void deleteVerb(Integer verbId) {
        imageRepository.deleteByVerbId(verbId);
        verbRepository.deleteById(verbId);
    }
}
