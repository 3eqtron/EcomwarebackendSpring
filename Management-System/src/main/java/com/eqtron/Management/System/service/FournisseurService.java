package com.eqtron.Management.System.service;

import com.eqtron.Management.System.pojo.Category;
import com.eqtron.Management.System.pojo.Fournisseur;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface FournisseurService {
    ResponseEntity<List<Fournisseur>> getAllfournisseur(String filtervalue);

    ResponseEntity<String> addnewFournisseur(Map<String, String> requestmap);
    ResponseEntity<String> updatefournisseur(Map<String, String> requestmap);
}
