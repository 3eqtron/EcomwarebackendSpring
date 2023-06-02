package com.eqtron.Management.System.serviceImpl;

import com.eqtron.Management.System.constents.SystemConstants;
import com.eqtron.Management.System.dao.CategoryDao;
import com.eqtron.Management.System.jwt.JwtFilter;
import com.eqtron.Management.System.pojo.Category;
import com.eqtron.Management.System.service.CategoryService;
import com.eqtron.Management.System.utils.SystemUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImp implements CategoryService {
@Autowired
    CategoryDao categoryDao;
@Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addnewCategory(Map<String, String> requestmap) {
        try {
if (jwtFilter.isAdmin()){
if (validateCategoryMap(requestmap,false)){
categoryDao.save(getCategoryForMap(requestmap,false));
return SystemUtils.getResponseEntity("Category ajouter avec succes",HttpStatus.OK);
}
}else {
    return SystemUtils.getResponseEntity(SystemConstants.Unauthorized_Acces,HttpStatus.UNAUTHORIZED);
}

    }catch (Exception ex){
        ex.printStackTrace();
    }
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private boolean validateCategoryMap(Map<String, String> requestmap, boolean validateId) {
        if (requestmap.containsKey("nom_category")){
            if (requestmap.containsKey("id")&& validateId){
                return true;
            }
            else if (!validateId) {
                return true;
            }
        }
return false;
    }
    private Category getCategoryForMap(Map<String,String> requestMap,boolean isAdd){
               Category category = new Category();
               if (isAdd){
                   category.setId(Integer.parseInt(requestMap.get("id")));

}
               category.setNom_category(requestMap.get("nom_category"));
               return category;
    }
    @Override
    public ResponseEntity<List<Category>> getAllcategories(String filtervalue) {
try {
if (!Strings.isNullOrEmpty(filtervalue)&& filtervalue.equalsIgnoreCase("true")){
    log.info("");
    return new ResponseEntity<List<Category>>(categoryDao.getallcategory(),HttpStatus.OK);
}
return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
}catch (Exception ex){
    ex.printStackTrace();
}
return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updatecategory(Map<String, String> requestmap) {

        try {
            if (jwtFilter.isAdmin()){
if (validateCategoryMap(requestmap,true)){
    Optional optional=categoryDao.findById(Integer.parseInt(requestmap.get("id")));
    if (!optional.isEmpty()){
categoryDao.save(getCategoryForMap(requestmap,true));
return SystemUtils.getResponseEntity("Category Modifier avec Succes",HttpStatus.OK);
    }else {
        return SystemUtils.getResponseEntity("ID de Category pas existant",HttpStatus.OK);
    }
}
return SystemUtils.getResponseEntity(SystemConstants.Donnés_Invalid,HttpStatus.BAD_REQUEST);
            }else {
                return SystemUtils.getResponseEntity(SystemConstants.Unauthorized_Acces,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
