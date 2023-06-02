package com.eqtron.Management.System.service;

import com.eqtron.Management.System.pojo.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    ResponseEntity<String> addnewCategory(Map<String, String> requestmap);

    ResponseEntity<List<Category>> getAllcategories(String filtervalue);
    ResponseEntity<String> updatecategory(Map<String, String> requestmap);
}
