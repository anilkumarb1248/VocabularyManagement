package com.vocabulary.learning.app.service;

import com.vocabulary.learning.app.entity.ExampleEntity;
import com.vocabulary.learning.app.entity.MeaningEntity;
import com.vocabulary.learning.app.entity.VerbEntity;
import com.vocabulary.learning.app.model.Verb;
import com.vocabulary.learning.app.repository.ExampleRepository;
import com.vocabulary.learning.app.repository.MeaningRepository;
import com.vocabulary.learning.app.repository.VerbRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    public List<Verb> getAllVerbs(String searchType, String searchInput, String sortOrder, String selectedLetter, Integer pageSize) {
        List<Verb> verbList = new ArrayList<>();

        String sortingBy = "baseForm";
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortingBy);
        Pageable pageable;
        if ("All".equalsIgnoreCase(selectedLetter)) {
            pageable = PageRequest.of(0, 5000, sort);
        } else {
            pageable = PageRequest.of(0, pageSize, sort);
        }

        Page<VerbEntity> page = null;
        if (StringUtils.isBlank(searchInput)) {
            page = verbRepository.findByBaseFormLike(selectedLetter + "%", pageable);
        } else {
            if ("starting".equalsIgnoreCase(searchType)) {
                page = verbRepository.findByBaseFormLikeOrPastTenseFormLikeOrPastParticipleFormLike(
                        searchInput + "%", searchInput + "%", searchInput + "%", pageable);
            } else if ("ending".equalsIgnoreCase(searchType)) {
                page = verbRepository.findByBaseFormLikeOrPastTenseFormLikeOrPastParticipleFormLike(
                        "%" + searchInput, "%" + searchInput, "%" + searchInput, pageable);
            } else {
                page = verbRepository.findByBaseFormLikeOrPastTenseFormLikeOrPastParticipleFormLike(
                        "%" + searchInput + "%", "%" + searchInput + "%", "%" + searchInput + "%", pageable);
            }

        }
        if (page != null) {
            List<VerbEntity> verbEntities = page.getContent();
            verbEntities.stream().forEach(entity -> {
                Verb verb = mapToDTO(entity);
                verbList.add(verb);
            });
        }
        return verbList;
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

//        List<ImageEntity> imageEntities = new ArrayList<>();
//        verb.getImages().stream().forEach(image -> {
//            ImageEntity imageEntity = new ImageEntity();
//            imageEntity.setImage(image);
//            imageEntities.add(imageEntity);
//        });
//        if (!CollectionUtils.isEmpty(imageEntities)) {
//            verbEntity.setImages(imageEntities);
//        }

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

    private Verb mapToDTO(VerbEntity entity) {
        Verb verb = new Verb();
        verb.setVerbId(entity.getVerbId());
        verb.setBaseForm(entity.getBaseForm());
        verb.setPastTenseForm(entity.getPastTenseForm());
        verb.setPastParticipleForm(entity.getPastParticipleForm());
        verb.setThirdPersonBaseForm(entity.getThirdPersonBaseForm());
        verb.setProgressiveForm(entity.getProgressiveForm());
        verb.setPhonetics(entity.getPhonetics());

        entity.getMeanings().stream().forEach(meaning -> {
            verb.getMeanings().add(meaning.getMeaning());
        });
//        entity.getImages().stream().forEach(image -> {
//            verb.getImages().add(image.getImage());
//        });

        entity.getExamples().stream().forEach(example -> {
            verb.getExamples().add(example.getExample());
        });
        verb.setCreatedTimeStamp(entity.getCreatedTimeStamp());
        verb.setUpdatedTimeStamp(entity.getUpdatedTimeStamp());
        verb.setLearningStatus(entity.getLearningStatus());
        return verb;
    }
}
