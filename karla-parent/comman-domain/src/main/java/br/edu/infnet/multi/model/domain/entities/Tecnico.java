package br.edu.infnet.multi.model.domain.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tecnico extends Funcionario {

    @Column(name = "especialidade", length = 100)
    private String especialidade;

    @Column(name = "disponível")
    private boolean disponivel;

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrdemServico> ordensServico = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("%s - Especialidade: %s - Disponível: %s",
                super.toString(), especialidade, disponivel ? "Sim" : "Não");
    }
}
