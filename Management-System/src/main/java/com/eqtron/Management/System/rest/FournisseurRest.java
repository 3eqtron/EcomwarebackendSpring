package com.eqtron.Management.System.rest;

import com.eqtron.Management.System.pojo.Category;
import com.eqtron.Management.System.pojo.Fournisseur;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "fournisseur")
public interface FournisseurRest {
    @PostMapping(path = "/add")
    ResponseEntity<String>addNewFournisseur(@RequestBody(required = true)
                                         Map<String,String> requestmap
    );

    @GetMapping(path = "/get")
    ResponseEntity<List<Fournisseur>> getAllfournisseur(@RequestParam(required = false)
                                                  String filtervalue);

    @PostMapping(path = "/update")
    ResponseEntity<String>updatefournisseur(@RequestBody (required = true)
                                         Map<String,String>requestmap
    );
}
