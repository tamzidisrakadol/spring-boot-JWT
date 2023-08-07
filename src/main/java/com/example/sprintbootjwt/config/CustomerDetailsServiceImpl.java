package com.example.sprintbootjwt.config;

import com.example.sprintbootjwt.modal.Customer;
import com.example.sprintbootjwt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = userRepo.getUserByEmail(email);
        if (customer==null){
            throw new UsernameNotFoundException("Could not found user");
        }
        return new CustomerDetails(customer);
    }
}
