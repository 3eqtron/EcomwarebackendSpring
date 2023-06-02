package com.eqtron.Management.System.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
@Service
public class JwtUtil {

    private  String secret="eyJzdWIiOiJFUzI1NmluT1RBIiwibmFtZSI6IkpvaG4gRG9lIn0";
    public Date extractExpiration(String token){
        return extractclamis(token,Claims::getExpiration);
    }
    public String extractUserName(String token){
        return extractclamis(token,Claims::getSubject);
    }
    public <T> T extractclamis(String token, Function<Claims, T>claimesResolver){
        final Claims claimes=extractAllClaims(token);
        return claimesResolver.apply(claimes);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }



    public String generateToken(String username,String role){
Map<String,Object>claimes=new HashMap<>();
claimes.put("role",role);
return
createToken(claimes,username);
    }
    private String createToken(Map<String, Object> claimes,String subject){
        return Jwts.builder().setClaims(claimes).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public Boolean validatetoken(String token, UserDetails userDetails) {

    final String username=extractUserName(token);
    return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));

    }
}
