package br.edu.infnet.multi.model.dto.out;

import br.edu.infnet.multi.model.domain.entities.Ocorrencia;
import br.edu.infnet.multi.model.domain.enums.PrioridadeOcorrencia;
import br.edu.infnet.multi.model.domain.enums.StatusOcorrencia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OcorrenciaResponseDTO {

    private Integer id;

    private AtivoResponseDTO ativo;

    private String descricaoOcorrencia;
    private PrioridadeOcorrencia prioridadeOcorrencia;
    private LocalDate dataRegistroOcorrencia;
    private StatusOcorrencia statusOcorrencia;

    private List<OrdemServicoResponseDTO> ordensServico = new ArrayList<>();
    
    public OcorrenciaResponseDTO(Ocorrencia ocorrencia){
        this.setId(ocorrencia.getId());
        this.ativo = new AtivoResponseDTO(ocorrencia.getAtivo());
        this.setDescricaoOcorrencia(ocorrencia.getDescricaoOcorrencia());
        this.setPrioridadeOcorrencia(ocorrencia.getPrioridadeOcorrencia());
        this.setDataRegistroOcorrencia(ocorrencia.getDataRegistroOcorrencia());
        this.setStatusOcorrencia(ocorrencia.getStatusOcorrencia());
    }

    @Override
    public String toString() {
        return String.format("Ocorrência - ID: %d - %s - Descrição: %s - Data da Ocorrência %s" +
                        " Prioridade: %s - Status: %s",
                id, ativo, descricaoOcorrencia, dataRegistroOcorrencia,
                prioridadeOcorrencia, statusOcorrencia);
    }
}
