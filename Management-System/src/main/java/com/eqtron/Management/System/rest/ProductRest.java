package com.eqtron.Management.System.rest;

import com.eqtron.Management.System.wrapper.ProductWrapper;
import com.eqtron.Management.System.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addnewproduct(@RequestBody Map<String, String> requestmap);

    @GetMapping(path = "/get")
   public ResponseEntity<List<ProductWrapper>> getAllProduct();

    @PostMapping(path = "/update")
    ResponseEntity<String>updateproduct(@RequestBody Map<String,String>requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String>deleteproduct(@PathVariable Integer id);

    @PostMapping(path = "/updateStatus")
    ResponseEntity<String>updateStatus(@RequestBody Map<String,String>requestMap);

    @GetMapping(path = "/getByCategory/{id}")
    ResponseEntity<List<ProductWrapper>>getByCategory(@PathVariable Integer id);
    @GetMapping(path = "/getById/{id}")
    ResponseEntity<ProductWrapper> getProductById (@PathVariable Integer id);
}
