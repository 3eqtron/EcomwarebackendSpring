package com.eqtron.Management.System.jwt;

import com.eqtron.Management.System.dao.UserDao;
import com.eqtron.Management.System.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {



    @Autowired
    UserDao userDao;
    private com.eqtron.Management.System.pojo.User userdetails;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
       log.info("Inside loadUserByUsername{}",username);
userdetails=userDao.FindByEmailId(username);
if (!Objects.isNull(userdetails))
    return new User(userdetails.getEmail(),userdetails.getPassword(),new ArrayList<>());
else throw new UsernameNotFoundException("Utilisateur n'existe pas");
    }



    public com.eqtron.Management.System.pojo.User getUserdetails(){

        return userdetails
                ;
    }


}
