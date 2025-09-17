package br.ufpb.dcx.dsc.repositorios.dto;

import jakarta.validation.constraints.NotBlank;

public class RepositorioDTO {

    @NotBlank
    private String nome;
    private boolean isPrivate;
    private Long id;
    public RepositorioDTO(){
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

}
