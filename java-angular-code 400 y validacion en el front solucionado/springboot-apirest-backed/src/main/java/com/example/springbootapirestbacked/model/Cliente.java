package com.example.springbootapirestbacked.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
    public static final long serialversionUID=1L; 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Temporal(TemporalType.DATE)
    private Date createAt;

    public Cliente() {
    }

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

    @PrePersist
    public void prePersist(){
        createAt= new Date();
    }

}
