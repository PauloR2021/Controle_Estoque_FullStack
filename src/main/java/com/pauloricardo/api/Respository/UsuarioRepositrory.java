package com.pauloricardo.api.Respository;

import com.pauloricardo.api.Model.Usuario.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositrory extends JpaRepository<UsuarioModel, Integer> {
    //Veririca se o Usuário Existe
    Optional<UsuarioModel> findByUsername(String username);
}
