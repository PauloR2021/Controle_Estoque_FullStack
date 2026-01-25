package com.pauloricardo.api.Service.Usuario;


import com.pauloricardo.api.DTO.Usuario.UsuarioDTO;
import com.pauloricardo.api.DTO.Usuario.ValidarCodigoDTO;
import com.pauloricardo.api.Model.Usuario.UsuarioModel;
import com.pauloricardo.api.Model.Usuario.UsuarioRole;
import com.pauloricardo.api.Respository.UsuarioRepositrory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepositrory usuarioRepositrory;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;


    @Transactional
    public void cadastararUsuario(UsuarioDTO dto){
        String codigo = gerarCodigo();

        UsuarioModel usuario= new UsuarioModel();

        String encryptedPassword = passwordEncoder.encode(dto.password());

        usuario.setNome(dto.nome());
        usuario.setUsername(dto.username());
        usuario.setPassword(encryptedPassword);
        usuario.setRole(UsuarioRole.USER);
        usuario.setEmail(dto.email());
        usuario.setAtivo(false);
        usuario.setCodigoVerificacao(codigo);
        usuario.setExpiraEm(LocalDateTime.now().plusMinutes(10));

        usuarioRepositrory.save(usuario);

        // ✅ ENVIA O EMAIL
        emailService.enviarCodigo(dto.email(), codigo);
    }

    @Transactional
    public void validarCodigo(ValidarCodigoDTO dto) {

        UsuarioModel usuario = usuarioRepositrory.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (usuario.getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Código expirado");
        }

        if (!usuario.getCodigoVerificacao().equals(dto.codigo())) {
            throw new RuntimeException("Código inválido");
        }

        usuario.setAtivo(true);
        usuario.setCodigoVerificacao(null);
        usuario.setExpiraEm(null);

        usuarioRepositrory.save(usuario);
    }

    public String gerarCodigo(){
        return String.valueOf(
                (int)(Math.random() * 900000) + 100000
        );
    }




}
