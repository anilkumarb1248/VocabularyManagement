package com.vocabulary.learning.app.service;

import com.vocabulary.learning.app.entity.ExampleEntity;
import com.vocabulary.learning.app.entity.MeaningEntity;
import com.vocabulary.learning.app.entity.VerbEntity;
import com.vocabulary.learning.app.enums.LearningStatus;
import com.vocabulary.learning.app.model.Verb;
import com.vocabulary.learning.app.model.VerbSearchRequest;
import com.vocabulary.learning.app.repository.ExampleRepository;
import com.vocabulary.learning.app.repository.MeaningRepository;
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
    private final ExampleRepository exampleRepository;
    private final MeaningRepository meaningRepository;

    @Autowired
    public VerbService(
            VerbRepository verbRepository,
            ExampleRepository exampleRepository,
            MeaningRepository meaningRepository) {
        this.verbRepository = verbRepository;
        this.exampleRepository = exampleRepository;
        this.meaningRepository = meaningRepository;
    }

    public List<Verb> getAllVerbs(VerbSearchRequest verbSearchRequest) {
        List<VerbEntity> verbEntities = null;
        if ("All".equalsIgnoreCase(verbSearchRequest.getSelectedLetter())) {
            verbEntities = findAllVerbs();
        } else if (StringUtils.isNotBlank(verbSearchRequest.getLearningStatus())) {
            verbEntities = findByLearningStatus(verbSearchRequest.getLearningStatus());
        } else {
            verbEntities = findAllWithPagination(verbSearchRequest);
        }

        List<Verb> verbList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(verbEntities)) {
            verbEntities.stream().forEach(entity -> {
                Verb verb = mapToDTO(entity, verbSearchRequest);
                verbList.add(verb);
            });
        }
        return verbList;
    }

    private List<VerbEntity> findAllVerbs() {
        Sort sort = Sort.by(Sort.Direction.fromString("ASC"), "baseForm");
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
                page = verbRepository.findByBaseFormLikeOrPastTenseFormLikeOrPastParticipleFormLike(
                        request.getSearchInput() + "%", request.getSearchInput() + "%", request.getSearchInput() + "%", pageable);
            } else if ("ending".equalsIgnoreCase(request.getSearchType())) {
                page = verbRepository.findByBaseFormLikeOrPastTenseFormLikeOrPastParticipleFormLike(
                        "%" + request.getSearchInput(), "%" + request.getSearchInput(), "%" + request.getSearchInput(), pageable);
            } else {
                page = verbRepository.findByBaseFormLikeOrPastTenseFormLikeOrPastParticipleFormLike(
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

    public List<Verb> findAllByVerbIds(List<Integer> verbIds, String order){
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

    public Verb getVerbDetails(Integer verbId){
        Verb verb = null;
        Optional<VerbEntity> optional = verbRepository.findById(verbId);
        if(optional.isPresent()){
            verb = mapToDTO(optional.get(), null);
        }
        return verb;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertVerbs(List<Verb> verbList) {
        List<VerbEntity> verbEntities = new ArrayList<>();
        verbList.stream().forEach(verb -> {
            verbEntities.add(mapToVerbEntity(verb, null));
        });

        if (!CollectionUtils.isEmpty(verbEntities)) {
            verbRepository.saveAll(verbEntities);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertVerb(Verb verb) {
        VerbEntity verbEntity = mapToVerbEntity(verb, null);
        verbRepository.save(verbEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateVerb(Verb verb) {

        VerbEntity entity = null;

        Optional<VerbEntity> optionalVerbEntity = verbRepository.findById(verb.getVerbId());
        if (optionalVerbEntity.isPresent()) {
            entity = optionalVerbEntity.get();
            meaningRepository.deleteByVerbId(entity.getVerbId());
            exampleRepository.deleteByVerbId(entity.getVerbId());
        }

        entity = mapToVerbEntity(verb, entity);
        verbRepository.save(entity);
    }

    private VerbEntity mapToVerbEntity(Verb verb, VerbEntity entity) {
        VerbEntity verbEntity = entity != null ? entity : new VerbEntity();
        verbEntity.setBaseForm(verb.getBaseForm());
        verbEntity.setPastTenseForm(verb.getPastTenseForm());
        verbEntity.setPastParticipleForm(verb.getPastParticipleForm());
        verbEntity.setThirdPersonBaseForm(verb.getThirdPersonBaseForm());
        verbEntity.setProgressiveForm(verb.getProgressiveForm());
        verbEntity.setPhonetics(verb.getPhonetics());
        List<MeaningEntity> meaningEntities = new ArrayList<>();
        verb.getMeanings().stream().forEach(meaning -> {
            MeaningEntity meaningEntity = new MeaningEntity();
            meaningEntity.setMeaning(meaning);
            meaningEntities.add(meaningEntity);
        });

        if (!CollectionUtils.isEmpty(meaningEntities)) {
            verbEntity.setMeanings(meaningEntities);
        }

        List<ExampleEntity> exampleEntities = new ArrayList<>();
        verb.getExamples().stream().forEach(example -> {
            ExampleEntity exampleEntity = new ExampleEntity();
            exampleEntity.setExample(example);
            exampleEntities.add(exampleEntity);
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

        if(request == null || !request.isExcludedChildren()){
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
}
