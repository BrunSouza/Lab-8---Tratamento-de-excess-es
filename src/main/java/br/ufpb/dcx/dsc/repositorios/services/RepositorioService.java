package br.ufpb.dcx.dsc.repositorios.services;

import br.ufpb.dcx.dsc.repositorios.api.ResourceNotFoundException;
import br.ufpb.dcx.dsc.repositorios.dto.RepositorioDTO;
import br.ufpb.dcx.dsc.repositorios.models.Organizacao;
import br.ufpb.dcx.dsc.repositorios.models.Repositorio;
import br.ufpb.dcx.dsc.repositorios.repository.OrganizacaoRepository;
import br.ufpb.dcx.dsc.repositorios.repository.RepositorioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepositorioService {

    private final RepositorioRepository repositorioRepository;
    private final OrganizacaoRepository organizacaoRepository;
    private final ModelMapper modelMapper;

    public RepositorioService(RepositorioRepository repositorioRepository, OrganizacaoRepository organizacaoRepository, ModelMapper modelMapper) {
        this.repositorioRepository = repositorioRepository;
        this.organizacaoRepository = organizacaoRepository;
        this.modelMapper = modelMapper;
    }

    public RepositorioDTO getRepositorio(Long id) {
        Repositorio repositorio = repositorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Repositório não encontrado com o ID: " + id));
        return convertToDTO(repositorio);
    }

    public List<RepositorioDTO> listRepositorios(Long organizacaoId) {
        List<Repositorio> repositorios;

        if (organizacaoId != null) {
            repositorios = repositorioRepository.findByOrganizationId(organizacaoId);
        } else {
            repositorios = repositorioRepository.findAll();
        }

        return repositorios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RepositorioDTO saveRepositorio(RepositorioDTO dto) {
        Organizacao organizacao = organizacaoRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Organização não encontrada com o ID: " + dto.getId()));

        Repositorio r = new Repositorio();
        r.setNome(dto.getNome());
        r.setIsPrivate(dto.getIsPrivate());
        r.setOrganizacao(organizacao);

        Repositorio savedRepositorio = repositorioRepository.save(r);
        return convertToDTO(savedRepositorio);
    }

    public void deleteRepositorio(Long id) {
        if (!repositorioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Repositório não encontrado com o ID: " + id);
        }
        repositorioRepository.deleteById(id);
    }

    public RepositorioDTO updateRepositorio(Long id, RepositorioDTO dto) {
        Repositorio toUpdate = repositorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Repositório não encontrado com o ID: " + id));

        toUpdate.setNome(dto.getNome());
        toUpdate.setIsPrivate(dto.getIsPrivate());

        if (dto.getId() != null) {
            Organizacao newOrganizacao = organizacaoRepository.findById(dto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Organização não encontrada com o ID: " + dto.getId()));
            toUpdate.setOrganizacao(newOrganizacao);
        }

        Repositorio updatedRepositorio = repositorioRepository.save(toUpdate);
        return convertToDTO(updatedRepositorio);
    }

    private RepositorioDTO convertToDTO(Repositorio repositorio) {
        RepositorioDTO dto = modelMapper.map(repositorio, RepositorioDTO.class);
        if (repositorio.getOrganizacao() != null) {
            dto.setId(repositorio.getOrganizacao().getOrgId());
        }
        return dto;
    }
}