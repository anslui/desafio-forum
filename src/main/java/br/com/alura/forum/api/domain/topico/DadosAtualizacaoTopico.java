package br.com.alura.forum.api.domain.topico;

public record DadosAtualizacaoTopico(
        String titulo,
        String mensagem,
        Long idAutor,
        Long idCurso
) {
}
