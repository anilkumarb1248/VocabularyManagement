package com.vocabulary.learning.app.service;

import com.vocabulary.learning.app.entity.ImageEntity;
import com.vocabulary.learning.app.exception.EntityNotFoundException;
import com.vocabulary.learning.app.model.Image;
import com.vocabulary.learning.app.repository.ImageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(String name, byte[] image, String type, Integer verbId){
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setName(name);
        imageEntity.setImage(image);
        imageEntity.setType(type);
        imageEntity.setVerbId(verbId);
        imageRepository.save(imageEntity);
    }

    public Image getImage(Integer verbId, String imageName) throws EntityNotFoundException {

        Optional<ImageEntity> optionalImage = Optional.empty();
        if(StringUtils.isNotBlank(imageName)){
            optionalImage = imageRepository.findByName(imageName);
        }else if(verbId != null){
            optionalImage = imageRepository.findById(verbId);
        }
        if(!optionalImage.isPresent()){
            throw new EntityNotFoundException("ImageEntity", "getImage", Map.of("verbId", verbId, "imageName", imageName));
        }

        ImageEntity imageEntity = optionalImage.get();
        Image image = new Image();
        image.setImageId(imageEntity.getImageId());
        image.setName(imageEntity.getName());
        image.setImage(imageEntity.getImage());
        image.setType(imageEntity.getType());
        image.setVerbId(imageEntity.getVerbId());
        return image;
    }
}
