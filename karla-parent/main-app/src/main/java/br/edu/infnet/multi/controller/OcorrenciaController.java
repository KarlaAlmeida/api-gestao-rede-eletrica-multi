package br.edu.infnet.multi.controller;


import br.edu.infnet.multi.model.dto.in.OcorrenciaRequestDTO;
import br.edu.infnet.multi.model.dto.out.OcorrenciaResponseDTO;
import br.edu.infnet.multi.model.service.OcorrenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ocorrencias")
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @PostMapping
    public ResponseEntity<OcorrenciaResponseDTO> incluir(@Valid @RequestBody OcorrenciaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ocorrenciaService.incluir(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<OcorrenciaResponseDTO> alterar(@PathVariable Integer id,
                                                         @Valid @RequestBody OcorrenciaRequestDTO dto) {

        if (dto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ocorrenciaService.alterar(id, dto));

    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<OcorrenciaResponseDTO> alterarStatus(@PathVariable Integer id,
                                                    @RequestParam String statusOcorrencia){
        return ResponseEntity.ok(ocorrenciaService.alterarStatus(id, statusOcorrencia));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OcorrenciaResponseDTO> obterPorId(@PathVariable Integer id){

        return ResponseEntity.ok(ocorrenciaService.obterPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<OcorrenciaResponseDTO>> obterLista(){

        List<OcorrenciaResponseDTO> lista = ocorrenciaService.obterLista();

        if(lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        ocorrenciaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
