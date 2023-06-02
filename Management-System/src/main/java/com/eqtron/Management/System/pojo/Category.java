package com.eqtron.Management.System.pojo;

import com.eqtron.Management.System.wrapper.ProductWrapper;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;


@NamedQuery(name = "Category.getallcategory",query = "select c from Category c where c.id in (select p.category from Product p where p.status='true')")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "category")
public class Category implements Serializable {
    private static final long SerialVersionUID = 1L;




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nom_category")
    private String nom_category;

}
