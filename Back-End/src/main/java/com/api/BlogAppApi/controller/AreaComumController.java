package com.api.BlogAppApi.controller;

import com.api.BlogAppApi.DTOs.AreaComumDTO;
import com.api.BlogAppApi.model.AreaComumDB;
import com.api.BlogAppApi.service.AreaComumService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areas-comuns")
public class AreaComumController {

    @Autowired
    AreaComumService areaComumService;

    @GetMapping
    public ResponseEntity<List<AreaComumDB>> getAllAreas() {
        List<AreaComumDB> areas = areaComumService.findAll();
        return areas.isEmpty()
            ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(areas)
            : ResponseEntity.ok(areas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAreaDetails(@PathVariable long id) {
        return areaComumService.findById(id)
            .<ResponseEntity<Object>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Área comum não encontrada"));
    }

    @PostMapping
    public ResponseEntity<Object> saveNewArea(@RequestBody @Valid AreaComumDTO dto) {
        var area = new AreaComumDB();
        BeanUtils.copyProperties(dto, area);
        return ResponseEntity.status(HttpStatus.CREATED).body(areaComumService.save(area));
    }

    @PutMapping("/{id}")
    public Object editarArea(@PathVariable Long id, @RequestBody @Valid AreaComumDTO dto) {
        return areaComumService.findById(id)
            .map(area -> {
                try {
                    area.setNome(dto.nome());
                    area.setDisponivel(dto.disponivel());
                    return ResponseEntity.ok(areaComumService.save(area));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao atualizar área comum: " + e.getMessage());
                }
            })
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Área comum não encontrada"));
    }

    @DeleteMapping("/{id}")
    public Object deleteArea(@PathVariable long id) {
        return areaComumService.findById(id).map(area -> {
            areaComumService.delete(area);
            return ResponseEntity.ok("Área comum deletada com sucesso");
        }).orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).body("Área comum não encontrada"));
    }
}