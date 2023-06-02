package com.eqtron.Management.System.dao;

import com.eqtron.Management.System.pojo.User;
import com.eqtron.Management.System.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {
User FindByEmailId(@Param("email")String email);

   List<UserWrapper>getAllusers();
   List<String>getAlladmin();
   @Transactional
   @Modifying

   Integer updateStatus(@Param("status")String status ,@Param("id")Integer id);
   User findByEmail(String email);
}
