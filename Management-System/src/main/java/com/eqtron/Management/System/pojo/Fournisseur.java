package com.eqtron.Management.System.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
@NamedQuery(name = "Fournisseur.getAllfournisseur",query = "select f from Fournisseur f where f.id in (select p.fournisseur from Product p where p.status='true')")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "fournisseur")
public class Fournisseur {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "adresse")
    private String adresse;
}
