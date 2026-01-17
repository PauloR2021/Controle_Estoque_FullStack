package com.pauloricardo.api.Respository;

import com.pauloricardo.api.Model.Usuario.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositrory extends JpaRepository<UsuarioModel, Integer> {
}
