package com.vocabulary.learning.app.service;

import com.vocabulary.learning.app.AppConstants;
import com.vocabulary.learning.app.enums.LearningStatus;
import com.vocabulary.learning.app.model.Verb;
import com.vocabulary.learning.app.request.VerbSearchRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    private static String filePath = AppConstants.EXPORT_FOLDER_PATH;
    private static String fileName = AppConstants.EXPORT_VERBS_FILE_NAME;

    private String fullFilePath;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelService.class);
    private final VerbService verbService;

    public ExcelService(VerbService verbService) {
        this.verbService = verbService;
        fullFilePath = filePath + "\\" + fileName;
    }

    public XSSFWorkbook createCurrentGridExcel(List<Integer> verbIds, String order) {

        List<Verb> verbList = verbService.findAllByVerbIds(verbIds, order);
        if (CollectionUtils.isEmpty(verbList)) {
            throw new RuntimeException("No verbs found to export");
        }
        return createVerbsExcelFile(verbList);
    }

    public XSSFWorkbook createVerbsExcelFile(List<Verb> verbList) {

        if (CollectionUtils.isEmpty(verbList)) {
            VerbSearchRequest request = new VerbSearchRequest();
            request.setSelectedLetter("All");
            request.setExcludedChildren(false);
            verbList = verbService.getAllVerbs(request);
        }
        if (CollectionUtils.isEmpty(verbList)) {
            throw new RuntimeException("No verbs found to export");
        }

        FileOutputStream fileOutputStream = null;
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Verbs List");
            sheet.createFreezePane(0, 1);
            sheet.setColumnWidth(1, 25 * 256);
            for (int c = 2; c <= 22; c++) {
                sheet.setColumnWidth(c, 40 * 256);
            }

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 16);
            headerFont.setColor(IndexedColors.BLACK.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
            headerCellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Font subHeaderFont = workbook.createFont();
            subHeaderFont.setBold(true);
            subHeaderFont.setFontHeightInPoints((short) 12);
            subHeaderFont.setColor(IndexedColors.BLACK.getIndex());
            subHeaderFont.setFontName("Times New Roman");
            subHeaderFont.setItalic(true);

            CellStyle subHeaderCellStyle = workbook.createCellStyle();
            subHeaderCellStyle.setFont(subHeaderFont);

            Font columnFont = workbook.createFont();
            columnFont.setFontHeightInPoints((short) 14);
            columnFont.setColor(IndexedColors.BLACK.getIndex());
            columnFont.setFontName("Times New Roman");

            CellStyle columnCellStyle = workbook.createCellStyle();
            columnCellStyle.setFont(columnFont);
            columnCellStyle.setWrapText(true);

            Font verbsFont = workbook.createFont();
            verbsFont.setFontHeightInPoints((short) 14);
            verbsFont.setColor(IndexedColors.BLACK.getIndex());
            verbsFont.setFontName("Times New Roman");

            CellStyle verbsCellStyle = workbook.createCellStyle();
            verbsCellStyle.setFont(verbsFont);
            verbsCellStyle.setFillBackgroundColor(IndexedColors.PALE_BLUE.getIndex());
            verbsCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
            verbsCellStyle.setFillPattern(FillPatternType.THIN_HORZ_BANDS);

            XSSFRow headerRow = sheet.createRow((short) 0);

            String[] headers = {"S.No.", "Base Form", "Past Tense Form", "Past Participle Form", "Third Person Form", "Progressing Form", "Phonetics"};
            for (int i = 0; i < headers.length; i++) {
                createCellData(headerRow, i, headers[i], headerCellStyle);
            }

            int serialNumber = 1;
            int rowNumber = 1;
            for (int counter = 0; counter < verbList.size(); counter++) {
                Verb verb = verbList.get(counter);

                XSSFRow verbRow = sheet.createRow((short) (rowNumber++));
                XSSFRow meaningsRow = sheet.createRow((short) (rowNumber++));
                XSSFRow examplesRow = sheet.createRow((short) (rowNumber++));

                int columnCounter = 0;
                createCellData(verbRow, columnCounter++, (serialNumber++) + "", verbsCellStyle);
                createCellData(verbRow, columnCounter++, verb.getBaseForm(), verbsCellStyle);
                createCellData(verbRow, columnCounter++, verb.getPastTenseForm(), verbsCellStyle);
                createCellData(verbRow, columnCounter++, verb.getPastParticipleForm(), verbsCellStyle);
                createCellData(verbRow, columnCounter++, verb.getThirdPersonBaseForm(), verbsCellStyle);
                createCellData(verbRow, columnCounter++, verb.getProgressiveForm(), verbsCellStyle);
                createCellData(verbRow, columnCounter++, verb.getPhonetics(), verbsCellStyle);

                createCellData(meaningsRow, 0, "", columnCellStyle);
                createCellData(meaningsRow, 1, "Meanings", subHeaderCellStyle);
                List<String> meanings = verb.getMeanings();
                if (CollectionUtils.isNotEmpty(meanings)) {
                    for (int mCounter = 0; mCounter < meanings.size(); mCounter++) {
                        createCellData(meaningsRow, mCounter + 2, meanings.get(mCounter), columnCellStyle);
                    }
                }

                createCellData(examplesRow, 0, "", columnCellStyle);
                createCellData(examplesRow, 1, "Examples", subHeaderCellStyle);
                List<String> examples = verb.getExamples();
                if (CollectionUtils.isNotEmpty(examples)) {
                    for (int eCounter = 0; eCounter < examples.size(); eCounter++) {
                        createCellData(examplesRow, eCounter + 2, examples.get(eCounter), columnCellStyle);
                    }
                }
            }
            try {
                File file = new File(fullFilePath);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();

                fileOutputStream = new FileOutputStream(fullFilePath);
                workbook.write(fileOutputStream);
            } catch (Exception e) {
                LOGGER.error("Failed to write into file");
            }
            System.out.println("Verbs list exported successfully");
            return workbook;

        } catch (Exception e) {
            LOGGER.error("Exception occurred while exporting excel: {}", e.getMessage());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                }
            }
//            if (workbook != null) {
//                try {
//                    workbook.close();
//                } catch (IOException e) {
//                }
//            }
        }
        return null;
    }

    private void createCellData(XSSFRow row, int index, String value, CellStyle style) {
        Cell cell = row.createCell(index);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    public void readAndInsertVerbsFromExcel(XSSFWorkbook workbook) {
        try {
            List<Verb> verbList = new ArrayList<>();

            XSSFSheet verbsSheet = workbook.getSheetAt(0);
            for (int rowCounter = 1; rowCounter < verbsSheet.getPhysicalNumberOfRows(); rowCounter = rowCounter + 3) {
                Verb verb = new Verb();

                XSSFRow verbRow = verbsSheet.getRow(rowCounter);
                verb.setBaseForm(verbRow.getCell(1) +"");
                verb.setPastTenseForm(verbRow.getCell(2) +"");
                verb.setPastParticipleForm(verbRow.getCell(3) +"");
                verb.setThirdPersonBaseForm(verbRow.getCell(4) +"");
                verb.setProgressiveForm(verbRow.getCell(5) +"");
                verb.setPhonetics(verbRow.getCell(6) +"");

                XSSFRow meaningsRow = verbsSheet.getRow(rowCounter+1);
                for (int meaningsCounter = 2; meaningsCounter < meaningsRow.getPhysicalNumberOfCells(); meaningsCounter++) {
                    if(StringUtils.isNotBlank(meaningsRow.getCell(meaningsCounter)+"")){
                        verb.getMeanings().add(meaningsRow.getCell(meaningsCounter)+"");
                    }
                }

                XSSFRow examplesRow = verbsSheet.getRow(rowCounter+2);
                for (int exampleCounter = 2; exampleCounter < examplesRow.getPhysicalNumberOfCells(); exampleCounter++) {
                    if(StringUtils.isNotBlank(examplesRow.getCell(exampleCounter)+"")){
                        verb.getExamples().add(examplesRow.getCell(exampleCounter)+"");
                    }
                }
                verb.setLearningStatus(LearningStatus.NOT_STARTED);
                verbList.add(verb);
            }

            System.out.println(verbList);

            if(CollectionUtils.isNotEmpty(verbList)){
                verbService.insertVerbs(verbList);
            }else{
                throw new RuntimeException("No records found in the excel");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
