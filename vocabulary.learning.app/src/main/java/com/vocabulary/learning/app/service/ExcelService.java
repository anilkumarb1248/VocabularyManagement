package com.vocabulary.learning.app.service;

import com.vocabulary.learning.app.AppConstants;
import com.vocabulary.learning.app.model.Verb;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    private static String filePath = AppConstants.EXPORT_FOLDER_PATH;
    private static String fileName = AppConstants.EXPORT_VERBS_FILE_NAME;

    private String fullFilePath;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelService.class);
    private final VerbService verbService;

    public ExcelService(VerbService verbService){
        this.verbService = verbService;
        fullFilePath = filePath +"\\"+ fileName;
    }

    public void createExcelFileWithVerbs(List<Verb> verbList) {

        if(CollectionUtils.isEmpty(verbList)){
            verbList = verbService.getAllVerbs(null,null, "ASC", "All", 0);
        }
        if(CollectionUtils.isEmpty(verbList)){
            throw new RuntimeException("No verbs found to export");
        }

        FileOutputStream fileOutputStream = null;
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Verbs List");

            HSSFRow rowheader = sheet.createRow((short) 0);
            rowheader.createCell(0).setCellValue("S.No.");
            rowheader.createCell(1).setCellValue("Base Form");
            rowheader.createCell(2).setCellValue("Past Tense Form");
            rowheader.createCell(3).setCellValue("Past Participle Form");
            rowheader.createCell(4).setCellValue("Third Person Form");
            rowheader.createCell(5).setCellValue("Progressing Form");


            int serialNumber = 1;
            int rowNumber = 1;
            for (int counter = 0; counter < verbList.size(); counter++) {
                Verb verb = verbList.get(counter);

                HSSFRow verbRow = sheet.createRow((short) (rowNumber++));
                HSSFRow meaningsRow = sheet.createRow((short) (rowNumber++));
                HSSFRow examplesRow = sheet.createRow((short) (rowNumber++));

                verbRow.createCell(0).setCellValue(serialNumber++);
                verbRow.createCell(1).setCellValue(verb.getBaseForm());
                verbRow.createCell(2).setCellValue(verb.getPastTenseForm());
                verbRow.createCell(3).setCellValue(verb.getPastParticipleForm());
                verbRow.createCell(4).setCellValue(verb.getThirdPersonBaseForm());
                verbRow.createCell(5).setCellValue(verb.getProgressiveForm());

                meaningsRow.createCell(0).setCellValue("");
                meaningsRow.createCell(1).setCellValue("Meanings");
                List<String> meanings = verb.getMeanings();
                if (CollectionUtils.isNotEmpty(meanings)) {
                    for (int mCounter = 0; mCounter < meanings.size(); mCounter++) {
                        meaningsRow.createCell(mCounter + 2).setCellValue(meanings.get(mCounter));
                    }
                }
                examplesRow.createCell(0).setCellValue("");
                examplesRow.createCell(1).setCellValue("Examples");
                List<String> examples = verb.getExamples();
                if (CollectionUtils.isNotEmpty(examples)) {
                    for (int eCounter = 0; eCounter < examples.size(); eCounter++) {
                        examplesRow.createCell(eCounter + 2).setCellValue(examples.get(eCounter));
                    }
                }
            }
            File file = new File(fullFilePath);
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();

            fileOutputStream = new FileOutputStream(fullFilePath);
            workbook.write(fileOutputStream);
            System.out.println("Verbs list exported successfully");

        } catch (Exception e) {
            LOGGER.error("Exception occurred while exporting excel: {}", e.getMessage());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                }
            }
        }
    }

}
