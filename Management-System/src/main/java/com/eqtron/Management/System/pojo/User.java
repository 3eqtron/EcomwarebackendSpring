package com.eqtron.Management.System.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
@NamedQuery(name = "User.FindByEmailId",query = "select u from User u where u.email=:email")
@NamedQuery(name = "User.getAllusers", query = "select new com.eqtron.Management.System.wrapper.UserWrapper ( u.id , u.nom , u.contact_num , u.email , u.status,u.entreprise.id,u.entreprise.name) from User u where u.role='user'")
@NamedQuery(name = "User.updateStatus",query = "update User u set u.status=:status where u.id=:id")
@NamedQuery(name = "User.getAlladmin", query = "select u.email  from User u where u.role='admin'")



@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "user")
public class User implements Serializable {
private static final long SerialVersionUID=1L;
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Integer id;
@Column(name = "nom")
private String nom;

    @Column(name = "contact_num")
    private String contact_num;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private String status;
    @Column(name = "role")
    private String role;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="entreprisefk",referencedColumnName = "id")
    Entreprise entreprise;

}
