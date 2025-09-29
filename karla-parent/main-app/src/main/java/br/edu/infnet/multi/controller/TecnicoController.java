package br.edu.infnet.multi.controller;


import br.edu.infnet.multi.model.dto.in.TecnicoRequestDTO;
import br.edu.infnet.multi.model.dto.out.TecnicoResponseDTO;
import br.edu.infnet.multi.model.service.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TecnicoResponseDTO> incluir(@Valid @RequestBody TecnicoRequestDTO tecnico) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoService.incluir(tecnico));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TecnicoResponseDTO> alterar(@PathVariable Integer id,
                                           @Valid @RequestBody TecnicoRequestDTO tecnico) {

        if (tecnico == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tecnicoService.alterar(id, tecnico));
    }

    @PatchMapping(value = "/{id}/inativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TecnicoResponseDTO> inativar(@PathVariable Integer id) {
        return ResponseEntity.ok(tecnicoService.inativar(id));
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<TecnicoResponseDTO> obterPorId(@PathVariable Integer id){
        return ResponseEntity.ok(tecnicoService.obterPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<TecnicoResponseDTO>> obterLista(){
        List<TecnicoResponseDTO> lista = tecnicoService.obterLista();

        if(lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("filtro/nome-e-especialidade")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<TecnicoResponseDTO> buscarPorNomeEspecialidade(@RequestParam String nomePrefixo,
                                                    @RequestParam String especialidade) {
        return tecnicoService.buscarPorNomeEspecialidade(nomePrefixo, especialidade);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Integer id){
        tecnicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
