package br.edu.infnet.multi.controller;


import br.edu.infnet.multi.model.dto.in.AtivoRequestDTO;
import br.edu.infnet.multi.model.dto.out.AtivoResponseDTO;
import br.edu.infnet.multi.model.service.AtivoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ativos")
public class AtivoController {

    private final AtivoService ativoService;

    public AtivoController(AtivoService ativoService) {
        this.ativoService = ativoService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AtivoResponseDTO> incluir(@Valid @RequestBody AtivoRequestDTO ativoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ativoService.incluir(ativoDTO));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AtivoResponseDTO> alterar(@PathVariable Integer id,
                                         @Valid @RequestBody AtivoRequestDTO ativoDTO) {

        if (ativoDTO == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ativoService.alterar(id, ativoDTO));

    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AtivoResponseDTO> alterarStatus(@PathVariable Integer id,
                                               @RequestParam String status){
        return ResponseEntity.ok(ativoService.alterarStatus(id, status));
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<AtivoResponseDTO> obterPorId(@PathVariable Integer id){
        return ResponseEntity.ok(ativoService.obterPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<AtivoResponseDTO>> obterLista(){

        List<AtivoResponseDTO> lista = ativoService.obterLista();

        if(lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        ativoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
