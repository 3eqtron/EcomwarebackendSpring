package com.eqtron.Management.System.wrapper;

import lombok.Data;

@Data


public class UserWrapper  {

   private Integer id;
    private String nom;
    private  String contact_num;
    private  String email;

    private  String status;
private Integer entreprise_id;
private String entreprise_name;


 public UserWrapper(Integer id, String nom, String contact_num, String email, String status,Integer entreprise_id,String entreprise_name) {
  this.id = id;
  this.nom = nom;
  this.contact_num = contact_num;
  this.email = email;
  this.status = status;
this.entreprise_id=entreprise_id;
 this.entreprise_name=entreprise_name;
 }
}
