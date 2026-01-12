package br.edu.infnet.multi.model.service;

import br.edu.infnet.multi.model.domain.entities.Ativo;
import br.edu.infnet.multi.model.domain.entities.Ocorrencia;
import br.edu.infnet.multi.model.domain.enums.PrioridadeOcorrencia;
import br.edu.infnet.multi.model.domain.enums.StatusOcorrencia;
import br.edu.infnet.multi.model.dto.in.OcorrenciaRequestDTO;
import br.edu.infnet.multi.model.dto.out.OcorrenciaResponseDTO;
import br.edu.infnet.multi.model.exceptions.ResourceNotFoundException;
import br.edu.infnet.multi.model.repository.AtivoRepository;
import br.edu.infnet.multi.model.repository.OcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final AtivoRepository ativoRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository, AtivoRepository ativoRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.ativoRepository = ativoRepository;
    }

     public OcorrenciaResponseDTO incluir(OcorrenciaRequestDTO dto) {

        Ativo ativo = ativoRepository.findById(dto.getAtivoId()).orElseThrow(()->
                 new ResourceNotFoundException("Ativo não encontrado pelo ID " + dto.getAtivoId()));

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setAtivo(ativo);
        ocorrencia.setDescricaoOcorrencia(dto.getDescricaoOcorrencia());
        ocorrencia.setDataRegistroOcorrencia(LocalDate.now());
        ocorrencia.setPrioridadeOcorrencia(PrioridadeOcorrencia.fromString(dto.getPrioridadeOcorrencia()));
        ocorrencia.setStatusOcorrencia(StatusOcorrencia.REGISTRADA);

        return new OcorrenciaResponseDTO(ocorrenciaRepository.save(ocorrencia));
    }

    public OcorrenciaResponseDTO alterar(Integer id, OcorrenciaRequestDTO dto) {

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("A ocorrência com ID " + id + " não foi encontrada."));
        ocorrencia.setId(id);

        Ativo ativo = ativoRepository.findById(dto.getAtivoId()).orElseThrow(()->
                new ResourceNotFoundException("Ativo não encontrado pelo ID " + dto.getAtivoId()));
        ativo.setId(id);

        ocorrencia.setAtivo(ativo);
        ocorrencia.setDescricaoOcorrencia(dto.getDescricaoOcorrencia());
        PrioridadeOcorrencia prioridadeOcorrencia =
                PrioridadeOcorrencia.fromString(dto.getPrioridadeOcorrencia());
        ocorrencia.setPrioridadeOcorrencia(prioridadeOcorrencia);

        return new OcorrenciaResponseDTO(ocorrenciaRepository.save(ocorrencia));
    }

    public OcorrenciaResponseDTO alterarStatus(Integer id, String statusNovo){
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("A ocorrência com ID " + id + " não foi encontrada."));
        ocorrencia.setId(id);

        StatusOcorrencia statusOcorrenciaNovo = StatusOcorrencia.fromString(statusNovo);

        if(statusOcorrenciaNovo.equals(ocorrencia.getStatusOcorrencia())){
            throw new IllegalStateException("O status atual da ocorrência já é " + statusOcorrenciaNovo);
        }

        ocorrencia.setStatusOcorrencia(statusOcorrenciaNovo);

        return new OcorrenciaResponseDTO(ocorrenciaRepository.save(ocorrencia));
    }

    public OcorrenciaResponseDTO obterPorId(Integer id) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("A ocorrência com ID " + id + " não foi encontrada."));
        return new OcorrenciaResponseDTO(ocorrencia);
    }

    public List<OcorrenciaResponseDTO> obterLista() {
        return ocorrenciaRepository.findAll()
                .stream()
                .map(OcorrenciaResponseDTO::new) // chama o construtor DTO(Tecnico)
                .toList();
    }

    public void excluir(Integer id) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("A ocorrência com ID " + id + " não foi encontrada."));
        ocorrenciaRepository.delete(ocorrencia);
    }

}
