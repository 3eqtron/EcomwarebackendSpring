package com.eqtron.Management.System.wrapper;

import lombok.Data;


@Data


public class ProductWrapper {

Integer id;
String name;
String description;
Integer price;

String status;
Integer categoryId;
String categoryName;
    Integer quantity;
     Integer entreprise_id;

     Integer fournisseur_id;

    public ProductWrapper() {
    }

    public ProductWrapper(Integer id, String name, String description, Integer price, String status, Integer categoryId, String categoryName,Integer quantity,Integer entreprise_id,Integer fournisseur_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.quantity=quantity;
        this.entreprise_id = entreprise_id;
        this.fournisseur_id=fournisseur_id;
    }

    public ProductWrapper(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductWrapper(Integer id, String name, String description, Integer price,Integer quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity=quantity;
    }
}
