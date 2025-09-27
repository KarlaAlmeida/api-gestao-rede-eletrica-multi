package br.edu.infnet.multi.model.domain.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EnderecoGeorreferenciado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cep", length = 100)
    private String cep;

    @Column(name = "logradouro", length = 100)
    private String logradouro;

    @Column(name = "complemento_rua", length = 100)
    private String complemento;

    @Column(name = "bairro", length = 100)
    private String bairro;

    @Column(name = "cidade", length = 100)
    private String localidade;

    @Column(name = "uf", length = 100)
    private String uf;

    @Column(name = "latitude", length = 100)
    private String latitude;

    @Column(name = "longitude", length = 100)
    private String longitude;

    @Column(name = "numero", length = 10)
    private int numero;

    @Column(name = "complemento_numero", length = 100)
    private String complementoNumero;

    @Override
    public String toString() {
        return String.format("id %d, cep %s, logradouro %s, complemento %s, bairro %s, localidade %s, " +
                        "uf %s, latitude %s, longitude %s",
                id, cep, logradouro, complemento, bairro, localidade, uf, latitude, longitude);
    }

}
