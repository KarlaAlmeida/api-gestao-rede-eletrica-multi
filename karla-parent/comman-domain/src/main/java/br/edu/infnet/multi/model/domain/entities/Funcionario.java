package br.edu.infnet.multi.model.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "cpf", length = 11)
    private String cpf;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "telefone", length = 11)
    private String telefone;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private EnderecoGeorreferenciado endereco;

    @Column(name = "ultimo_salario")
    private double ultimoSalario;

    @Column(name = "ativo")
    private boolean ativo;

    @Override
    public String toString() {
        return String.format("Funcionario %s - ID: %d - CPF: %s - E-mail: %s - Telefone: %s " +
                        "Endereço: %s - Último Salário: R$%.2f - Situação: %s",
                nome, id, cpf, email, telefone, endereco, ultimoSalario,
                ativo ? "Ativo" : "Inativo ");
    }
}
