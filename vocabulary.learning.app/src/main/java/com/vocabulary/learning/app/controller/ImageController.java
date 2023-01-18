package com.vocabulary.learning.app.controller;

import com.vocabulary.learning.app.exception.EntityNotFoundException;
import com.vocabulary.learning.app.model.Image;
import com.vocabulary.learning.app.model.Verb;
import com.vocabulary.learning.app.response.IndividualResponse;
import com.vocabulary.learning.app.response.Status;
import com.vocabulary.learning.app.service.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://127.0.0.1:4200",
        "http://192.168.0.143:4200",
        "http://192.168.0.1:4200",
        "http://desktop-ghd0jav:4200"
})
public class ImageController {

    //https://www.javainuse.com/fullstack/imageupload
    //https://www.baeldung.com/java-db-storing-files

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/{verbId}")
    public Callable<ResponseEntity<IndividualResponse<Verb>>> uploadImage(@RequestParam("imageFile") MultipartFile image, @PathVariable Integer verbId) {

        IndividualResponse<Verb> individualResponse = new IndividualResponse<>();
        if (verbId == null) {
            individualResponse.setStatus(Status.BAD_REQUEST);
            individualResponse.setMsg("Verb Id must not be null");
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.BAD_REQUEST);
        }

        try {
//            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            System.out.println("Original Image Byte Size - " + image.getBytes().length);
            imageService.saveImage(image.getOriginalFilename(), compressBytes(image.getBytes()), image.getContentType(), verbId);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while uploading image: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public Image getImage(@RequestParam("verbId") Integer verbId, @RequestParam("imageName") String imageName) throws IOException {

        if (verbId == null && StringUtils.isBlank(imageName)) {
            return null;
        }
        try {
            Image image = imageService.getImage(verbId, imageName);
            if (image != null && image.getImage() != null) {
                image.setImage(decompressBytes(image.getImage()));
                return image;
            }
        } catch (EntityNotFoundException e) {
            LOGGER.error("EntityNotFoundException occurred while loading image: {} ", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Exception occurred while loading image: ", e);
        }
        return null;
    }

    @DeleteMapping("/{verbId}")
    public Callable<ResponseEntity<IndividualResponse<Verb>>> deleteImage(@PathVariable Integer verbId) {
        IndividualResponse<Verb> individualResponse = new IndividualResponse<>();
        try {
            imageService.deleteImage(verbId);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.OK);
        } catch (Exception e) {
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // Un compress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
            LOGGER.error("IOException occurred while retrieving the image: ", ioe);
        } catch (DataFormatException dfe) {
            LOGGER.error("DataFormatException occurred while retrieving the image: ", dfe);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while retrieving the image: ", e);
        }

        return outputStream.toByteArray();
    }

}
