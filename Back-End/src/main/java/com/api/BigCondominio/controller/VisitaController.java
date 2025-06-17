
package com.api.BigCondominio.controller;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.BigCondominio.DTOs.VisitaDTO;
import com.api.BigCondominio.model.visitaDB;
import com.api.BigCondominio.service.VisitaService;

import java.util.List;

@RestController
@RequestMapping("/visitas")
public class VisitaController {

    @Autowired
    VisitaService visitaService;

    @GetMapping
    public ResponseEntity<List<visitaDB>> getAllVisitas() {
        List<visitaDB> visitas = visitaService.findAll();
        return visitas.isEmpty()
            ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(visitas)
            : ResponseEntity.ok(visitas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getVisitaDetails(@PathVariable long id) {
        return visitaService.findById(id)
            .<ResponseEntity<Object>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Visita não encontrada"));
    }

    @PostMapping
    public ResponseEntity<Object> saveNewVisita(@RequestBody @Valid VisitaDTO dto) {
        var visita = new visitaDB();
        BeanUtils.copyProperties(dto, visita);
        return ResponseEntity.status(HttpStatus.CREATED).body(visitaService.save(visita));
    }

    @PutMapping("/{id}")
    public ResponseEntity<visitaDB> editarVisita(@PathVariable Long id, @RequestBody VisitaDTO dto) {
        return visitaService.findById(id).map(visita -> {
            visita.setNomeVisitante(dto.nomeVisitante());
            visita.setDocumento(dto.documento());
            visita.setDataHoraEntrada(dto.dataHoraEntrada());
            visita.setDataHoraSaida(dto.dataHoraSaida());
            visita.setStatus(dto.status());
            return ResponseEntity.ok(visitaService.save(visita));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public Object deleteVisita(@PathVariable long id) {
        return visitaService.findById(id).map(visita -> {
            visitaService.delete(visita);
            return ResponseEntity.ok("Visita deletada com sucesso");
        }).orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).body("Visita não encontrada"));
    }
}
