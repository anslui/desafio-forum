package br.com.alura.forum.api.infra.security;
import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
        @NotBlank
        String email,
        @NotBlank
        String senha
) {
}
