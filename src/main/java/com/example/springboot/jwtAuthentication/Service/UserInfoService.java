package com.example.springboot.jwtAuthentication.Service;

import com.example.springboot.jwtAuthentication.Model.UserInfo;
import com.example.springboot.jwtAuthentication.Repository.UserInfoRepository;
import com.example.springboot.jwtAuthentication.Security.Config.UserInfoDetails;
import com.example.springboot.jwtAuthentication.Security.JWT.JwtService;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtService jwtService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByUsername(username);
        return userInfo.map(UserInfoDetails::new).orElseThrow( ()-> new UsernameNotFoundException("User not found" +username));
    }


    public UserInfo saveUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setRoles("ROLE_USER");
        return userInfoRepository.save(userInfo);
    }


    public List<UserInfo> getAllUserList(){
        return userInfoRepository.findAll();
    }

    public UserInfo getUserById(int id){
        return userInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("User with given id is not found on server"));
    }


    public void deleteUserInfo (int id){
        userInfoRepository.deleteById(id);
    }


    public UserInfo userDetails(int id,String authToken){
         String token = authToken.substring(7);
         Claims claims = jwtService.extractAllClaims(token);
         String username = claims.getSubject();
         UserInfo userInfo = this.searchUserByName(username);

         if (userInfo.getId()==id) {
            return userInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("User with given id is not found on server"));
         }else{
            UserInfo userInfo2 = null;
            return userInfo2;
         }
    }


    private UserInfo searchUserByName(String username){
        return userInfoRepository.findByUsername(username).orElseThrow( ()-> new UsernameNotFoundException("User not found" +username));
    }
}
