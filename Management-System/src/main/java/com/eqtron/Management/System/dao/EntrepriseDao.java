package com.eqtron.Management.System.dao;

import com.eqtron.Management.System.pojo.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseDao extends JpaRepository<Entreprise, Integer> {

}