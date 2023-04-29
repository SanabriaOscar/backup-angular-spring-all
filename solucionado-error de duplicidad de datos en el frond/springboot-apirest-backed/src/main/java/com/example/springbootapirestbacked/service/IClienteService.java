package com.example.springbootapirestbacked.service;

import com.example.springbootapirestbacked.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    public List<Cliente>findAll();
    public Page<Cliente> findAll(Pageable pageable);
    public Cliente save(Cliente cliente);
    public Cliente findById(Long id);
    public void delete(Long id);


}
