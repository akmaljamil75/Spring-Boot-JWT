package com.spring_jwt.service;


import java.util.*;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoCollection;
import com.spring_jwt.dto.BaseSearchDTO;

public class BaseSearchService {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @SuppressWarnings("unchecked")
    public Object search(BaseSearchDTO dto, String namaCollection) throws Exception {
        Document query = new Document();
        MongoCollection<Document> mongoCollection =  mongoTemplate.getCollection(namaCollection);
        if(dto.getFilters() != null) {
            for (Map.Entry<String, Object> entry : dto.getFilters().entrySet()) {
                String field = entry.getKey();
                Object value = entry.getValue();
                if(value instanceof Map) { 
                    Map<String, Object> operations = (Map<String,Object>) value;
                    for (Map.Entry<String, Object> operationEntry : operations.entrySet()) {
                        String operation = operationEntry.getKey();
                        Object operationValue = operationEntry.getValue();
                        switch (operation) {
                            case "in":
                                query.append(field, new Document("$in",Arrays.asList(operationValue)));
                                break;
                            case "gt":
                                query.append(field, new Document("$gt", operationValue));
                                break;
                            case "gte":
                                query.append(field, new Document("$gte", operationValue));
                                break;
                            case "lt":
                                query.append(field, new Document("$lt", operationValue));
                                break;
                            case "lte":
                                query.append(field, new Document("$lte", operationValue));
                                break;
                            case "regex":
                                query.append(field, new Document("$regex", operationValue).append("$options", "i"));
                                break;
                            default:
                                break;
                        }
                    }
                }
                else { 
                    query.append(field, value);
                }
            }
        }
        if(query.size() == 0) {
           return mongoCollection.find().into(new ArrayList<>());
        }
        return mongoCollection.find(query).into(new ArrayList<>());
    }

}
