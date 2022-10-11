package com.vocabulary.learning.app.controller;

import com.vocabulary.learning.app.AppConstants;
import com.vocabulary.learning.app.model.Verb;
import com.vocabulary.learning.app.response.IndividualResponse;
import com.vocabulary.learning.app.response.Status;
import com.vocabulary.learning.app.service.ExcelService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/excel")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://127.0.0.1:4200",
        "http://192.168.0.143:4200",
        "http://192.168.0.1:4200",
        "http://desktop-ghd0jav:4200"
})
public class ExcelController {
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
            XSSFWorkbook workbook = excelService.createVerbsExcelFile(null);
            return exportExcel(workbook, "verbs.xlsx");
        } catch (Exception e) {
            LOGGER.error("Exception occurred while exporting the verbs: ", e);
        }
        return null;
    }

    @GetMapping("/verbs/ids")
    @ResponseBody
    public ResponseEntity<Resource> generateCurrentGridExcel(@RequestParam("ids") List<Integer> ids, @RequestParam("order") String order) {
        try {
            if(StringUtils.isBlank(order)){
                order = "ASC";
            }
            XSSFWorkbook workbook = excelService.createCurrentGridExcel(ids, order);
            return exportExcel(workbook, "currentGridVerbs.xlsx");
        } catch (Exception e) {
            LOGGER.error("Exception occurred while exporting the verbs: ", e);
        }
        return null;
    }

    @GetMapping("/verbs/sample")
    @ResponseBody
    public ResponseEntity<Resource> generateVerbsExcelSample() {
        try {
            List<Verb> verbList = new ArrayList<>();
            Verb verb1 = new Verb();
            verb1.setBaseForm("Go");
            verb1.setPastTenseForm("Went");
            verb1.setPastParticipleForm("Gone");
            verb1.setThirdPersonBaseForm("Goes");
            verb1.setProgressiveForm("Going");
            verb1.setPhonetics("ɡəʊ/");
            verb1.getMeanings().add("move from one place to another or travel");
            verb1.getMeanings().add("leave or depart");
            verb1.getMeanings().add(".......So On");
            verb1.getExamples().add("He went out to the shops");
            verb1.getExamples().add("I really must go");
            verb1.getExamples().add(".......So On");

            Verb verb2 = new Verb();
            verb2.setBaseForm("Eat");
            verb2.setPastTenseForm("Ate");
            verb2.setPastParticipleForm("Eaten");
            verb2.setThirdPersonBaseForm("Eats");
            verb2.setProgressiveForm("Eating");

            Verb verb3 = new Verb();
            verb3.setBaseForm("Sleep");
            verb3.setPastTenseForm("Slept");
            verb3.setPastParticipleForm("Slept");
            verb3.setThirdPersonBaseForm("Sleeps");
            verb3.setProgressiveForm("Sleeping");

            verbList.add(verb1);
            verbList.add(verb2);
            verbList.add(verb3);

            XSSFWorkbook workbook = excelService.createVerbsExcelFile(verbList);
            return exportExcel(workbook, "SampleVerbsFile.xlsx");
        } catch (Exception e) {
            LOGGER.error("Exception occurred while exporting the sample verb excel file: ", e);
        }
        return null;
    }

    private ResponseEntity<Resource> exportExcel(XSSFWorkbook workbook, String fileName){
        ByteArrayOutputStream byteArrayOutputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            if (workbook != null) {
                byteArrayOutputStream = new ByteArrayOutputStream();
                workbook.write(byteArrayOutputStream);
                byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

                InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName)
                        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                        .body(inputStreamResource);
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred while exporting the verbs: ", e);
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
            } catch (Exception e) {
                LOGGER.error("Exception occurred while closing the streams: {}", e.getMessage());
            }
        }
        return null;
    }

    @PostMapping("/verbs/upload")
    public Callable<ResponseEntity<IndividualResponse<Verb>>> uploadVerbsFromExcelFile(@RequestParam("verbsExcelFile") MultipartFile verbsExcelFile) {
        IndividualResponse<Verb> individualResponse = new IndividualResponse<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(verbsExcelFile.getInputStream());
            excelService.readAndInsertVerbsFromExcel(workbook);
            individualResponse.setStatus(Status.SUCCESS);
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while uploading verbs excel file: ", e);
            individualResponse.setStatus(Status.ERROR);
            individualResponse.setMsg(e.getMessage());
            return () -> new ResponseEntity<>(individualResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
