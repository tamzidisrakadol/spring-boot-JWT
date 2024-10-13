package com.example.springboot.jwtAuthentication.Controller;


import com.example.springboot.jwtAuthentication.Model.AuthRequest;
import com.example.springboot.jwtAuthentication.Model.JwtResponse;
import com.example.springboot.jwtAuthentication.Model.UserInfo;
import com.example.springboot.jwtAuthentication.Security.JWT.JwtService;
import com.example.springboot.jwtAuthentication.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserInfo> createUser(@RequestBody UserInfo userInfo){
        UserInfo userInfo1 = userInfoService.saveUser(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(userInfo1);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin/userList")
    public ResponseEntity<List<UserInfo>> listOfAllUSer(){
        List<UserInfo> userInfoList = userInfoService.getallUserList();
        return ResponseEntity.ok(userInfoList);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAndGenerateToken(@RequestBody AuthRequest authRequest){
        try {
            JwtResponse jwtResponse = new JwtResponse();

            String token = this.doAuthenticate(authRequest.getUsername(), authRequest.getPassword());
            jwtResponse.setToken(token);
            jwtResponse.setUsername(authRequest.getUsername());

            return new ResponseEntity<>(jwtResponse.toString(), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Credentials Invalid !!", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        }
    }

    public String doAuthenticate(String username,String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(username);
        } else {
            throw new BadCredentialsException("Invalid user request!");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id){
        userInfoService.deleteUserInfo(id);
        return ResponseEntity.ok("deleted user");
    }

    
    @GetMapping("/getUserDetails/{id}")
    public ResponseEntity<UserInfo> getUserDetails(@PathVariable("id") int id){
        UserInfo userInfo = userInfoService.userDetails(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(userInfo);
    }

}
