package com.pauloricardo.api.Service.Autenticacao;

import com.pauloricardo.api.Model.Usuario.UsuarioModel;
import com.pauloricardo.api.Respository.UsuarioRepositrory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepositrory usuarioRepositrory;

    //Veriifca que tipo de usuário é

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsuarioModel usuario = usuarioRepositrory.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if (!usuario.isAtivo()) {
            throw new DisabledException("Usuário não está ativo");
        }

        return usuario;

    }
}
