package br.edu.infnet.multi.model.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OcorrenciaRequestDTO {

    @NotNull(message = "O ID do ativo é obrigatório.")
    private Integer ativoId;

    @NotBlank(message = "A descrição da ocorrência é obrigatória.")
    private String descricaoOcorrencia;

    @NotBlank(message = "A prioridade da ocorrência é obrigatória e deve ser BAIXA, MEDIA, ALTA ou CRITICA.")
    private String prioridadeOcorrencia;

}
