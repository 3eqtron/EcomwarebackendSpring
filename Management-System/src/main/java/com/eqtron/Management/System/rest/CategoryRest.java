package com.eqtron.Management.System.rest;

import com.eqtron.Management.System.pojo.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "category")
public interface CategoryRest {
    @PostMapping(path = "/add")
    ResponseEntity<String>addNewCategory(@RequestBody (required = true)
                                         Map<String,String>requestmap
                                         );

    @GetMapping(path = "/get")
    ResponseEntity<List<Category>>getallcategory(@RequestParam(required = false)
                                                 String filtervalue);

    @PostMapping(path = "/update")
    ResponseEntity<String>updatecategory(@RequestBody (required = true)
                                         Map<String,String>requestmap
    );
}
