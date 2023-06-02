package com.eqtron.Management.System.rest;

import com.eqtron.Management.System.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {
    @PostMapping(path = "/signup")
public ResponseEntity<String>signup(@RequestBody(required = true)Map<String,String>requestMap);


    @PostMapping(path = "/login")
    public ResponseEntity<String>login(@RequestBody(required = true)Map<String,String>requestMap);
@GetMapping(path = "/get")

    public ResponseEntity<List<UserWrapper>> getAllusers();
@PostMapping(path = "/update")
public ResponseEntity<String>update(@RequestBody(required = true)Map<String,String>requestMap);
@GetMapping(path = "/checktoken")
ResponseEntity<String>checktoken();
    @PostMapping(path = "/changepassword")
    public ResponseEntity<String>changepassword(@RequestBody(required = true)Map<String,String>requestMap);
    @PostMapping(path = "/forgetpassword")
    public ResponseEntity<String>forgetpassword(@RequestBody(required = true)Map<String,String>requestMap);
}
