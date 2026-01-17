package com.pauloricardo.api.Model.Usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "usuarios")
@Getter @Setter
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true, length = 50)
    private String username;

    @Column(nullable = false,length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private UsuarioRole role;

    private  Boolean ativo = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (ativo == null) ativo = true;
    }


    public UsuarioModel() {
    }

    public UsuarioModel(Integer id, String username, String password, UsuarioRole role, Boolean ativo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.ativo = ativo;
    }


}
