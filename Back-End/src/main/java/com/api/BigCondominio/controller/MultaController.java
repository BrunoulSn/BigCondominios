
package com.api.BigCondominio.controller;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.BigCondominio.DTOs.AtualizarStatusDTO;
import com.api.BigCondominio.DTOs.MultaDTO;
import com.api.BigCondominio.model.MultaDB;
import com.api.BigCondominio.model.moradorDB;
import com.api.BigCondominio.service.MultaService;

import java.time.LocalDate;
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
        var morador = new moradorDB();
        morador.setId(dto.moradorId());
        multa.setMorador(morador);

        return ResponseEntity.status(HttpStatus.CREATED).body(multaService.save(multa));
    }


    @PutMapping("/{id}")
    public ResponseEntity<MultaDB> editarMulta(@PathVariable Long id, @RequestBody MultaDTO dto) {
        return multaService.findById(id).map(multa -> {
            multa.setDescricao(dto.descricao());
            multa.setValor(dto.valor());
            multa.setDataOcorrencia(dto.dataOcorrencia());
            multa.setDataVencimento(dto.dataVencimento()); // ← conversão aqui
            multa.setStatus(dto.status());
            multa.setGravidade(dto.gravidade());
            return ResponseEntity.ok(multaService.save(multa));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestBody @Valid AtualizarStatusDTO dto) {
        return multaService.findById(id).map(multa -> {
            multa.setStatus(dto.status());

            // ✅ Atualiza a data de pagamento se for paga
            if ("paga".equalsIgnoreCase(dto.status())) {
                multa.setDataPagamento(LocalDate.now());
            }

            multaService.save(multa);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Multa não encontrada"));
    }

    @DeleteMapping("/{id}")
    public Object deleteMulta(@PathVariable long id) {
        return multaService.findById(id).map(multa -> {
            multaService.delete(multa);
            return ResponseEntity.ok("Multa deletada com sucesso");
        }).orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).body("Multa não encontrada"));
    }
}