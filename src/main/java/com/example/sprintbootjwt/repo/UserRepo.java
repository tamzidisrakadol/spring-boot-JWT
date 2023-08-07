package com.example.sprintbootjwt.repo;

import com.example.sprintbootjwt.modal.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Customer,Integer> {

    public Customer getUserByEmail(@Param("email") String email);

}
