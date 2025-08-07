package br.com.alura.forum.api.controller;

import br.com.alura.forum.api.domain.usuario.Usuario;
import br.com.alura.forum.api.infra.security.DadosAutenticacao;
import br.com.alura.forum.api.infra.security.DadosValidacaoToken;
import br.com.alura.forum.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity logar(@RequestBody @Valid DadosAutenticacao dados){
        try {
            var autenticacaoToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
            var autenticacao = manager.authenticate(autenticacaoToken);
            var tokenJWT = tokenService.criarToken((Usuario) autenticacao.getPrincipal());
            return ResponseEntity.ok(new DadosValidacaoToken(tokenJWT));

        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
