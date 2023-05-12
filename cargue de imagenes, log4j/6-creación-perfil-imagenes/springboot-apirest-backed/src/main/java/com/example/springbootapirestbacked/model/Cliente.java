package com.example.springbootapirestbacked.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
    public static final long serialversionUID=1L; 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private  String nombre;
    @NotEmpty(message = "El apellido no puede estar vacío")
    @Column(nullable = false)
    private  String apellido;
    @NotEmpty(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    @Column(nullable = false, unique = true)
    private  String email;
    @Column(name = "create_at",nullable = false)
    @NotNull(message = "La fecha no puede ser nula")
    @Temporal(TemporalType.DATE)
    private Date createAt;
    private String foto;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Cliente() {
    }
   /* @PrePersist
    public void prePersist(){
        createAt= new Date();
    }*/
    public Cliente(long l, String juan, String salazar, String s) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }



}
