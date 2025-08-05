package br.com.alura.forum.api.domain.topico;

import br.com.alura.forum.api.domain.curso.Curso;
import br.com.alura.forum.api.domain.curso.CursoRepository;
import br.com.alura.forum.api.domain.usuario.Usuario;
import br.com.alura.forum.api.domain.usuario.UsuarioRepository;
import br.com.alura.forum.api.infra.ValidacaoException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public DadosRetornoTopico cadastrar(@Valid DadosCadastroTopico dados) {
        if (!cursoRepository.existsById(dados.idCurso())){
            throw new ValidacaoException("Id do curso informado não existe.");
        }
        if (!usuarioRepository.existsById(dados.idAutor())){
            throw new ValidacaoException("Id do usuário informado não está existe.");
        }
        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new ValidacaoException("Já existe um tópico com este título e mensagem.");
        }
        Curso curso = cursoRepository.getReferenceById(dados.idCurso());
        Usuario usuario = usuarioRepository.getReferenceById(dados.idAutor());

        Topico topico = new Topico(dados.titulo(), dados.mensagem(), usuario, curso);
        topicoRepository.save(topico);
        return new DadosRetornoTopico(topico);
    }

    public DadosListagemTopico atualizar(Long id, DadosAtualizacaoTopico dados){
        var topico = topicoRepository.getReferenceById(id);

        Curso novoCurso = null;
        if (dados.idCurso() != null ){
            novoCurso = cursoRepository.findById(dados.idCurso())
                    .orElseThrow(() -> new ValidacaoException("Id do curso informado não existe."));
        }
        Usuario novoAutor = null;
        if (dados.idAutor() != null){
            novoAutor = usuarioRepository.findById(dados.idAutor())
                    .orElseThrow(() -> new ValidacaoException("Id do curso informado não existe."));
        }

        String titulo = dados.titulo() != null? dados.titulo() : topico.getTitulo();
        String mensagem = dados.mensagem() != null? dados.mensagem():topico.getMensagem();

        boolean jaExiste = topicoRepository.existsByTituloAndMensagem(titulo, mensagem);

        if (jaExiste) {
            throw new ValidacaoException("Já existe um tópico com este título e mensagem.");
        }

        topico.atualizar(dados, novoAutor, novoCurso);
        return new DadosListagemTopico(topico);
    }
}
