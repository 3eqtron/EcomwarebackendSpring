package com.eqtron.Management.System.rest;

import com.eqtron.Management.System.pojo.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequestMapping(path = "/Bill")
public interface BillRest {
    @PostMapping(path = "/generateReport")
    ResponseEntity<String> generateReport (@RequestBody Map<String, Object> requestMap);
    @GetMapping(path = "/getbills")
    ResponseEntity<List<Bill>> getBills();
    @PostMapping(path = "/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String,Object> requestMap);
}
