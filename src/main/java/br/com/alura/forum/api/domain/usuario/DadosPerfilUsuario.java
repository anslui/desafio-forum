package br.com.alura.forum.api.domain.usuario;

public record DadosPerfilUsuario(Long id, String nome) {
    public DadosPerfilUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getNome());
    }
}
