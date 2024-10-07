package com.example.springboot.jwtAuthentication.Repository;


import com.example.springboot.jwtAuthentication.Model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {


    // custom query
    // @Query("select u from User u where u.email = :email")

    @Query(value ="SELECT * FROM userinfo WHERE username = ?",nativeQuery = true)
    Optional <UserInfo> findByUsername(String username);

}
