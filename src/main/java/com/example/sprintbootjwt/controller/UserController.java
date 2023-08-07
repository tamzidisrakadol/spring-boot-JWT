package com.example.sprintbootjwt.controller;

import com.example.sprintbootjwt.modal.Customer;
import com.example.sprintbootjwt.repo.UserRepo;
import com.example.sprintbootjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/home")
public class UserController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/saveUser")
    public String  createCustomerInfo(@RequestBody Customer customer){
        userService.saveUser(customer);
        return "Successfully added";
    }

    @GetMapping("/showall")
    public List<Customer> getAllCustomerList(){
        return userService.getAllCustomerList();
    }


    @GetMapping("/user/getCustomerid/{email}")
    public Customer getAllCustomerList(@PathVariable("email")String email){
        return userService.getCustomerByEmail(email);
    }
    @GetMapping("/getCustomerid")
    public String getCustomerList(){
        return "200 ok";
    }



}
