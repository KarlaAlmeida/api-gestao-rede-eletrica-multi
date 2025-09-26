package br.edu.infnet.multi.model.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AtivoRequestDTO {

    @NotNull(message = "Tipo de ativo é obrigatório")
    private String tipoAtivo;

    @NotNull(message = "Data de instalação é obrigatória")
    @PastOrPresent(message = "Data de instalação deve estar no passado ou hoje.")
    private LocalDate dataInstalacao;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido. Use o formato XXXXX-XXX")
    private String cep;

}
