package br.edu.infnet.multi.clients;


import br.edu.infnet.multi.model.domain.entities.EnderecoGeorreferenciado;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "karlageolocalizaaoapi", url = "${karlaapitdd.url}")
public interface GeolocalizacaoFeignClient {

    @GetMapping("/api/enderecos/{cep}")
    EnderecoGeorreferenciado obterEnderecoGeorreferenciadoPorCep (@PathVariable String cep);
}
