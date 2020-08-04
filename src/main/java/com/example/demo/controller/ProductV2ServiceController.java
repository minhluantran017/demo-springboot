package com.example.demo.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.ProductV2;
import com.example.demo.repository.ProductV2Repo;

@RestController
public class ProductV2ServiceController {

   /* 
    * * * * * * * * * * * * * * * * * * *
    * API version 2
    * Product repository save in MongoDB
    * * * * * * * * * * * * * * * * * * *
    */
   
   @Autowired
   private ProductV2Repo productV2Repo;

   // Update a product
   @RequestMapping(value = "api/v2/products/{id}", method = RequestMethod.PUT)
   public ResponseEntity<Object> updateProductV2(@PathVariable("id") String id, @RequestBody ProductV2 productV2) { 
      if (productV2Repo.findBy_id(id) != null) {
         productV2.setId(id);
         productV2Repo.save(productV2);
         return new ResponseEntity<>("Product is updated successsfully", HttpStatus.OK);
      }
      else 
         return new ResponseEntity<Object>("Product not found", HttpStatus.NOT_FOUND);
   }

   // Create a product
   @RequestMapping(value = "api/v2/products/{name}", method = RequestMethod.POST)
   public ResponseEntity<Object> createProductV2(@PathVariable("name") String name, @RequestBody ProductV2 productV2) {
      String id=UUID.randomUUID().toString();
      if (productV2Repo.findBy_id(id) == null) {
         productV2.setId(id);
         productV2Repo.save(productV2  );
         return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
      }
      else {
         return new ResponseEntity<Object>("Product existed", HttpStatus.BAD_REQUEST);
      }
   }

   // Get all products
   @RequestMapping(value = "api/v2/products")
   public ResponseEntity<Object> getProductV2() {
      return new ResponseEntity<>(productV2Repo.findAll(), HttpStatus.OK);
   }

   // Get a product by ID
   @RequestMapping(value = "api/v2/products/{id}")
   public ResponseEntity<Object> getProductV2DetailById(@PathVariable("id") String id) {
      return new ResponseEntity<>(productV2Repo.findBy_id(id), HttpStatus.OK);
   }

   // Get a product by name
   @RequestMapping(value = "api/v2/products/{name}")
   public ResponseEntity<Object> getProductV2DetailByName(@PathVariable("name") String name) {
      return new ResponseEntity<>(productV2Repo.findByName(name), HttpStatus.OK);
   }

   // Delete a product
   @RequestMapping(value = "api/v2/products/{id}", method = RequestMethod.DELETE)
   public ResponseEntity<Object> deleteProductV2(@PathVariable("id") String id) { 
      if (productV2Repo.findBy_id(id) != null) {
         productV2Repo.delete(productV2Repo.findBy_id(id));
         return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
      }
      else 
         return new ResponseEntity<Object>("Product not found", HttpStatus.NOT_FOUND);
      
   }

}