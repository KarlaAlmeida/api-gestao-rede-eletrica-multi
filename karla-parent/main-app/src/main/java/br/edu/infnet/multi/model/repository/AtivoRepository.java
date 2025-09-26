package br.edu.infnet.multi.model.repository;

import br.edu.infnet.multi.model.domain.entities.Ativo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Integer> {

    @Override
    @EntityGraph(attributePaths = {"endereco"})
    List<Ativo> findAll();

    @Override
    @EntityGraph(attributePaths = {"endereco"})
    Optional<Ativo> findById(Integer id);

}
