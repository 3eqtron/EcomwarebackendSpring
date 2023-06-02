package com.eqtron.Management.System.serviceImpl;

import com.eqtron.Management.System.constents.SystemConstants;
import org.jasypt.util.text.AES256TextEncryptor;
import com.eqtron.Management.System.dao.UserDao;
import com.eqtron.Management.System.jwt.CustomerUserDetailsService;
import com.eqtron.Management.System.jwt.JwtFilter;
import com.eqtron.Management.System.jwt.JwtUtil;
import com.eqtron.Management.System.pojo.Entreprise;
import com.eqtron.Management.System.pojo.User;
import com.eqtron.Management.System.service.UserService;
import com.eqtron.Management.System.utils.EmailUtils;
import com.eqtron.Management.System.utils.SystemUtils;
import com.eqtron.Management.System.wrapper.UserWrapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service

public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    JwtUtil jwtUtil;
@Autowired
    EmailUtils emailUtils;

@Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
log.info("Inside SignUp {}",requestMap);
try {


    if (ValidateSignUpMap(requestMap)) {
        User user = userDao.FindByEmailId(requestMap.get("email"));
        if (Objects.isNull(user)) {
            userDao.save(getUserFromMap(requestMap));
//            entrepriseDao.save(getEntrepriseFromMap(requestMap));
            return SystemUtils.getResponseEntity("Registré avec Succées", HttpStatus.OK);
        } else {
            return SystemUtils.getResponseEntity("Email Existe", HttpStatus.BAD_REQUEST);
        }
    } else {
        return SystemUtils.getResponseEntity(SystemConstants.Donnés_Invalid, HttpStatus.BAD_REQUEST);
    }
}catch (Exception ex){
    ex.printStackTrace();
}
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private boolean ValidateSignUpMap(Map<String,String>requestMap){
if (requestMap.containsKey("nom")&&requestMap.containsKey("contact_num")
        && requestMap.containsKey("email")&&requestMap.containsKey("password")){
    return true;
        }
return false;
    }
    private User getUserFromMap(Map<String,String>requestMap){
User user=new User();
        Entreprise entreprise =new Entreprise();
user.setNom(requestMap.get("nom"));
        user.setContact_num(requestMap.get("contact_num"));
        user.setEmail(requestMap.get("email"));
      user.setPassword(new BCryptPasswordEncoder().encode(requestMap.get("password")));





//     user.setPassword(requestMap.get("password"));

        user.setStatus("true");
        user.setRole("admin");
user.setEntreprise(entreprise);


        entreprise.setName(requestMap.get("name"));

        entreprise.setAdresse(requestMap.get("adresse"));
        entreprise.setResponsable(requestMap.get("responsable"));


        return user;
    }



    @Override

    public ResponseEntity<String> login(Map<String, String> requestMap) {

        log.info("Inside login");
        try {
Authentication auth=authenticationManager.authenticate(
new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
            );



            if (auth.isAuthenticated()){
                if (customerUserDetailsService.getUserdetails().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(customerUserDetailsService.getUserdetails().getEmail(),
                                    customerUserDetailsService.getUserdetails().getRole())+"\"}",
                            HttpStatus.OK);
                }}
                else {
                    return new ResponseEntity<String>("{\"message \":\""+"attendre que vous etre approuve par Admin."
                            +"\"}",HttpStatus.BAD_REQUEST);
                }

        }catch (Exception ex){
            log.error("{}",ex);
        }
        return new ResponseEntity<String>("{\"message \":\""+"Data non valide."
                +"\"}",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllusers() {
        log.info("Inside get");
        try {
if (jwtFilter.isAdmin()){
return new ResponseEntity<>(userDao.getAllusers(),HttpStatus.OK);
}
else {
    return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
}
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {

            try {
                if (jwtFilter.isAdmin()){
            Optional  <User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
            if (!optional.isEmpty()){
                userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                sendMailToAdmin(requestMap.get("Status"),optional.get().getEmail(),userDao.getAlladmin());
                return SystemUtils.getResponseEntity("Update user est termine avec succes",HttpStatus.OK);
            }
                }
                else {
return SystemUtils.getResponseEntity(SystemConstants.Unauthorized_Acces,HttpStatus.UNAUTHORIZED);
                }
        }catch (Exception ex){
             ex.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private void sendMailToAdmin(String status, String user, List<String> alladmin) {
        alladmin.remove(jwtFilter.getCurrentUser());
        if (status!= null && status.equalsIgnoreCase("true")){
emailUtils.sendsimplemessage(jwtFilter.getCurrentUser(),"Compte Accepter...","USER:-"+user+" \n accepter par \n  ADMIN:-"+jwtFilter.getCurrentUser(),alladmin);
        }else {
            emailUtils.sendsimplemessage(jwtFilter.getCurrentUser(),"Compte Rejeter...","USER:-"+user+" \n rejeter par\n ADMIN:-"+jwtFilter.getCurrentUser(),alladmin);

        }

    }
    @Override
    public ResponseEntity<String> checkToken() {
return SystemUtils.getResponseEntity("true",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changepassword(Map<String, String> requestMap) {
        try {
User userobj=userDao.findByEmail(jwtFilter.getCurrentUser());
if (!userobj.equals(null)){
if (userobj.getPassword().equals(requestMap.get("oldpassword"))){

    userobj.setPassword(requestMap.get("newpassword"));
    userDao.save(userobj);
    return SystemUtils.getResponseEntity("Mot de passe changer avec succes",HttpStatus.OK);
}
    return SystemUtils.getResponseEntity("Ancient Mot de passe incorrect",HttpStatus.BAD_REQUEST);
}
return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
            ex.printStackTrace();
        }return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgetpassword(Map<String, String> requestMap) {

        try {

                User user=userDao.findByEmail(requestMap.get("email"));
                if (!Objects.isNull(user)&& !Strings.isNullOrEmpty(user.getEmail()))
                    emailUtils.forgotmail(user.getEmail(),"Information par ROOT",user.getPassword());
                    return  SystemUtils.getResponseEntity("Verifier votre email pour plus informations",HttpStatus.OK);



        }catch (Exception ex){
            ex.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
