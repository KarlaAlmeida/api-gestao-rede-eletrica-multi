package br.edu.infnet.multi.model.service;


import br.edu.infnet.multi.model.domain.entities.Ocorrencia;
import br.edu.infnet.multi.model.domain.entities.OrdemServico;
import br.edu.infnet.multi.model.domain.entities.Tecnico;
import br.edu.infnet.multi.model.domain.enums.StatusOS;
import br.edu.infnet.multi.model.dto.in.OrdemServicoAlteraDataConclusaoDTO;
import br.edu.infnet.multi.model.dto.in.OrdemServicoRequestDTO;
import br.edu.infnet.multi.model.dto.out.OrdemServicoResponseDTO;
import br.edu.infnet.multi.model.exceptions.ResourceNotFoundException;
import br.edu.infnet.multi.model.repository.OcorrenciaRepository;
import br.edu.infnet.multi.model.repository.OrdemServicoRepository;
import br.edu.infnet.multi.model.repository.TecnicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;
    private final OcorrenciaRepository ocorrenciaRepository;
    private final TecnicoRepository tecnicoRepository;

    public OrdemServicoService(OrdemServicoRepository ordemServicoRepository,
                               OcorrenciaRepository ocorrenciaRepository,
                               TecnicoRepository tecnicoRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    public OrdemServicoResponseDTO incluir(OrdemServicoRequestDTO dto) {

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(dto.getOcorrenciaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ocorrência não encontrada pelo ID " + dto.getOcorrenciaId()));

        Tecnico tecnico = tecnicoRepository.findByCpf(dto.getCpfTecnico())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Técnico não encontrado pelo CPF " + dto.getCpfTecnico()));

        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setOcorrencia(ocorrencia);
        ordemServico.setTecnico(tecnico);
        ordemServico.setDataCriacaoOS(LocalDate.now());
        ordemServico.setDescricaoServico(dto.getDescricaoServico());
        ordemServico.setStatusOS(StatusOS.ABERTA);

        return new OrdemServicoResponseDTO(ordemServicoRepository.save(ordemServico));
    }

    public OrdemServicoResponseDTO alterar(Integer id, OrdemServicoRequestDTO dto) {

        OrdemServico ordemServico = ordemServicoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(
                        "A ordem de serviço com ID " + id + " não foi encontrada."));
        ordemServico.setId(id);

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(dto.getOcorrenciaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ocorrência não encontrada pelo ID " + dto.getOcorrenciaId()));
        ocorrencia.setId(id);

        Tecnico tecnico = tecnicoRepository.findByCpf(dto.getCpfTecnico())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Técnico não encontrado pelo CPF " + dto.getCpfTecnico()));
        tecnico.setId(id);

        ordemServico.setOcorrencia(ocorrencia);
        ordemServico.setTecnico(tecnico);
        ordemServico.setDescricaoServico(dto.getDescricaoServico());

        return new OrdemServicoResponseDTO(ordemServicoRepository.save(ordemServico));
    }

    public OrdemServicoResponseDTO alterarStatus(Integer id, String statusNovo){
        OrdemServico ordemServico = ordemServicoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(
                        "A ordem de serviço com ID " + id + " não foi encontrada."));
        ordemServico.setId(id);

        StatusOS statusOSNovo = StatusOS.fromString(statusNovo);

        if(statusOSNovo.equals(ordemServico.getStatusOS())){
            throw new IllegalStateException("O status atual da ordem de serviço já é " + statusOSNovo);
        }

        ordemServico.setStatusOS(statusOSNovo);

        return new OrdemServicoResponseDTO(ordemServicoRepository.save(ordemServico));
    }

    public OrdemServicoResponseDTO alterarDataConclusao(Integer id, OrdemServicoAlteraDataConclusaoDTO dataConclusaoDTO){
        OrdemServico ordemServico = ordemServicoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(
                        "A ordem de serviço com ID " + id + " não foi encontrada."));
        ordemServico.setId(id);

        if(dataConclusaoDTO.equals(ordemServico.getDataConclusaoOS())){
            throw new IllegalStateException(
                    "A data de conclusão atual da ordem de serviço já é " + dataConclusaoDTO);
        }

        ordemServico.setDataConclusaoOS(dataConclusaoDTO.getDataConclusaoOS());

        return new OrdemServicoResponseDTO(ordemServicoRepository.save(ordemServico));
    }

    public OrdemServicoResponseDTO obterPorId(Integer id) {
        return new OrdemServicoResponseDTO(ordemServicoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("A ordem de serviço com ID " + id + " não foi encontrada.")));
    }

    public List<OrdemServicoResponseDTO> obterLista() {
        return ordemServicoRepository.findAll()
                .stream()
                .map(OrdemServicoResponseDTO::new) // chama o construtor DTO(Tecnico)
                .toList();
    }

    public List<OrdemServicoResponseDTO> listarPorTecnico(String cpf) {
        return ordemServicoRepository.findByTecnicoCpf(cpf)
                .stream()
                .map(OrdemServicoResponseDTO::new) // chama o construtor DTO(Tecnico)
                .toList();
    }

    public List<OrdemServicoResponseDTO> filtrarPorDescricaoServicoEPeriodo(
            String descricaoBusca, LocalDate dataInicio, LocalDate dataFim) {
        return ordemServicoRepository
                .findByDescricaoServicoContainingIgnoreCaseAndDataCriacaoOSBetween(
                        descricaoBusca, dataInicio, dataFim)
                .stream()
                .map(OrdemServicoResponseDTO::new) // chama o construtor DTO(Tecnico)
                .toList();
    }

    public void excluir(Integer id) {
        OrdemServico ordemServico = ordemServicoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(
                        "A ordem de serviço com ID " + id + " não foi encontrada."));
        ordemServicoRepository.delete(ordemServico);
    }

}
