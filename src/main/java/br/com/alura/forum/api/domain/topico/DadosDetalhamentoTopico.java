package br.com.alura.forum.api.domain.topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String curso,
        String titulo,
        String mensagem,
        LocalDateTime dataDeCriacao
                                      ) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getCurso().getNome(), topico.getTitulo(), topico.getMensagem(), topico.getDataDeCriacao());
    }
}
