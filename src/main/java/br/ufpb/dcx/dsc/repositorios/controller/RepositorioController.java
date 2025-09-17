package br.ufpb.dcx.dsc.repositorios.controller;

import br.ufpb.dcx.dsc.repositorios.dto.RepositorioDTO;
import br.ufpb.dcx.dsc.repositorios.services.RepositorioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/repositorios")
public class RepositorioController {

    private final RepositorioService repositorioService;

    public RepositorioController(RepositorioService repositorioService) {
        this.repositorioService = repositorioService;
    }

    @GetMapping("/{repoId}")
    public RepositorioDTO getRepositorio(@PathVariable Long repoId) {
        return repositorioService.getRepositorio(repoId);
    }

    @GetMapping
    public List<RepositorioDTO> getFilteredRepositorios(@RequestParam(name = "organizationId", required = false) Long organizationId) {
        return repositorioService.listRepositorios(organizationId);
    }

    @PostMapping
    public ResponseEntity<RepositorioDTO> createRepositorio(@Valid @RequestBody RepositorioDTO repositorioDTO) {
        RepositorioDTO createdRepo = repositorioService.saveRepositorio(repositorioDTO);
        return new ResponseEntity<>(createdRepo, HttpStatus.CREATED);
    }

    @PutMapping("/{repoID}")
    public RepositorioDTO updateRepositorio(@PathVariable Long repoID, @Valid @RequestBody RepositorioDTO repositorioDTO) {
        return repositorioService.updateRepositorio(repoID, repositorioDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{repoId}")
    public void deleteRepositorio(@PathVariable Long repoId) {
        repositorioService.deleteRepositorio(repoId);
    }
}