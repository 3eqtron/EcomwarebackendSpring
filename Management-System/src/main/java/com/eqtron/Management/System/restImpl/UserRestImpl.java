package com.eqtron.Management.System.restImpl;

import com.eqtron.Management.System.constents.SystemConstants;
import com.eqtron.Management.System.rest.UserRest;
import com.eqtron.Management.System.service.UserService;
import com.eqtron.Management.System.utils.SystemUtils;
import com.eqtron.Management.System.wrapper.ProductWrapper;
import com.eqtron.Management.System.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {
    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
        try {
            return userService.signup(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @Override
    public ResponseEntity<List<UserWrapper>> getAllusers() {
        try {
            return userService.getAllusers();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            return userService.update(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checktoken() {
        try {
            return userService.checkToken();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changepassword(Map<String, String> requestMap) {
        try {
            return userService.changepassword(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgetpassword(Map<String, String> requestMap) {
        try {
return userService.forgetpassword(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}