
package com.api.BlogAppApi.controller;

import com.api.BlogAppApi.DTOs.MultaDTO;
import com.api.BlogAppApi.model.MultaDB;
import com.api.BlogAppApi.service.MultaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/multas")
public class MultaController {

    @Autowired
    MultaService multaService;

    @GetMapping
    public ResponseEntity<List<MultaDB>> getAllMultas() {
        List<MultaDB> multas = multaService.findAll();
        return multas.isEmpty()
            ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(multas)
            : ResponseEntity.ok(multas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMultaDetails(@PathVariable long id) {
        return multaService.findById(id)
            .<ResponseEntity<Object>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Multa não encontrada"));
    }

    @PostMapping
    public ResponseEntity<Object> saveNewMulta(@RequestBody @Valid MultaDTO dto) {
        var multa = new MultaDB();
        BeanUtils.copyProperties(dto, multa);
        return ResponseEntity.status(HttpStatus.CREATED).body(multaService.save(multa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MultaDB> editarMulta(@PathVariable Long id, @RequestBody MultaDTO dto) {
        return multaService.findById(id).map(multa -> {
            multa.setDescricao(dto.descricao());
            multa.setValor(dto.valor());
            multa.setDataOcorrencia(dto.dataOcorrencia());
            multa.setDataVencimento(dto.dataVencimento());
            multa.setStatus(dto.status());
            multa.setGravidade(dto.gravidade());
            return ResponseEntity.ok(multaService.save(multa));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public Object deleteMulta(@PathVariable long id) {
        return multaService.findById(id).map(multa -> {
            multaService.delete(multa);
            return ResponseEntity.ok("Multa deletada com sucesso");
        }).orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).body("Multa não encontrada"));
    }
}
