package com.awsdynamodb.controller;

import com.awsdynamodb.service.DynamoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dynamoDB")
public class DynamoDBController {

    @Autowired
    private DynamoDBService dynamoDBService;

    @GetMapping("/create-table/{tableName}")
    public ResponseEntity<HttpStatus> createTable(@PathVariable String tableName) throws Exception {
        dynamoDBService.createTable(tableName);
        return ResponseEntity.ok().build();
    }

}
