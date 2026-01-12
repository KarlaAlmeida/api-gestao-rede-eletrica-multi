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
public class OrdemServicoRequestDTO {


    @NotNull(message = "O ID da ocorrência é obrigatório.")
    private Integer ocorrenciaId;

    @NotBlank(message = "O CPF do técnico é obrigatório.")
    private String cpfTecnico;


    @NotBlank(message = "A descrição do serviço é obrigatória.")
    private String descricaoServico;

}
