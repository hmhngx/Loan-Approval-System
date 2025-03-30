package com.example.demo.controller;

import com.example.demo.dispatcher.PriceListDispatcher;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pricelists")
public class PriceListController {

    private final PriceListDispatcher dispatcher;

    @Autowired
    public PriceListController(PriceListDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @SneakyThrows
    @ApiOperation(value = "Create a new PriceList entry")
    @PostMapping(
            value = "/create",
            produces = {"application/json"}
    )

    public ResponseEntity<? extends Object> createPriceList(
            @ApiParam(value = "Template fee exception", required = true) @RequestParam("file")
            MultipartFile file) {

        boolean createdPriceList = dispatcher.createPriceList(file);
        if(createdPriceList) {
            return new ResponseEntity<>(createdPriceList, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
