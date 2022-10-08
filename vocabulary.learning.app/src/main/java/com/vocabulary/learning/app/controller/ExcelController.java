package com.vocabulary.learning.app.controller;

import com.vocabulary.learning.app.AppConstants;
import com.vocabulary.learning.app.service.ExcelService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/excel")
@CrossOrigin(origins = "http://localhost:4200")
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
}
