package com.pauloricardo.api.Controller.Autenticacao;

import com.pauloricardo.api.DTO.Autenticacao.AutenticacaoDTO;
import com.pauloricardo.api.DTO.Autenticacao.LoginResponseDTO;
import com.pauloricardo.api.DTO.Autenticacao.RegistrarDTO;
import com.pauloricardo.api.Model.Usuario.UsuarioModel;
import com.pauloricardo.api.Model.Usuario.UsuarioRole;
import com.pauloricardo.api.Respository.UsuarioRepositrory;
import com.pauloricardo.api.Service.Autenticacao.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private UsuarioRepositrory usuarioRepositrory;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO dto){

        //Criando uma Váriavel com o Username e o Passworod de Login
        var userNamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());

        //Fazendo a Autenticação das Informações
        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token = tokenService.gerarToken((UsuarioModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegistrarDTO dto){

        System.out.println("Entrou no Registrar");

        //Verificando se no Banco de Dados já existe o Usuário
        if(this.usuarioRepositrory.findByUsername(dto.username()).isPresent()){
            return ResponseEntity.badRequest().body("Username already exists");
        }

        //Criptografando a senha que o Usuário Digitou
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());

        //Criando um Novo User
        UsuarioModel user = new UsuarioModel(dto.nome(),dto.username(),encryptedPassword, UsuarioRole.USER, dto.ativo());

        this.usuarioRepositrory.save(user); //Salvando o Usuário no Banco de dados

        return ResponseEntity.ok("User registered successfully");

    }



}
