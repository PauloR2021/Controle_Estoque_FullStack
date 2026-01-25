package com.pauloricardo.api.Controller.Autenticacao;

import com.pauloricardo.api.DTO.Autenticacao.AutenticacaoDTO;
import com.pauloricardo.api.DTO.Autenticacao.LoginResponseDTO;
import com.pauloricardo.api.DTO.Usuario.ResetSenhaDTO;
import com.pauloricardo.api.DTO.Usuario.UsuarioDTO;
import com.pauloricardo.api.DTO.Usuario.ValidacaoEmailDTO;
import com.pauloricardo.api.DTO.Usuario.ValidarCodigoDTO;
import com.pauloricardo.api.Model.Usuario.UsuarioModel;
import com.pauloricardo.api.Respository.UsuarioRepositrory;
import com.pauloricardo.api.Service.Autenticacao.TokenService;
import com.pauloricardo.api.Service.Usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private UsuarioRepositrory usuarioRepositrory;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO dto){

        var user = usuarioRepositrory.findByUsername(dto.username())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!user.isAtivo()) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Usuário não ativado. Verifique seu e-mail.");
        }

        var userNamePassword =
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password());

        var auth = authenticationManager.authenticate(userNamePassword);

        var token = tokenService.gerarToken((UsuarioModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UsuarioDTO dto){

        if(usuarioRepositrory.findByUsername(dto.username()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username Já Existe");
        }

        usuarioService.cadastararUsuario(dto);

        return ResponseEntity.ok("Usuário criado. Verifique seu e-mail!");
    }

    @PostMapping("/validar-codigo")
    public ResponseEntity<?> validarCodigo(@RequestBody ValidarCodigoDTO dto) {
        usuarioService.validarCodigo(dto);
        return ResponseEntity.ok("Usuário ativado com sucesso!");
    }

    @PostMapping("/reset-codigo")
    public ResponseEntity resetCode(@RequestBody @Valid ValidacaoEmailDTO dto){
        usuarioService.solicitandoReset(dto.email());
        return ResponseEntity.ok("Código Enviado para o E-mail");
    }


    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@RequestBody @Valid ResetSenhaDTO dto){
        usuarioService.resetSenha(dto.email(),dto.code(),dto.novaSenha());
        return ResponseEntity.ok("Senha Alterada com Sucesso");
    }




}
