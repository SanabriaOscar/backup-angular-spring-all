package com.example.springbootapirestbacked.repository;

import com.example.springbootapirestbacked.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
// @Query("select u from ")
}
