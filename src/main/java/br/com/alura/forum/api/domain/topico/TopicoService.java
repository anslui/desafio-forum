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

    public DadosDetalhamentoTopico cadastrar(@Valid DadosCadastroTopico dados) {
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
        return new DadosDetalhamentoTopico(topico);
    }
}
