package br.edu.infnet.multi.controller;


import br.edu.infnet.multi.model.domain.entities.EnderecoGeorreferenciado;
import br.edu.infnet.multi.model.dto.out.EnderecoGeorreferenciadoResponseDTO;
import br.edu.infnet.multi.model.service.EnderecoGeorreferenciadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoGeorreferenciadoController {

    private final EnderecoGeorreferenciadoService enderecoGeorreferenciadoService;


    public EnderecoGeorreferenciadoController(EnderecoGeorreferenciadoService enderecoGeorreferenciadoService) {
        this.enderecoGeorreferenciadoService = enderecoGeorreferenciadoService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<EnderecoGeorreferenciadoResponseDTO> obterLocalidade(@PathVariable String cep) {

        return ResponseEntity.ok( enderecoGeorreferenciadoService.obterEnderecoGeorreferenciadoPorCep(cep));
    }
}
