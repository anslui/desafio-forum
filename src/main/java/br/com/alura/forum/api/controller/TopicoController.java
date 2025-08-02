package br.com.alura.forum.api.controller;

import br.com.alura.forum.api.domain.topico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados){
        var dto = service.cadastrar(dados);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, direction = Sort.Direction.ASC, sort = "dataDeCriacao") Pageable paginacao){
        var page = topicoRepository.findAllByEstadoTrue(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }
}
