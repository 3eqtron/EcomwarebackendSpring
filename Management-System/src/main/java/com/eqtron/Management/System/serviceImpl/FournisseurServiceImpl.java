package com.eqtron.Management.System.serviceImpl;

import com.eqtron.Management.System.constents.SystemConstants;
import com.eqtron.Management.System.dao.FournisseurDao;
import com.eqtron.Management.System.jwt.JwtFilter;
import com.eqtron.Management.System.pojo.Category;
import com.eqtron.Management.System.pojo.Fournisseur;
import com.eqtron.Management.System.service.FournisseurService;
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
public class FournisseurServiceImpl implements FournisseurService {

    @Autowired
    FournisseurDao fournisseurDao;
    @Autowired
    JwtFilter jwtFilter;

        @Override
        public ResponseEntity<List<Fournisseur>> getAllfournisseur(String filtervalue) {
            try {
                if (!Strings.isNullOrEmpty(filtervalue)&& filtervalue.equalsIgnoreCase("true")){
                    log.info("");
                    return new ResponseEntity<List<Fournisseur>>(fournisseurDao.getAllfournisseur(), HttpStatus.OK);
                }
                return new ResponseEntity<>(fournisseurDao.findAll(),HttpStatus.OK);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return new ResponseEntity<List<Fournisseur>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    private Fournisseur getFournisseurForMap(Map<String,String> requestMap, boolean isAdd){
        Fournisseur fournisseur = new Fournisseur();
        if (isAdd){
            fournisseur.setId(Integer.parseInt(requestMap.get("id")));

        }
        fournisseur.setName(requestMap.get("name"));
        fournisseur.setAdresse(requestMap.get("adresse"));
        return fournisseur;
    }
    private boolean validatefournisseurMap(Map<String, String> requestmap, boolean validateId) {
        if (requestmap.containsKey("name")){
            if (requestmap.containsKey("id")&& validateId){
                return true;
            }
            else if (!validateId) {
                return true;
            }
        }
        return false;
    }
    @Override
    public ResponseEntity<String> addnewFournisseur(Map<String, String> requestmap) {
        try {
            if (jwtFilter.isAdmin()){
                if (validatefournisseurMap(requestmap,false)){
                    fournisseurDao.save(getFournisseurForMap(requestmap,false));
                    return SystemUtils.getResponseEntity("fournisseur ajouter avec succes",HttpStatus.OK);
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
    public ResponseEntity<String> updatefournisseur(Map<String, String> requestmap) {

        try {
            if (jwtFilter.isAdmin()){
                if (validatefournisseurMap(requestmap,true)){
                    Optional optional=fournisseurDao.findById(Integer.parseInt(requestmap.get("id")));
                    if (!optional.isEmpty()){
                        fournisseurDao.save(getFournisseurForMap(requestmap,true));
                        return SystemUtils.getResponseEntity("fournisseur Modifier avec Succes",HttpStatus.OK);
                    }else {
                        return SystemUtils.getResponseEntity("ID de fournisseur pas existant",HttpStatus.OK);
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

