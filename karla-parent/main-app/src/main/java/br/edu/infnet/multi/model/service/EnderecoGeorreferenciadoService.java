package br.edu.infnet.multi.model.service;

import br.edu.infnet.multi.clients.OpenStreetMapFeignClient;
import br.edu.infnet.multi.clients.ViaCepFeignClient;
import br.edu.infnet.multi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.multi.model.dto.out.EnderecoGeorreferenciadoResponseDTO;
import br.edu.infnet.multi.model.exceptions.CepNotFoundException;
import br.edu.infnet.multi.model.exceptions.ExternalApiException;
import br.edu.infnet.multi.model.exceptions.InvalidCepException;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoGeorreferenciadoService {

    private final ViaCepFeignClient viaCepFeignClient;
    private final OpenStreetMapFeignClient openStreetMapFeignClient;

    public EnderecoGeorreferenciadoService(ViaCepFeignClient viaCepFeignClient, OpenStreetMapFeignClient openStreetMapFeignClient) {
        this.viaCepFeignClient = viaCepFeignClient;
        this.openStreetMapFeignClient = openStreetMapFeignClient;
    }

    public EnderecoGeorreferenciadoResponseDTO obterEnderecoGeorreferenciadoPorCep(String cep){

        if (cep == null || !cep.matches("\\d{8}")) {
            throw new InvalidCepException("O CEP deve conter 8 dígitos.");
        }

        try {
            ViaCepFeignClient.ViaCepResponse viaCepResponse = viaCepFeignClient.findByCep(cep);

            if (viaCepResponse.getCep() == null || viaCepResponse.getCep().isEmpty()){
                throw new CepNotFoundException("CEP não encontrado: " + cep);
            }

            EnderecoGeorreferenciado endereco = new EnderecoGeorreferenciado();
            endereco.setCep(viaCepResponse.getCep());
            endereco.setLogradouro(viaCepResponse.getLogradouro());
            endereco.setComplemento(viaCepResponse.getComplemento());
            endereco.setBairro(viaCepResponse.getBairro());
            endereco.setLocalidade(viaCepResponse.getLocalidade());
            endereco.setUf(viaCepResponse.getUf());

            String query = viaCepResponse.getLogradouro() + ", " + viaCepResponse.getLocalidade() + ", " + viaCepResponse.getUf();
            List<OpenStreetMapFeignClient.OpenStreetMapResponse> openStreetMapResponses =
                    openStreetMapFeignClient.search(query, "jsonv2", 10);

            if (openStreetMapResponses != null && !openStreetMapResponses.isEmpty()) {
                OpenStreetMapFeignClient.OpenStreetMapResponse geolocalizacao = openStreetMapResponses.get(0);
                endereco.setLatitude(geolocalizacao.getLat());
                endereco.setLongitude(geolocalizacao.getLon());
            }

            return new EnderecoGeorreferenciadoResponseDTO(endereco);
        } catch (FeignException e) {
            throw new ExternalApiException(
                    "Erro na comunicação com a API do viacep ou openStreetMap: " + e.getMessage());
        }
    }
}
