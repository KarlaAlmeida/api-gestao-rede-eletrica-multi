package br.edu.infnet.multi.model.dto.in;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TecnicoRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}",
            message = "Telefone inválido. Use o formato (XX) XXXXX-XXXX ou (XX) XXXX-XXXX.")
    private String telefone;

    @PositiveOrZero(message = "Salário deve ser maior ou igual a zero")
    private double ultimoSalario;

    private boolean ativo;

    @NotBlank(message = "Especialidade é obrigatória")
    @Size(max = 30, message = "Especialidade deve ter no máximo 30 caracteres")
    private String especialidade;

    private boolean disponivel;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido. Use o formato XXXXX-XXX")
    private String cep;

    //@NotNull(message = "Número é obrigatória")
    //@Size(max = 10, message = "Número deve ter no máximo 10 caracteres")
    private Integer numero;

    @Size(max = 100, message = "Complemento do número deve ter no máximo 30 caracteres")
    private String complementoNumero;


}
