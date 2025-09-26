package br.edu.infnet.multi.clients;

import br.edu.infnet.multi.model.domain.entities.Endereco;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "${api.viacep.url}")
public interface ViaCepFeignClient {

    @GetMapping("/{cep}/json/")
    Endereco findByCep(@PathVariable String cep);
}
