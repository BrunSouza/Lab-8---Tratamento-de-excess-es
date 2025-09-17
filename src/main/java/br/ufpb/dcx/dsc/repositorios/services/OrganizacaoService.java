package br.ufpb.dcx.dsc.repositorios.services;

import br.ufpb.dcx.dsc.repositorios.api.ResourceNotFoundException;
import br.ufpb.dcx.dsc.repositorios.dto.OrganizacaoDTO;
import br.ufpb.dcx.dsc.repositorios.models.Organizacao;
import br.ufpb.dcx.dsc.repositorios.repository.OrganizacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizacaoService {

    private final OrganizacaoRepository OrganizacaoRepository;
    private final ModelMapper modelMapper;

    public OrganizacaoService(OrganizacaoRepository OrganizacaoRepository, ModelMapper modelMapper) {
        this.OrganizacaoRepository = OrganizacaoRepository;
        this.modelMapper = modelMapper;
    }

    public List<OrganizacaoDTO> listAllOrganizacaos() {
        List<Organizacao> Organizacaos = OrganizacaoRepository.findAll();
        return Organizacaos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public OrganizacaoDTO createOrganizacao(OrganizacaoDTO OrganizacaoDTO) {
        Organizacao Organizacao = new Organizacao();
        Organizacao.setNome(OrganizacaoDTO.getNome());
        Organizacao savedOrganizacao = OrganizacaoRepository.save(Organizacao);
        return convertToDTO(savedOrganizacao);
    }

    public OrganizacaoDTO updateOrganizacao(Long id, OrganizacaoDTO OrganizacaoDTO) {
        Organizacao existingOrganizacao = OrganizacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organizacao not found with ID: " + id));

        existingOrganizacao.setNome(OrganizacaoDTO.getNome());
        Organizacao updatedOrganizacao = OrganizacaoRepository.save(existingOrganizacao);
        return convertToDTO(updatedOrganizacao);
    }

    public void deleteOrganizacao(Long id) {
        if (!OrganizacaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Organizacao not found with ID: " + id);
        }
        OrganizacaoRepository.deleteById(id);
    }

    private OrganizacaoDTO convertToDTO(Organizacao Organizacao) {
        return modelMapper.map(Organizacao, OrganizacaoDTO.class);
    }
}