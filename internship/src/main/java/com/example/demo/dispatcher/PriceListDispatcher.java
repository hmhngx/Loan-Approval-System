package com.example.demo.dispatcher;

import org.springframework.web.multipart.MultipartFile;

public interface PriceListDispatcher {

    boolean createPriceList(MultipartFile file);
}
