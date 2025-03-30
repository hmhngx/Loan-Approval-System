package com.example.demo.domain;

import com.example.demo.dispatcher.PriceListDispatcher;
import com.example.demo.helper.ExcelReader;
import com.example.demo.model.PriceList;
import com.example.demo.repository.PriceListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
@Slf4j
public class PriceListDomain implements PriceListDispatcher {

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private ExcelReader excelReader;

    @Override
    @Transactional
    public boolean createPriceList(MultipartFile fileUpload) {
        try {
            // Save the uploaded file to disk and get the path
            //String filePath = excelReader.writeFile(fileUpload);

            //log.info("path.."+filePath);
            // Read the Excel file and convert it to a list of PriceList objects
            List<PriceList> priceLists = excelReader.readExcel("C:\\path\\to\\upload\\dir\\2024\\08\\1724058798981_PRICE_LIST[1].xlsx");

            // Check if the list is not empty and save to the database
            if (priceLists!=null) {
                priceListRepository.saveAll(priceLists);
                return true;
            }

            return false;
        } catch (Exception ex) {
            // Log the error and return false if any exception occurs
            ex.printStackTrace(); // Ideally, use a logger instead of printStackTrace
            return false;
        }
    }
}
