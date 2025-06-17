package com.api.BigCondominio.controller;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.BigCondominio.DTOs.MoradorDTO;
import com.api.BigCondominio.model.moradorDB;
import com.api.BigCondominio.service.MoradorService;

import java.util.List;

@RestController
@RequestMapping("/morador")
public class MoradorController {

    @Autowired
    MoradorService moradorService;

    @GetMapping
    public ResponseEntity<List<moradorDB>> getAllMoradores() {
        List<moradorDB> moradores = moradorService.findAll();
        return moradores.isEmpty()
            ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(moradores)
            : ResponseEntity.ok(moradores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMoradorDetails(@PathVariable long id) {
        return moradorService.findById(id)
            .<ResponseEntity<Object>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Morador não encontrado"));
    }

    @PostMapping
    public ResponseEntity<Object> saveNewMorador(@RequestBody @Valid MoradorDTO dto) {
        var morador = new moradorDB();
        BeanUtils.copyProperties(dto, morador);
        return ResponseEntity.status(HttpStatus.CREATED).body(moradorService.save(morador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editarMorador(@PathVariable Long id, @RequestBody @Valid MoradorDTO dto) {
        return moradorService.findById(id)
            .map(morador -> {
            BeanUtils.copyProperties(dto, morador);
            return ResponseEntity.ok(moradorService.save(morador));
        })
        .<ResponseEntity<Object>>map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Morador não encontrado"));
    }

    @DeleteMapping("/{id}")
    public Object deleteMorador(@PathVariable long id) {
        return moradorService.findById(id).map(morador -> {
            moradorService.delete(morador);
            return ResponseEntity.ok("Morador deletado com sucesso");
        }).orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).body("Morador não encontrado"));
    }
}