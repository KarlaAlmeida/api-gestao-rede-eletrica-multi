package br.edu.infnet.multi.model.service;

import br.edu.infnet.multi.clients.OpenStreetMapFeignClient;
import br.edu.infnet.multi.clients.ViaCepFeignClient;
import br.edu.infnet.multi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.multi.model.domain.entities.Geolocalizacao;
import br.edu.infnet.multi.model.dto.out.EnderecoGeorreferenciadoResponseDTO;
import br.edu.infnet.multi.model.exceptions.CepNotFoundException;
import br.edu.infnet.multi.model.exceptions.ExternalApiException;
import br.edu.infnet.multi.model.exceptions.InvalidCepException;
import br.edu.infnet.multi.model.exceptions.ResourceNotFoundException;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoGeorreferenciadoService {

    /*private final GeolocalizacaoFeignClient geolocalizacaoFeignClient;

    public EnderecoGeorreferenciadoService(GeolocalizacaoFeignClient geolocalizacaoFeignClient) {
        this.geolocalizacaoFeignClient = geolocalizacaoFeignClient;
    }

    public EnderecoGeorreferenciadoResponseDTO obterEnderecoGeorreferenciadoPorCep(String cep){

        if (cep == null || !cep.matches("\\d{8}")) {
            throw new InvalidCepException("O CEP deve conter 8 dígitos.");
        }

        try {
            EnderecoGeorreferenciado enderecoGeorreferenciado =
                    geolocalizacaoFeignClient.obterEnderecoGeorreferenciadoPorCep(cep);

            EnderecoGeorreferenciadoResponseDTO enderecoGeorreferenciadoResponseDTO =
                    new EnderecoGeorreferenciadoResponseDTO(enderecoGeorreferenciado);

            return enderecoGeorreferenciadoResponseDTO;

        } catch (FeignException.NotFound e) {
            throw new CepNotFoundException("CEP não encontrado: " + cep);
        } catch (FeignException e) {
            throw new ExternalApiException("Erro na comunicação com a API de geolocalização: " + e.getMessage());
        }
    }*/

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
            EnderecoGeorreferenciado endereco = viaCepFeignClient.findByCep(cep);

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

            return new EnderecoGeorreferenciadoResponseDTO(endereco);
        } catch (FeignException e) {
            throw new ExternalApiException(
                    "Erro na comunicação com a API do viacep ou openStreetMap: " + e.getMessage());
        }
    }
}
