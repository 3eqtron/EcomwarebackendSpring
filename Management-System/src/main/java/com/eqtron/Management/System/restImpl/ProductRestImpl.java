package com.eqtron.Management.System.restImpl;

import com.eqtron.Management.System.constents.SystemConstants;
import com.eqtron.Management.System.rest.ProductRest;
import com.eqtron.Management.System.service.ProductService;
import com.eqtron.Management.System.utils.SystemUtils;
import com.eqtron.Management.System.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProductRestImpl implements ProductRest {
    @Autowired
    ProductService productService;
    @Override
    public ResponseEntity<String> addnewproduct(Map<String, String> requestmap) {
        try {
return productService.addnewproduct(requestmap);
        }catch (Exception ex){
            ex.printStackTrace();
        }return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {


          return   productService.getAllProduct();

    }

    @Override
    public ResponseEntity<String> updateproduct(Map<String, String> requestMap) {
        try {
return productService.updateproduct(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteproduct(Integer id) {
        try {
return productService.deleteproduct(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            return productService.updateStatus(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try {
return productService.getByCategory(id);
        }catch (Exception ex ){
            ex.printStackTrace();
        }return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try {
            return productService.getProductById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
