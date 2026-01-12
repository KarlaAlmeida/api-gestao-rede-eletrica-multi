package br.edu.infnet.multi.model.repository;

import br.edu.infnet.multi.model.domain.entities.Tecnico;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

    @Override
    @EntityGraph(attributePaths = {"ordensServico", "endereco"})
    List<Tecnico> findAll();

    @EntityGraph(attributePaths = {"ordensServico", "endereco"})
    Optional<Tecnico> findByCpf(String cpf);

    @EntityGraph(attributePaths = {"ordensServico", "endereco"})
    List<Tecnico> findByNomeStartingWithIgnoreCaseAndEspecialidadeIgnoreCase(
            String prefixoNome, String especialidade);
}
