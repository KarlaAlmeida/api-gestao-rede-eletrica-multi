package br.edu.infnet.multi.model.service;


import br.edu.infnet.multi.model.domain.entities.Ativo;
import br.edu.infnet.multi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.multi.model.dto.in.AtivoRequestDTO;
import br.edu.infnet.multi.model.dto.out.AtivoResponseDTO;
import br.edu.infnet.multi.model.dto.out.EnderecoGeorreferenciadoResponseDTO;
import br.edu.infnet.multi.model.domain.enums.StatusAtivo;
import br.edu.infnet.multi.model.domain.enums.TipoAtivo;
import br.edu.infnet.multi.model.exceptions.ResourceNotFoundException;
import br.edu.infnet.multi.model.repository.AtivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtivoService{

    private final AtivoRepository ativoRepository;
    private final EnderecoGeorreferenciadoService enderecoGeorreferenciadoService;

    public AtivoService(AtivoRepository ativoRepository,
                        EnderecoGeorreferenciadoService enderecoGeorreferenciadoService) {
        this.ativoRepository = ativoRepository;
        this.enderecoGeorreferenciadoService = enderecoGeorreferenciadoService;
    }

    public AtivoResponseDTO incluir(AtivoRequestDTO ativoRequestDTO) {

        Ativo ativo = new Ativo();
        ativo.setTipoAtivo(TipoAtivo.fromString(ativoRequestDTO.getTipoAtivo()));
        ativo.setDataInstalacao(ativoRequestDTO.getDataInstalacao());
        ativo.setStatusAtivo(StatusAtivo.ATIVO);

        String cepLimpo = ativoRequestDTO.getCep().replace("-", "");

        EnderecoGeorreferenciadoResponseDTO enderecoGeorreferenciadoResponseDTO =
                enderecoGeorreferenciadoService.obterEnderecoGeorreferenciadoPorCep(cepLimpo);

        EnderecoGeorreferenciado enderecoGeorreferenciado = new EnderecoGeorreferenciado();
        enderecoGeorreferenciado.setCep(enderecoGeorreferenciadoResponseDTO.getCep());
        enderecoGeorreferenciado.setLogradouro(enderecoGeorreferenciadoResponseDTO.getLogradouro());
        enderecoGeorreferenciadoResponseDTO.setComplemento(enderecoGeorreferenciado.getComplemento());
        enderecoGeorreferenciadoResponseDTO.setBairro(enderecoGeorreferenciadoResponseDTO.getBairro());
        enderecoGeorreferenciadoResponseDTO.setLocalidade(enderecoGeorreferenciadoResponseDTO.getLocalidade());
        enderecoGeorreferenciadoResponseDTO.setUf(enderecoGeorreferenciadoResponseDTO.getUf());
        enderecoGeorreferenciadoResponseDTO.setLatitude(enderecoGeorreferenciadoResponseDTO.getLatitude());
        enderecoGeorreferenciadoResponseDTO.setLongitude(enderecoGeorreferenciadoResponseDTO.getLongitude());

        ativo.setEndereco(enderecoGeorreferenciado);

        return new AtivoResponseDTO(ativoRepository.save(ativo));
    }

    public AtivoResponseDTO alterar(Integer id, AtivoRequestDTO ativoRequestDTO) {

        Ativo ativo = ativoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O ativo com ID " + id + " não foi encontrado."));

        ativo.setId(id);
        ativo.setTipoAtivo(TipoAtivo.fromString(ativoRequestDTO.getTipoAtivo()));
        ativo.setDataInstalacao(ativoRequestDTO.getDataInstalacao());

        String cepLimpo = ativoRequestDTO.getCep().replace("-", "");

        EnderecoGeorreferenciadoResponseDTO enderecoGeorreferenciadoResponseDTO =
                enderecoGeorreferenciadoService.obterEnderecoGeorreferenciadoPorCep(cepLimpo);

        EnderecoGeorreferenciado enderecoGeorreferenciado = new EnderecoGeorreferenciado();
        enderecoGeorreferenciado.setCep(enderecoGeorreferenciadoResponseDTO.getCep());
        enderecoGeorreferenciado.setLogradouro(enderecoGeorreferenciadoResponseDTO.getLogradouro());
        enderecoGeorreferenciadoResponseDTO.setComplemento(enderecoGeorreferenciado.getComplemento());
        enderecoGeorreferenciadoResponseDTO.setBairro(enderecoGeorreferenciadoResponseDTO.getBairro());
        enderecoGeorreferenciadoResponseDTO.setLocalidade(enderecoGeorreferenciadoResponseDTO.getLocalidade());
        enderecoGeorreferenciadoResponseDTO.setUf(enderecoGeorreferenciadoResponseDTO.getUf());
        enderecoGeorreferenciadoResponseDTO.setLatitude(enderecoGeorreferenciadoResponseDTO.getLatitude());
        enderecoGeorreferenciadoResponseDTO.setLongitude(enderecoGeorreferenciadoResponseDTO.getLongitude());

        ativo.setEndereco(enderecoGeorreferenciado);

        return new AtivoResponseDTO(ativoRepository.save(ativo));
    }

    public AtivoResponseDTO alterarStatus(Integer id, String status){
        Ativo ativo = ativoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O ativo com ID " + id + " não foi encontrado."));

        ativo.setId(id);
        StatusAtivo statusNovo = StatusAtivo.fromString(status);

        if(statusNovo.equals(ativo.getStatusAtivo())){
            throw new IllegalStateException("O status atual do ativo já é " + status);
        }

        ativo.setStatusAtivo(statusNovo);

        return new AtivoResponseDTO(ativoRepository.save(ativo));
    }

    public AtivoResponseDTO obterPorId(Integer id) {
        Ativo ativo = ativoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O ativo com ID " + id + " não foi encontrado."));

        return new AtivoResponseDTO(ativo);
    }

    public List<AtivoResponseDTO> obterLista() {
        return ativoRepository.findAll()
                .stream()
                .map(AtivoResponseDTO::new)
                .toList();
    }

    public void excluir(Integer id) {
        Ativo ativo = ativoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O ativo com ID " + id + " não foi encontrado."));
        ativoRepository.delete(ativo);
    }

}
