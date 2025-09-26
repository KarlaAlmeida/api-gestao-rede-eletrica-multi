package br.edu.infnet.multi.model.domain.entities;


import br.edu.infnet.multi.model.domain.enums.StatusAtivo;
import br.edu.infnet.multi.model.domain.enums.TipoAtivo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TipoAtivo tipoAtivo;

    @Enumerated(EnumType.STRING)
    private StatusAtivo statusAtivo;

    @Column(name = "data_instalacao", length = 100)
    private LocalDate dataInstalacao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private EnderecoGeorreferenciado endereco;

    @Override
    public String toString() {
        return String.format("Ativo - ID: %d - Tipo: %s - Endereço: %s - " +
                        "Data de instalação: %s - Status: %s",
                id, tipoAtivo, endereco, dataInstalacao, statusAtivo);
    }
}
