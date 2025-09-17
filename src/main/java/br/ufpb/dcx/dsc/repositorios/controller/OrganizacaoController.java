package br.ufpb.dcx.dsc.repositorios.controller;

import br.ufpb.dcx.dsc.repositorios.dto.OrganizacaoDTO;
import br.ufpb.dcx.dsc.repositorios.services.OrganizacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/organizacoes")
public class OrganizacaoController {

    private final OrganizacaoService organizacaoService;

    public OrganizacaoController(OrganizacaoService organizacaoService) {
        this.organizacaoService = organizacaoService;
    }

    @GetMapping
    public List<OrganizacaoDTO> getAllOrganizacaos() {
        return organizacaoService.listAllOrganizacaos();
    }

    @PostMapping
    public ResponseEntity<OrganizacaoDTO> createOrganizacao(@Valid @RequestBody OrganizacaoDTO organizacaoDTO) {
        OrganizacaoDTO createdOrganizacao = organizacaoService.createOrganizacao(organizacaoDTO);
        return new ResponseEntity<>(createdOrganizacao, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public OrganizacaoDTO updateOrganizacao(@PathVariable Long id, @Valid @RequestBody OrganizacaoDTO organizacaoDTO) {
        return organizacaoService.updateOrganizacao(id, organizacaoDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteOrganizacao(@PathVariable Long id) {
        organizacaoService.deleteOrganizacao(id);
    }
}