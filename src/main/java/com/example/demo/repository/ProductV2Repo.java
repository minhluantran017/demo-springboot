package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.ProductV2;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductV2Repo extends MongoRepository<ProductV2, String> {
    ProductV2 findBy_id(String _id);
    List<ProductV2> findByName(String name);
}