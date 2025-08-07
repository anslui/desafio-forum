package br.com.alura.forum.api.controller;

import br.com.alura.forum.api.domain.topico.*;
import br.com.alura.forum.api.domain.usuario.DadosPerfilUsuario;
import br.com.alura.forum.api.domain.usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    TopicoService service;
    @Autowired
    TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados,
                                    @AuthenticationPrincipal Usuario usuario){

        var dto = service.cadastrar(dados, new DadosPerfilUsuario(usuario));
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(direction = Sort.Direction.ASC,
            sort = "dataDeCriacao") Pageable paginacao){

        var page = topicoRepository.findAllByEstadoTrue(paginacao)
                .map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        if (!topico.isEstado()) {
            throw new EntityNotFoundException();
        }
        var dto = new DadosListagemTopico(topico);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id,
                                    @RequestBody @Valid DadosAtualizacaoTopico dados,
                                    @AuthenticationPrincipal Usuario usuario){

        var dto = service.atualizar(id, dados, new DadosPerfilUsuario(usuario));
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id,
                                  @AuthenticationPrincipal Usuario usuario){

        service.desativar(id, new DadosPerfilUsuario(usuario));
        return ResponseEntity.noContent().build();
    }
}
