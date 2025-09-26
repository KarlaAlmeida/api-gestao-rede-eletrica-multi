package br.edu.infnet.multi.model.domain.entities;


import br.edu.infnet.multi.model.domain.enums.StatusOS;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ocorrencia_id")
    @JsonBackReference
    private Ocorrencia ocorrencia;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    @JsonBackReference
    private Tecnico tecnico;

    @Column(name = "descricao_servico", length = 100)
    private String descricaoServico;

    @Column(name = "data_criacao_OS", length = 100)
    private LocalDate dataCriacaoOS;

    @Column(name = "data_conclusao_OS", length = 100)
    private LocalDate dataConclusaoOS;

    @Enumerated(EnumType.STRING)
    private StatusOS statusOS;

    @Override
    public String toString() {
        return String.format("Ordem de Serviço - ID: %d - %s - %s - Descrição Serviço %s - " +
                        "Data criação OS: %s - Data conclusão OS: %s - Status: %s",
                id, ocorrencia, tecnico, descricaoServico, dataCriacaoOS, dataConclusaoOS, statusOS);
    }
}
