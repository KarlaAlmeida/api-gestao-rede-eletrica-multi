package br.edu.infnet.multi.model.service;


import br.edu.infnet.multi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.multi.model.domain.entities.Tecnico;
import br.edu.infnet.multi.model.dto.in.TecnicoRequestDTO;
import br.edu.infnet.multi.model.dto.out.EnderecoGeorreferenciadoResponseDTO;
import br.edu.infnet.multi.model.dto.out.TecnicoResponseDTO;
import br.edu.infnet.multi.model.exceptions.ResourceNotFoundException;
import br.edu.infnet.multi.model.repository.TecnicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TecnicoService{

    private final TecnicoRepository tecnicoRepository;
    private final EnderecoGeorreferenciadoService enderecoGeorreferenciadoService;

    public TecnicoService(TecnicoRepository tecnicoRepository,
                          EnderecoGeorreferenciadoService enderecoGeorreferenciadoService) {
        this.tecnicoRepository = tecnicoRepository;
        this.enderecoGeorreferenciadoService = enderecoGeorreferenciadoService;
    }


    public TecnicoResponseDTO incluir(TecnicoRequestDTO tecnicoRequestDTO) {

        Tecnico tecnico = new Tecnico();
        tecnico.setNome(tecnicoRequestDTO.getNome());
        tecnico.setCpf(tecnicoRequestDTO.getCpf());
        tecnico.setEmail(tecnicoRequestDTO.getEmail());
        tecnico.setTelefone(tecnicoRequestDTO.getTelefone());
        tecnico.setUltimoSalario(tecnicoRequestDTO.getUltimoSalario());
        tecnico.setAtivo(tecnicoRequestDTO.isAtivo());
        tecnico.setEspecialidade(tecnicoRequestDTO.getEspecialidade());
        tecnico.setDisponivel(tecnicoRequestDTO.isDisponivel());

        String cepLimpo = tecnicoRequestDTO.getCep().replace("-", "");

        EnderecoGeorreferenciadoResponseDTO enderecoGeorreferenciadoResponseDTO =
                enderecoGeorreferenciadoService.obterEnderecoGeorreferenciadoPorCep(cepLimpo);

        EnderecoGeorreferenciado enderecoGeorreferenciado = new EnderecoGeorreferenciado();
        enderecoGeorreferenciado.setCep(enderecoGeorreferenciadoResponseDTO.getCep());
        enderecoGeorreferenciado.setLogradouro(enderecoGeorreferenciadoResponseDTO.getLogradouro());
        enderecoGeorreferenciadoResponseDTO.setComplemento(enderecoGeorreferenciado.getComplemento());
        enderecoGeorreferenciadoResponseDTO.setBairro(enderecoGeorreferenciadoResponseDTO.getBairro());
        enderecoGeorreferenciadoResponseDTO.setLocalidade(enderecoGeorreferenciadoResponseDTO.getLocalidade());
        enderecoGeorreferenciadoResponseDTO.setUf(enderecoGeorreferenciadoResponseDTO.getUf());
        enderecoGeorreferenciado.setNumero(tecnicoRequestDTO.getNumero());
        enderecoGeorreferenciado.setComplementoNumero(tecnicoRequestDTO.getComplementoNumero());

        tecnico.setEndereco(enderecoGeorreferenciado);

        return new TecnicoResponseDTO(tecnicoRepository.save(tecnico));
    }


    public TecnicoResponseDTO alterar(Integer id, TecnicoRequestDTO tecnicoAtualizado) {

        Tecnico tecnico = tecnicoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O técnico com ID " + id + " não foi encontrado."));

        tecnico.setId(id);
        tecnico.setNome(tecnicoAtualizado.getNome());
        tecnico.setCpf(tecnicoAtualizado.getCpf());
        tecnico.setEmail(tecnicoAtualizado.getEmail());
        tecnico.setTelefone(tecnicoAtualizado.getTelefone());
        tecnico.setUltimoSalario(tecnicoAtualizado.getUltimoSalario());
        tecnico.setAtivo(tecnicoAtualizado.isAtivo());
        tecnico.setEspecialidade(tecnicoAtualizado.getEspecialidade());
        tecnico.setDisponivel(tecnicoAtualizado.isDisponivel());

        String cepLimpo = tecnicoAtualizado.getCep().replace("-", "");

        EnderecoGeorreferenciadoResponseDTO enderecoGeorreferenciadoResponseDTO =
                enderecoGeorreferenciadoService.obterEnderecoGeorreferenciadoPorCep(cepLimpo);

        EnderecoGeorreferenciado enderecoGeorreferenciado = new EnderecoGeorreferenciado();
        enderecoGeorreferenciado.setCep(enderecoGeorreferenciadoResponseDTO.getCep());
        enderecoGeorreferenciado.setLogradouro(enderecoGeorreferenciadoResponseDTO.getLogradouro());
        enderecoGeorreferenciadoResponseDTO.setComplemento(enderecoGeorreferenciado.getComplemento());
        enderecoGeorreferenciadoResponseDTO.setBairro(enderecoGeorreferenciadoResponseDTO.getBairro());
        enderecoGeorreferenciadoResponseDTO.setLocalidade(enderecoGeorreferenciadoResponseDTO.getLocalidade());
        enderecoGeorreferenciadoResponseDTO.setUf(enderecoGeorreferenciadoResponseDTO.getUf());
        enderecoGeorreferenciado.setNumero(tecnicoAtualizado.getNumero());
        enderecoGeorreferenciado.setComplementoNumero(tecnicoAtualizado.getComplementoNumero());
        enderecoGeorreferenciado.setNumero(tecnicoAtualizado.getNumero());
        enderecoGeorreferenciado.setComplementoNumero(tecnicoAtualizado.getComplementoNumero());

        tecnico.setEndereco(enderecoGeorreferenciado);

        return new TecnicoResponseDTO(tecnicoRepository.save(tecnico));
    }

    public TecnicoResponseDTO inativar(Integer id) {
        Tecnico tecnico = tecnicoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O técnico com ID " + id + " não foi encontrado."));

        tecnico.setId(id);

        if(!tecnico.isAtivo()) {
            System.out.println("Técnico " + tecnico.getNome() + " já está inativo!");
            return new TecnicoResponseDTO(tecnico);
        }
        tecnico.setAtivo(false);

        return new TecnicoResponseDTO(tecnicoRepository.save(tecnico));
    }


    public TecnicoResponseDTO obterPorId(Integer id) {
        Tecnico tecnico = tecnicoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O técnico com ID " + id + " não foi encontrado."));

        return new TecnicoResponseDTO(tecnico);

    }

    public List<TecnicoResponseDTO> obterLista() {
        return tecnicoRepository.findAll()
                .stream()
                .map(TecnicoResponseDTO::new) // chama o construtor DTO(Tecnico)
                .toList();
    }

    public List<TecnicoResponseDTO> buscarPorNomeEspecialidade(String nomePrefixo, String especialidade) {
        return tecnicoRepository.
                findByNomeStartingWithIgnoreCaseAndEspecialidadeIgnoreCase(
                        nomePrefixo, especialidade)
                .stream()
                .map(TecnicoResponseDTO::new) // chama o construtor DTO(Tecnico)
                .collect(Collectors.toList());
    }

    public void excluir(Integer id) {
        Tecnico tecnico = tecnicoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("O técnico com ID " + id + " não foi encontrado."));
        tecnicoRepository.delete(tecnico);
    }

}
