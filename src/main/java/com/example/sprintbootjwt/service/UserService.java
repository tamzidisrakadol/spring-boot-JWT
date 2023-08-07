package com.example.sprintbootjwt.service;

import com.example.sprintbootjwt.modal.Customer;
import com.example.sprintbootjwt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserService {

    @Autowired
    UserRepo userRepo;

    public void saveUser(Customer customer){
        userRepo.save(customer);
    }

    public List<Customer> getAllCustomerList(){
        return userRepo.findAll();
    }

    public Customer getCustomerByEmail(String email){
        return userRepo.getUserByEmail(email);
    }
}
