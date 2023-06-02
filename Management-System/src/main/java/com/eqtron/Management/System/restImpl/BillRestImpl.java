package com.eqtron.Management.System.restImpl;

import com.eqtron.Management.System.constents.SystemConstants;
import com.eqtron.Management.System.pojo.Bill;
import com.eqtron.Management.System.rest.BillRest;
import com.eqtron.Management.System.service.BillService;
import com.eqtron.Management.System.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BillRestImpl implements BillRest {

    @Autowired
    BillService billService;
    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try {
return billService.generateReport(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        try {
return billService.getBills();
        }catch (Exception ex){
            ex.printStackTrace();
        }return null;
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
         try {
             return billService.getPdf(requestMap);
         }catch (Exception ex){
             ex.printStackTrace();
         }return null;
    }
}
