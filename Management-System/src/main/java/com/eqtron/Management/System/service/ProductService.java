package com.eqtron.Management.System.service;

import com.eqtron.Management.System.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ResponseEntity<String>addnewproduct(Map<String,String>requestMap);
    ResponseEntity<List<ProductWrapper>>getAllProduct();

    ResponseEntity<String> updateproduct(Map<String, String> requestMap);

    ResponseEntity<String> deleteproduct(Integer id);

    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    ResponseEntity<List<ProductWrapper>> getByCategory(Integer id);

    ResponseEntity<ProductWrapper> getProductById(Integer id);
}
