package br.edu.infnet.multi.model.dto.out;


import br.edu.infnet.multi.model.domain.entities.Ativo;
import br.edu.infnet.multi.model.domain.enums.StatusAtivo;
import br.edu.infnet.multi.model.domain.enums.TipoAtivo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AtivoResponseDTO {

    private Integer id;
    private TipoAtivo tipoAtivo;
    private StatusAtivo statusAtivo;
    private LocalDate dataInstalacao;
    private EnderecoGeorreferenciadoResponseDTO endereco;

    public AtivoResponseDTO(Ativo ativo) {
        this.setId(ativo.getId());
        this.setTipoAtivo(ativo.getTipoAtivo());
        this.setStatusAtivo(ativo.getStatusAtivo());
        this.setDataInstalacao(ativo.getDataInstalacao());
        this.setEndereco(new EnderecoGeorreferenciadoResponseDTO(ativo.getEndereco()));
    }


}

