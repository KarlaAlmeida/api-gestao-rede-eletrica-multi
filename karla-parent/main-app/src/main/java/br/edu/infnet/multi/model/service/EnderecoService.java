package br.edu.infnet.multi.model.service;


import br.edu.infnet.multi.clients.OpenStreetMapFeignClient;
import br.edu.infnet.multi.clients.ViaCepFeignClient;
import br.edu.infnet.multi.model.domain.entities.Endereco;
import br.edu.infnet.multi.model.domain.entities.Geolocalizacao;
import br.edu.infnet.multi.model.exceptions.CepNotFoundException;
import br.edu.infnet.multi.model.exceptions.ExternalApiException;
import br.edu.infnet.multi.model.exceptions.InvalidCepException;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    private final ViaCepFeignClient viaCepFeignClient;
    private final OpenStreetMapFeignClient openStreetMapFeignClient;

    public EnderecoService(ViaCepFeignClient viaCepFeignClient,
                           OpenStreetMapFeignClient openStreetMapFeignClient) {
        this.viaCepFeignClient = viaCepFeignClient;
        this.openStreetMapFeignClient = openStreetMapFeignClient;
    }

    public Endereco obterEnderecoPorCep(String cep){

        if (cep == null || !cep.matches("\\d{8}")) {
            throw new InvalidCepException("O CEP deve conter 8 dígitos.");
        }

        try {
            Endereco endereco = viaCepFeignClient.findByCep(cep);

            if (endereco.getCep() == null || endereco.getCep().isEmpty()){
                throw new CepNotFoundException("CEP não encontrado: " + cep);
            }

            String query = endereco.getLogradouro() + ", " + endereco.getLocalidade() + ", " + endereco.getUf();
            List<Geolocalizacao> geolocalizacoes = openStreetMapFeignClient.search(query, "jsonv2", 10);

            if (geolocalizacoes != null && !geolocalizacoes.isEmpty()) {
                Geolocalizacao geolocalizacao = geolocalizacoes.get(0);
                endereco.setLatitude(geolocalizacao.getLat());
                endereco.setLongitude(geolocalizacao.getLon());
            }

            return endereco;
        } catch (FeignException e) {
            throw new ExternalApiException("Erro na comunicação com a API do viacep ou openStreetMap: " + e.getMessage());
        }


    }
}
