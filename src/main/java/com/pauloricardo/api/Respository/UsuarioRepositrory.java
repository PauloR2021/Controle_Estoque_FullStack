package com.pauloricardo.api.Respository;

import com.pauloricardo.api.Model.Usuario.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositrory extends JpaRepository<UsuarioModel, Integer> {
    //Veririca se o Usuário Existe
    Optional<UsuarioModel> findByUsername(String username);

    Optional<UsuarioModel> findByEmail(String email);

    // Verifica se o Username existe
    boolean existsByUsername(String username);
}
