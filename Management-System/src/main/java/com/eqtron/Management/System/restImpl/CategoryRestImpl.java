package com.eqtron.Management.System.restImpl;

import com.eqtron.Management.System.constents.SystemConstants;
import com.eqtron.Management.System.pojo.Category;
import com.eqtron.Management.System.rest.CategoryRest;
import com.eqtron.Management.System.service.CategoryService;
import com.eqtron.Management.System.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController

public class CategoryRestImpl implements CategoryRest {
    @Autowired
    CategoryService categoryService;
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestmap) {
        try {
            return categoryService.addnewCategory(requestmap);

    }catch (Exception ex){
        ex.printStackTrace();
    }
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getallcategory(String filtervalue) {

        try {
return categoryService.getAllcategories(filtervalue);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updatecategory(Map<String, String> requestmap) {

        try {

return categoryService.updatecategory(requestmap);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
