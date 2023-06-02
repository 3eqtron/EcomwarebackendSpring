package com.eqtron.Management.System.serviceImpl;

import com.eqtron.Management.System.constents.SystemConstants;
import com.eqtron.Management.System.dao.BillDao;
import com.eqtron.Management.System.jwt.JwtFilter;
import com.eqtron.Management.System.pojo.Bill;
import com.eqtron.Management.System.service.BillService;
import com.eqtron.Management.System.utils.SystemUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
public class BillServiceImpl implements BillService {
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    BillDao billDao;
    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
      log.info("inside generateReport");
        try {
            String fileName;
            if (validateRequestMap(requestMap)){
                if (requestMap.containsKey("isGenerate")&& !(Boolean)requestMap.get("isGenerate")){
                    fileName= (String) requestMap.get("uuid");
                }else {
                    fileName =SystemUtils.getUUID();
                    requestMap.put("uuid",fileName);
                    insertBill(requestMap);
                }
                String data="Nom :"+requestMap.get("name") +"\n" +"Numero de Contact :"+requestMap.get("contact_num")+"\n"
                        +"Email :"+requestMap.get("email")+"\n"+"Methode de Payement"+requestMap.get("payementmethod");
                Document document=new Document();
                PdfWriter.getInstance(document,new FileOutputStream(SystemConstants.Store_Location+"\\"+fileName+".pdf"));

                document.open();
                setRectangleInPdf(document);
                Paragraph chunk =new Paragraph("System De Management de stock By 3eqtron",getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);
                Paragraph paragraph=new Paragraph(data+"\n \n",getFont("data"));
                document.add(paragraph);
                PdfPTable table= new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);
                JSONArray jsonArray =SystemUtils.getJsonrrayFromString((String) requestMap.get("productDetails"));
                for (int i = 0; i< jsonArray.length();i++){
                 addRows(table,SystemUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);
                Paragraph footer =new Paragraph("Total :"+requestMap.get("total")+"\n"+
                        "Merci pour votre Visite",getFont("data"));
                document.add(footer);
                document.close();
                return new ResponseEntity<>("{\"uuid\":\""+fileName+"\"}",HttpStatus.OK);
            }
            return SystemUtils.getResponseEntity("Donnes requis ne existe pas",HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }return SystemUtils.getResponseEntity(SystemConstants.Erreur_Servi, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private void addRows(PdfPTable table, Map<String, Object> data) {

        log.info("inside addRows");
        table.addCell((String)data.get("name"));
        table.addCell((String)data.get("category"));
        table.addCell((String)data.get("quantity"));
        table.addCell(Double.toString((Double)data.get("price")));
        table.addCell(Double.toString((Double)data.get("total")));


    }

    private void addTableHeader(PdfPTable table) {
    log.info("inside Tableheader");
        Stream.of("Nom","Category","Quantite","prix","Total")
                .forEach(columnTitle ->
                        {
                            PdfPCell header=new PdfPCell();
                            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            header.setBorderWidth(2);
                            header.setPhrase(new Phrase(columnTitle));
                            header.setBackgroundColor(BaseColor.CYAN);
                            header.setHorizontalAlignment(Element.ALIGN_CENTER);
                            header.setVerticalAlignment(Element.ALIGN_CENTER);
                            table.addCell(header);
                        }
                        );}

    private Font getFont(String type) {
        log.info("inside getfont");
        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
        dataFont.setStyle(Font.BOLD);
        return dataFont;
            default:
                return new Font();
        }
    }

    private void setRectangleInPdf(Document document)throws DocumentException {
    log.info("inside setRectangleInPdf");
        Rectangle rect =new Rectangle(577,825,18,15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);
    }

    private void insertBill(Map<String, Object> requestMap) {
    try {
        Bill bill=new Bill();
        bill.setUuid((String)requestMap.get("uuid"));
        bill.setName((String)requestMap.get("name"));
        bill.setEmail((String)requestMap.get("email"));
        bill.setContact_num((String)requestMap.get("contact_num"));
        bill.setPayementmethod((String)requestMap.get("payementmethod"));
        bill.setProductDetails((String)requestMap.get("productDetails"));
        bill.setTotal(Integer.parseInt((String)requestMap.get("total")));
        bill.setCreatedby(jwtFilter.getCurrentUser());
        billDao.save(bill);


    }catch (Exception ex){
        ex.printStackTrace();
    }

    }


    private boolean validateRequestMap(Map<String, Object> requestMap) {
    return requestMap.containsKey("name")&&
           requestMap.containsKey("contact_num")&&
           requestMap.containsKey("email")&&
           requestMap.containsKey("payementmethod")&&
           requestMap.containsKey("productDetails")&&
           requestMap.containsKey("total");


    }
    @Override
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> list =new ArrayList<>();
        if (jwtFilter.isAdmin()){
list=billDao.getAllBills();
        }else {
list=billDao.getBillByUserName(jwtFilter.getCurrentUser());
        }return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("inside get Pdf :requestMap {}",requestMap);
        try {
            byte[]byteArray = new byte[0];
            if (!requestMap.containsKey("uuid")&& validateRequestMap(requestMap))
                return new ResponseEntity<>(byteArray,HttpStatus.BAD_REQUEST);
                String filePath=SystemConstants.Store_Location+"\\"+(String) requestMap.get("uuid")+".pdf";
if (SystemUtils.isFileExist(filePath)){
    byteArray =getbyteArray(filePath);
    return new ResponseEntity<>(byteArray,HttpStatus.OK);
}else {
    requestMap.put("isGenerate",false);
    generateReport(requestMap);
    byteArray =getbyteArray(filePath);
    return new ResponseEntity<>(byteArray,HttpStatus.OK);
}
        }catch (Exception ex){
            ex.printStackTrace();
        }return null;

    }

    private byte[] getbyteArray(String filePath)throws Exception {
    File inialFile =new File(filePath);
        InputStream targetStream =new FileInputStream(inialFile);
        byte[]byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;

    }


}
