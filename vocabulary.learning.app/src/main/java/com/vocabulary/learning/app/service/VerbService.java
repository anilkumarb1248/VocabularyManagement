package com.vocabulary.learning.app.service;

import com.vocabulary.learning.app.entity.ExampleEntity;
import com.vocabulary.learning.app.entity.MeaningEntity;
import com.vocabulary.learning.app.entity.VerbEntity;
import com.vocabulary.learning.app.enums.ExampleType;
import com.vocabulary.learning.app.model.Verb;
import com.vocabulary.learning.app.repository.VerbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class VerbService {

    private final VerbRepository verbRepository;

    @Autowired
    public VerbService(VerbRepository verbRepository) {
        this.verbRepository = verbRepository;
    }

    public List<Verb> getAllVerbs() {
        List<Verb> verbList = new ArrayList<>();
        List<VerbEntity> verbEntities = verbRepository.findAll();
        verbEntities.stream().forEach(entity -> {
            Verb verb = mapToDTO(entity);
            verbList.add(verb);
        });
        return verbList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertVerbs(List<Verb> verbList) {
        List<VerbEntity> verbEntities = new ArrayList<>();
        verbList.stream().forEach(verb -> {
            verbEntities.add(mapToEntity(verb));
        });

        if (!CollectionUtils.isEmpty(verbEntities)) {
            verbRepository.saveAll(verbEntities);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertVerb(Verb verb) {
        VerbEntity verbEntity =  mapToEntity(verb);
        verbRepository.save(verbEntity);
    }

    public void updateVerb(Integer verbId, Verb verb){
        VerbEntity verbEntity =  mapToEntity(verb);
        verbEntity.setVerbId(verbId);
        verbRepository.save(verbEntity);
    }

    private VerbEntity mapToEntity(Verb verb) {
        VerbEntity verbEntity = new VerbEntity();
        verbEntity.setBaseForm(verb.getBaseForm());
        verbEntity.setPastTenseForm(verb.getPastTenseForm());
        verbEntity.setPastParticipleForm(verb.getPastParticipleForm());
        verbEntity.setThirdPersonBaseForm(verb.getThirdPersonBaseForm());
        verbEntity.setProgressiveForm(verb.getProgressiveForm());
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
            exampleEntity.setExampleType(ExampleType.EXAMPLE);
            exampleEntities.add(exampleEntity);
        });

        verb.getBaseFormExamples().stream().forEach(example -> {
            ExampleEntity exampleEntity = new ExampleEntity();
            exampleEntity.setExample(example);
            exampleEntity.setExampleType(ExampleType.BASE_FORM_EXAMPLE);
            exampleEntities.add(exampleEntity);
        });

        verb.getPastTenseExample().stream().forEach(example -> {
            ExampleEntity exampleEntity = new ExampleEntity();
            exampleEntity.setExample(example);
            exampleEntity.setExampleType(ExampleType.PAST_TENSE_EXAMPLE);
            exampleEntities.add(exampleEntity);
        });

        verb.getPastParticipleFormExample().stream().forEach(example -> {
            ExampleEntity exampleEntity = new ExampleEntity();
            exampleEntity.setExample(example);
            exampleEntity.setExampleType(ExampleType.PAST_PARTICIPLE_EXAMPLE);
            exampleEntities.add(exampleEntity);
        });

        if (!CollectionUtils.isEmpty(exampleEntities)) {
            verbEntity.setExamples(exampleEntities);
        }

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

        entity.getMeanings().stream().forEach(meaning -> {
            verb.getMeanings().add(meaning.getMeaning());
        });
//        entity.getImages().stream().forEach(image -> {
//            verb.getImages().add(image.getImage());
//        });

        entity.getExamples().stream().forEach(example -> {
            switch (example.getExampleType()) {
                case EXAMPLE:
                    verb.getExamples().add(example.getExample());
                    break;
                case BASE_FORM_EXAMPLE:
                    verb.getBaseFormExamples().add(example.getExample());
                    break;
                case PAST_TENSE_EXAMPLE:
                    verb.getPastTenseExample().add(example.getExample());
                    break;
                case PAST_PARTICIPLE_EXAMPLE:
                    verb.getPastParticipleFormExample().add(example.getExample());
                    break;
            }
        });
        verb.setCreatedTimeStamp(entity.getCreatedTimeStamp());
        verb.setUpdatedTimeStamp(entity.getUpdatedTimeStamp());

        return verb;
    }
}
