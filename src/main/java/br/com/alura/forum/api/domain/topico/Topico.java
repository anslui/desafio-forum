package br.com.alura.forum.api.domain.topico;

import br.com.alura.forum.api.domain.curso.Curso;
import br.com.alura.forum.api.domain.usuario.Usuario;
import br.com.alura.forum.api.infra.ValidacaoException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;
    private LocalDateTime dataDeCriacao;
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Topico(String titulo, String mensagem, Usuario usuario, Curso curso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.autor = usuario;
        this.curso = curso;
        this.dataDeCriacao = LocalDateTime.now();
        this.estado = true;
    }

    public void atualizar(DadosAtualizacaoTopico dados, Usuario novoAutor, Curso novoCurso) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
        if (novoAutor != null) {
            this.autor = novoAutor;
        }
        if (novoCurso != null) {
            this.curso = novoCurso;
        }
    }
    public void desativar() {
        if (!this.estado){
            throw new ValidacaoException("Esse tópico já foi desativado.");
        }
        this.estado = false;
    }
}
