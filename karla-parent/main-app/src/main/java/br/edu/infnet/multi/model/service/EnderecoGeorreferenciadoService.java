package br.edu.infnet.multi.model.service;

import br.edu.infnet.multi.clients.GeolocalizacaoFeignClient;
import br.edu.infnet.multi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.multi.model.dto.out.EnderecoGeorreferenciadoResponseDTO;
import br.edu.infnet.multi.model.exceptions.CepNotFoundException;
import br.edu.infnet.multi.model.exceptions.ExternalApiException;
import br.edu.infnet.multi.model.exceptions.InvalidCepException;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class EnderecoGeorreferenciadoService {

    private final GeolocalizacaoFeignClient geolocalizacaoFeignClient;

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
    }
}
