package com.eqtron.Management.System.serviceImpl;

import com.eqtron.Management.System.constents.SystemConstants;
import com.eqtron.Management.System.dao.ProductDao;
import com.eqtron.Management.System.jwt.JwtFilter;
import com.eqtron.Management.System.pojo.*;
import com.eqtron.Management.System.service.ProductService;
import com.eqtron.Management.System.utils.SystemUtils;
import com.eqtron.Management.System.wrapper.ProductWrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;
    @Autowired
    JwtFilter jwtFilter;



    @Override
    public ResponseEntity<String> addnewproduct(Map<String, String> requestmap) {
        try {
if (jwtFilter.isAdmin()){
if (validateproductmap(requestmap,false)){
productDao.save(getproductformap(requestmap,false));
return SystemUtils.getResponseEntity("Produit ajouter avec succes",HttpStatus.OK);
    }return SystemUtils.getResponseEntity(SystemConstants.Donnés_Invalid,HttpStatus.BAD_REQUEST);
}else return SystemUtils.getResponseEntity(SystemConstants.Unauthorized_Acces, HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            ex.printStackTrace();
        }return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi, HttpStatus.INTERNAL_SERVER_ERROR);
    }




    private boolean validateproductmap(Map<String, String> requestmap, boolean validateId) {
        if (requestmap.containsKey("name")){
            if (requestmap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }return false;
    }
    private Product getproductformap(Map<String, String> requestMap, boolean isAdd) {
        Entreprise entreprise=new Entreprise();
        entreprise.setId(Integer.parseInt(requestMap.get("entreprise_id")));

        Category category = new Category();
    category.setId(Integer.parseInt(requestMap.get("categoryId")));
        Fournisseur fournisseur=new Fournisseur();
        fournisseur.setId(Integer.parseInt(requestMap.get("fournisseur_id")));
        Product product = new Product();
        if (isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));

        }else {
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setEntreprise(entreprise);
        product.setFournisseur(fournisseur);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        product.setQuantity(Integer.parseInt(requestMap.get("quantity")));
        return product;


    }
    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {

        return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);

    }
    @Override
    public ResponseEntity<String> updateproduct(Map<String, String> requestMap) {
        try {
if (jwtFilter.isAdmin()){
if (validateproductmap(requestMap,true)){
           Optional<Product> optional= productDao.findById(Integer.parseInt(requestMap.get("id")));
if (!optional.isEmpty()){
Product product= getproductformap(requestMap,true);
product.setStatus(optional.get().getStatus());
productDao.save(product);
return SystemUtils.getResponseEntity("Produit modifier avec succes",HttpStatus.OK);
}else {
    SystemUtils.getResponseEntity("Produit ne existe pas",HttpStatus.OK);
}
}else {
    return SystemUtils.getResponseEntity(SystemConstants.Donnés_Invalid,HttpStatus.BAD_REQUEST);
}
}else {
    return SystemUtils.getResponseEntity(SystemConstants.Unauthorized_Acces,HttpStatus.UNAUTHORIZED);
}
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteproduct(Integer id) {
        try {
if (jwtFilter.isAdmin()){
    Optional optional = productDao.findById(id);
    if (! optional.isEmpty()){
        productDao.deleteById(id);
        return SystemUtils.getResponseEntity("Produit supprimer avec succes",HttpStatus.OK);
    }
    return SystemUtils.getResponseEntity("Id de produit ne existe pas",HttpStatus.OK);
}else return SystemUtils.getResponseEntity(SystemConstants.Unauthorized_Acces,HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            ex.printStackTrace();
        }return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                Optional optional =productDao.findById(Integer.parseInt(requestMap.get("id")));
                if (! optional.isEmpty()){
                    productDao.updateProductStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return SystemUtils.getResponseEntity("Status de produit modifier avec succes",HttpStatus.OK);
                }return SystemUtils.getResponseEntity("Produit ne existe pas",HttpStatus.OK);
            }else return SystemUtils.getResponseEntity(SystemConstants.Unauthorized_Acces,HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            ex.printStackTrace();
        }return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try {
return new ResponseEntity<>(productDao.getProductByCategory(id),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try {
            return new ResponseEntity<>(productDao.getProductById(id),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
