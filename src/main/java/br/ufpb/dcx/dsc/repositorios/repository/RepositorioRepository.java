package br.ufpb.dcx.dsc.repositorios.repository;

import br.ufpb.dcx.dsc.repositorios.models.Repositorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositorioRepository extends JpaRepository<Repositorio, Long> {
    @Query("SELECT r FROM Repositorio r WHERE r.organizacao.orgId = :orgId")
    List<Repositorio> findByOrganizationId(Long orgId);

}
