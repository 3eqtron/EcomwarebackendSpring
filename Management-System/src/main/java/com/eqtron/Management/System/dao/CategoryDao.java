package com.eqtron.Management.System.dao;

import com.eqtron.Management.System.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category,Integer> {
    List<Category>getallcategory();
}
