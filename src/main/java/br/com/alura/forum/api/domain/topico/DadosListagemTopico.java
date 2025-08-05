package br.com.alura.forum.api.domain.topico;

import br.com.alura.forum.api.domain.curso.Curso;
import br.com.alura.forum.api.domain.usuario.DadosPerfilUsuario;

import java.time.LocalDateTime;

public record DadosListagemTopico(Long id,
                                  String titulo,
                                  String mensagem,
                                  LocalDateTime dataDeCriacao,
                                  Boolean estado,
                                  DadosPerfilUsuario autor,
                                  Curso curso) {
    public DadosListagemTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataDeCriacao(), topico.isEstado(), new DadosPerfilUsuario(topico.getAutor()), topico.getCurso());
    }
}
