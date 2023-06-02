package com.eqtron.Management.System.service;

import com.eqtron.Management.System.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<String>signup(Map<String,String>requestMap);
    ResponseEntity<String>login(Map<String,String>requestMap);
    ResponseEntity<List<UserWrapper>>getAllusers();
    ResponseEntity<String>update(Map<String,String>requestMap);

    ResponseEntity<String> checkToken();

    ResponseEntity<String> changepassword(Map<String,String>requestMap);

    ResponseEntity<String> forgetpassword(Map<String, String> requestMap);
}
