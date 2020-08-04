package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Product;

@RestController
public class ProductServiceController {

   /* 
    * * * * * * * * * * * * * * * * * * *
    * API version 1
    * Product repository save in local Java
    * * * * * * * * * * * * * * * * * * *
    */

   private static Map<String, Product> productRepo = new HashMap<>();
   static {
      Product iphonex = new Product();
      iphonex.setId(UUID.randomUUID().toString());
      iphonex.setName("Iphone X");
      iphonex.setPrice("1000USD");
      iphonex.setBranch("Apple");
      productRepo.put(iphonex.getId(), iphonex);
      
      Product iphonexx = new Product();
      iphonexx.setId(UUID.randomUUID().toString());
      iphonexx.setName("Iphone XX");
      iphonexx.setPrice("2000USD");
      iphonexx.setBranch("Apple");
      productRepo.put(iphonexx.getId(), iphonexx);
   }

   // Update a product
   @RequestMapping(value = "api/v1/products/{id}", method = RequestMethod.PUT)
   public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product) { 
      if (productRepo.containsKey(id)) {
         productRepo.remove(id);
         product.setId(id);
         productRepo.put(id, product);
         return new ResponseEntity<>("Product is updated successsfully", HttpStatus.OK);
      }
      else 
         return new ResponseEntity<Object>("Product not found", HttpStatus.NOT_FOUND);
   }

   // Create a product
   @RequestMapping(value = "api/v1/products/{name}", method = RequestMethod.POST)
   public ResponseEntity<Object> createProduct(@PathVariable("name") String name, @RequestBody Product product) {
      String id=UUID.randomUUID().toString();
      if (!productRepo.containsKey(id)) {
         product.setId(id);
         productRepo.put(product.getId(), product);
         return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
      }
      else {
         return new ResponseEntity<Object>("Product existed", HttpStatus.BAD_REQUEST);
      }
   }

   // Get all products
   @RequestMapping(value = "api/v1/products")
   public ResponseEntity<Object> getProduct() {
      return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
   }

   // Get a product
   @RequestMapping(value = "api/v1/products/{id}")
   public ResponseEntity<Object> getProductDetail(@PathVariable("id") String id) {
      if (productRepo.containsKey(id)) {
            return new ResponseEntity<>(productRepo.get(id), HttpStatus.OK);
         }
         else 
            return new ResponseEntity<Object>("Product not found", HttpStatus.NOT_FOUND);
   }

   // Delete a product
   @RequestMapping(value = "api/v1/products/{id}", method = RequestMethod.DELETE)
   public ResponseEntity<Object> deleteProduct(@PathVariable("id") String id) { 
      if (productRepo.containsKey(id)) {
         productRepo.remove(id);
         return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
      }
      else 
         return new ResponseEntity<Object>("Product not found", HttpStatus.NOT_FOUND);
      
   }

}