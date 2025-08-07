package br.com.alura.forum.api.domain.topico;

import br.com.alura.forum.api.domain.curso.Curso;
import br.com.alura.forum.api.domain.curso.CursoRepository;
import br.com.alura.forum.api.domain.usuario.DadosPerfilUsuario;
import br.com.alura.forum.api.domain.usuario.Usuario;
import br.com.alura.forum.api.domain.usuario.UsuarioRepository;
import br.com.alura.forum.api.domain.ValidacaoException;
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

    public DadosRetornoTopico cadastrar(@Valid DadosCadastroTopico dados, DadosPerfilUsuario dadosUsuario) {
        if (!cursoRepository.existsById(dados.idCurso())){
            throw new ValidacaoException("Id do curso informado não existe.");
        }
        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new ValidacaoException("Já existe um tópico com este título e mensagem.");
        }
        Curso curso = cursoRepository.getReferenceById(dados.idCurso());
        Usuario usuario = usuarioRepository.getReferenceById(dadosUsuario.id());

        Topico topico = new Topico(dados.titulo(), dados.mensagem(), usuario, curso);
        topicoRepository.save(topico);
        return new DadosRetornoTopico(topico);
    }

    public DadosListagemTopico atualizar(Long id,
                                         DadosAtualizacaoTopico dados,
                                         DadosPerfilUsuario dadosUsuario) {
        Topico topico = topicoRepository.getReferenceById(id);
        if (!topico.isAutor(dadosUsuario.id())){
            throw new ValidacaoException("Usuário não é o autor do tópico.");
        }

        Curso novoCurso = null;
        if (dados.idCurso() != null ){
            novoCurso = cursoRepository.findById(dados.idCurso())
                    .orElseThrow(() -> new ValidacaoException("Id do curso informado não existe."));
        }

        String titulo = dados.titulo() != null? dados.titulo() : topico.getTitulo();
        String mensagem = dados.mensagem() != null? dados.mensagem():topico.getMensagem();

        boolean jaExiste = topicoRepository.existsByTituloAndMensagem(titulo, mensagem);

        if (jaExiste) {
            throw new ValidacaoException("Já existe um tópico com este título e mensagem.");
        }

        topico.atualizar(dados, novoCurso);
        return new DadosListagemTopico(topico);
    }

    public void desativar(Long id, DadosPerfilUsuario dados) {
        Topico topico = topicoRepository.getReferenceById(id);

        if (!topico.isAutor(dados.id())){
            throw new ValidacaoException("Usuário não é o autor do tópico.");
        }
        topico.desativar();
    }

}
