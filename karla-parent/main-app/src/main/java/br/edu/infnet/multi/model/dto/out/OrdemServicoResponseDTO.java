package br.edu.infnet.multi.model.dto.out;


import br.edu.infnet.multi.model.domain.entities.OrdemServico;
import br.edu.infnet.multi.model.domain.enums.StatusOS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServicoResponseDTO {

    private Integer id;
    private OcorrenciaResponseDTO ocorrencia;
    private TecnicoResponseDTO tecnico;
    private String descricaoServico;
    private LocalDate dataCriacaoOS;
    private LocalDate dataConclusaoOS;
    private StatusOS statusOS;

    public OrdemServicoResponseDTO(OrdemServico ordemServico){
        this.setId(ordemServico.getId());
        this.ocorrencia = new OcorrenciaResponseDTO(ordemServico.getOcorrencia());
        this.tecnico = new TecnicoResponseDTO(ordemServico.getTecnico());
        this.setDescricaoServico(ordemServico.getDescricaoServico());
        this.setDataCriacaoOS(ordemServico.getDataCriacaoOS());
        this.setDataConclusaoOS(ordemServico.getDataConclusaoOS());
        this.setStatusOS(ordemServico.getStatusOS());
    }



    @Override
    public String toString() {
        return String.format("Ordem de Serviço - ID: %d - %s - %s - Descrição Serviço %s - " +
                        "Data criação OS: %s - Data conclusão OS: %s - Status: %s",
                id, ocorrencia, tecnico, descricaoServico, dataCriacaoOS, dataConclusaoOS, statusOS);
    }
}
