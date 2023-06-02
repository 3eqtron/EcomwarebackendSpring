package com.eqtron.Management.System.dao;

import com.eqtron.Management.System.pojo.Category;
import com.eqtron.Management.System.pojo.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FournisseurDao extends JpaRepository<Fournisseur,Integer> {
    List<Fournisseur> getAllfournisseur();
}
