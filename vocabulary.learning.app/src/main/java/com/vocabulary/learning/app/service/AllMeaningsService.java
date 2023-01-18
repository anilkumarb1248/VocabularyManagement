package com.vocabulary.learning.app.service;

import com.vocabulary.learning.app.entity.AllMeaningsEntity;
import com.vocabulary.learning.app.model.AllMeanings;
import com.vocabulary.learning.app.repository.AllMeaningsRepository;
import com.vocabulary.learning.app.request.AllMeaningsRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AllMeaningsService {

    private final AllMeaningsRepository allMeaningsRepository;

    public AllMeaningsService(AllMeaningsRepository allMeaningsRepository) {
        this.allMeaningsRepository = allMeaningsRepository;
    }

    public void createMeaning(AllMeanings allMeanings) {
        AllMeaningsEntity allMeaningsEntity = new AllMeaningsEntity();
        BeanUtils.copyProperties(allMeanings, allMeaningsEntity);
        allMeaningsRepository.save(allMeaningsEntity);
    }

    public void updateMeaning(AllMeanings allMeanings) {
        AllMeaningsEntity allMeaningsEntity = new AllMeaningsEntity();
        BeanUtils.copyProperties(allMeanings, allMeaningsEntity);
        allMeaningsRepository.save(allMeaningsEntity);
    }

    public void deleteMeaning(Integer id) {
        allMeaningsRepository.deleteById(id);
    }

    public List<AllMeanings> getAllMeanings(AllMeaningsRequest request) {

        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortOrder()), "createdTimeStamp");
        Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getPageSize(), sort);

        Page<AllMeaningsEntity> page = allMeaningsRepository.findByMeaningType(request.getMeaningType(), pageable);

        List<AllMeanings> allMeaningsList = new ArrayList<>();
        if (page != null) {
            List<AllMeaningsEntity> allMeaningsEntities = page.getContent();
            if (CollectionUtils.isNotEmpty(allMeaningsEntities)) {
                allMeaningsEntities.stream().forEach(entity -> {
                    AllMeanings allMeanings = new AllMeanings();
                    BeanUtils.copyProperties(entity, allMeanings);
                    allMeaningsList.add(allMeanings);
                });
            }
        }
        return allMeaningsList;
    }
}
