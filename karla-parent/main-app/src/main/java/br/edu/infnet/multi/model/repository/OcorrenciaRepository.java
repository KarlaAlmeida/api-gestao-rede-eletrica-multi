package br.edu.infnet.multi.model.repository;


import br.edu.infnet.multi.model.domain.entities.Ocorrencia;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Integer> {

    @Override
    @EntityGraph(attributePaths = {"ordensServico", "ativo", "ativo.endereco"})
    List<Ocorrencia> findAll();

    @Override
    @EntityGraph(attributePaths = {"ordensServico", "ativo", "ativo.endereco"})
    Optional<Ocorrencia> findById(Integer id);
}
