package com.vocabulary.learning.app.controller;

import com.vocabulary.learning.app.AppConstants;
import com.vocabulary.learning.app.service.ExcelService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/excel")
@CrossOrigin(origins = "http://localhost:4200")
public class ExcelController {
    //https://frontbackend.com/spring-boot/spring-boot-angular-download-excel,-pdf-and-csv-file
    //

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);

    private final ExcelService excelService;

    private static String filePath = AppConstants.EXPORT_FOLDER_PATH;
    private static String fileName = AppConstants.EXPORT_VERBS_FILE_NAME;

    @Autowired
    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/verbs")
    @ResponseBody
    public ResponseEntity<Resource> generateExcelFile() {
        try {
            excelService.createExcelFileWithVerbs(null);
            Path file = Paths.get(filePath).resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                Path path = resource.getFile().toPath();
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred while exporting the verbs: ", e);
        }
        return null;
    }
}
