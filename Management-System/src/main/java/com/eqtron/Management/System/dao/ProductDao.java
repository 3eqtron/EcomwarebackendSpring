package com.eqtron.Management.System.dao;

import com.eqtron.Management.System.pojo.Product;
import com.eqtron.Management.System.wrapper.ProductWrapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductDao extends JpaRepository<Product,Integer> {
    List<ProductWrapper> getAllProduct();
@Modifying
@Transactional

    Integer updateProductStatus(@Param("status")String status,@Param("id")Integer id);

    List<ProductWrapper> getProductByCategory(@Param("id")Integer id);

    ProductWrapper getProductById(@Param("id")Integer id);
}
