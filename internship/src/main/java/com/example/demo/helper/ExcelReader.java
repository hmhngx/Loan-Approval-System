package com.example.demo.helper;

import com.example.demo.model.PriceList;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;



@Slf4j
@Component
public class ExcelReader {

    private static final String uploadDir = "/path/to/upload/dir/";  // Adjust this to your upload directory

    @SneakyThrows
    public String writeFile(MultipartFile part) {
        Date currentDate = new Date(System.currentTimeMillis());
        String tempFile = part.getOriginalFilename();
        String fileName = currentDate.getTime() + "_" + tempFile;
        String filePath = getSubFolderByDate(currentDate, "yyyy/MM/");

        // Create directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir, filePath);
        Files.createDirectories(uploadPath);

        // Construct the full file path
        Path fileFullPath = uploadPath.resolve(fileName);

        // Save the file
        Files.copy(part.getInputStream(), fileFullPath, StandardCopyOption.REPLACE_EXISTING);

        // Return the path of the saved file
        return fileFullPath.toString();
    }

    private String getSubFolderByDate(Date date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
        return localDate.format(formatter);
    }

    private void mkDir(String dir) {
        File directory = new File(dir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    @SneakyThrows
    public List<PriceList> readExcel(String path) {
        try{
             InputStream is = new FileInputStream(path);
            ReadableWorkbook workbook= new ReadableWorkbook(is);
            Sheet sheet = workbook.getFirstSheet();
            List<PriceList> valueList = new ArrayList<>();
            try (Stream<Row> rows = sheet.openStream()) {
                rows.forEach(row -> {
                    PriceList model = new PriceList();
                    model.setStartDate(getDateValue(row, 0));
                    model.setItemId(Long.parseLong(getStringValue(row, 1)));
                    model.setItemName(getStringValue(row, 2));
                    model.setTwoWeeks(getNumberValue(row, 3));
                    model.setOneMonth(getNumberValue(row, 4));
                    model.setTwoMonths(getNumberValue(row, 5));
                    model.setThreeMonths(getNumberValue(row, 6));
                    model.setSixMonths(getNumberValue(row, 7));
                    model.setStatus(getStringValue(row, 8));

                    valueList.add(model);
                });
            }
            workbook.close();
            return valueList;
        }
        catch (Exception ex)
        {
            log.error("(readExcel) ex: {}", ex);
            return null;
        }

//        log.info("(readExcel) path: {}", path);
//
//        try (InputStream is = new FileInputStream(path){
//             ReadableWorkbook workbook = new ReadableWorkbook(is);
//
//            Sheet sheet = workbook.getFirstSheet();
//            List<PriceList> valueList = new ArrayList<>();
//
//            try (Stream<Row> rows = sheet.openStream()) {
//                rows.forEach(row -> {
//                    PriceList model = new PriceList();
//                    model.setStartDate(getDateValue(row, 0));
//                    model.setItemId(Long.parseLong(getStringValue(row, 1)));
//                    model.setItemName(getStringValue(row, 2));
//                    model.setTwoWeeks(getNumberValue(row, 3));
//                    model.setOneMonth(getNumberValue(row, 4));
//                    model.setTwoMonths(getNumberValue(row, 5));
//                    model.setThreeMonths(getNumberValue(row, 6));
//                    model.setSixMonths(getNumberValue(row, 7));
//                    model.setStatus(getStringValue(row, 8));
//
//                    valueList.add(model);
//                });
//            }
//
//            return valueList;
//
//        } catch (Exception e) {
//            log.error("(readExcel) ex: {}", e);
//            throw new RuntimeException("Invalid data format in template", e);
//        }
    }


    private Date getDateValue(Row row, int index) {
        String strValue = row.getCell(index).toString();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        try {
            if (strValue != null && !strValue.isEmpty()) {
                return formatter.parse(strValue);
            }
        } catch (ParseException ex) {
            // Log the exception if necessary
            ex.printStackTrace();
        }

        return null;
    }

    private String getStringValue(Row row, int index) {
        return row.getCell(index).toString();
    }

    private BigDecimal getNumberValue(Row row, int index) {
        String data=row.getCell(index).toString();
        if(data!=null)
        {
            return new BigDecimal(data);
        }
        return new BigDecimal(0);
    }
}
