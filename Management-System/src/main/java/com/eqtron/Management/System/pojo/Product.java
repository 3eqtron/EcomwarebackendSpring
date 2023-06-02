package com.eqtron.Management.System.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.eqtron.Management.System.wrapper.ProductWrapper;
import java.io.Serializable;


@NamedQuery(name = "Product.getAllProduct",query = "select new com.eqtron.Management.System.wrapper.ProductWrapper (p.id , p.name , p.description , p.price, p.status , p.category.id , p.category.nom_category,p.quantity ,p.entreprise.id,p.fournisseur.id) from Product p ")
@NamedQuery(name = "Product.updateProductStatus",query = "update Product p set p.status=:status where p.id=:id")
@NamedQuery(name = "Product.getProductByCategory",query = "select new com.eqtron.Management.System.wrapper.ProductWrapper(p.id , p.name) from Product p where p.category.id=:id and p.status='true'")
@NamedQuery(name = "Product.getProductById",query = "select new com.eqtron.Management.System.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,quantity)from Product p where p.id=:id")
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "product")
public class Product implements Serializable {

    private static final long SerialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="category_fk",nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name="entreprise_fk",nullable = false)
    Entreprise entreprise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="fournisseur_fk",nullable = false)
    Fournisseur fournisseur;


    @Column(name = "description")
    private String description;
@Column(name = "price")
    private Integer price;
    @Column(name = "quantity")
    private Integer quantity;
@Column(name = "status")
    private String status;
}
