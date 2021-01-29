package com.awsdynamodb.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class DynamoDBService {

    private DynamoDB amazonDynamoDB;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAws() {
        log.info("Initialize AWS");

        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonDynamoDB amazonDynamoDBClient = AmazonDynamoDBClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .withRegion(Regions.US_EAST_2)
            .build();

        amazonDynamoDB = new DynamoDB(amazonDynamoDBClient);
    }

    public void createTable(String tableName) throws Exception {
        try {
            Table table = amazonDynamoDB.createTable(tableName,
                Arrays.asList(
                    new KeySchemaElement("id", KeyType.HASH),
                    new KeySchemaElement("name", KeyType.RANGE)),
                Arrays.asList(
                    new AttributeDefinition("id", ScalarAttributeType.N),
                    new AttributeDefinition("name", ScalarAttributeType.S)),
                new ProvisionedThroughput(10L, 10L)
            );

            table.waitForActive();
            log.info("Sucesso. Status da tabela: " + table.getDescription().getTableStatus());
        } catch(Exception e) {
            throw new Exception(e);
        }
    }

    public void updateTable() {

    }

    public void deleteTable() {

    }

}
