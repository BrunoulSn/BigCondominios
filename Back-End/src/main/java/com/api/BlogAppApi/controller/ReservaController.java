
package com.api.BlogAppApi.controller;

import com.api.BlogAppApi.DTOs.ReservaDTO;
import com.api.BlogAppApi.model.ReservaDB;
import com.api.BlogAppApi.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaDB>> getAllReservas() {
        List<ReservaDB> reservas = reservaService.findAll();
        return reservas.isEmpty()
            ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(reservas)
            : ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getReservaDetails(@PathVariable long id) {
        return reservaService.findById(id)
            .<ResponseEntity<Object>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva não encontrada"));
    }

    @PostMapping
    public ResponseEntity<Object> saveNewReserva(@RequestBody @Valid ReservaDTO dto) {
        var reserva = new ReservaDB();
        BeanUtils.copyProperties(dto, reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.save(reserva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDB> editarReserva(@PathVariable Long id, @RequestBody ReservaDTO dto) {
        return reservaService.findById(id).map(reserva -> {
            reserva.setDataHoraInicio(dto.dataHoraInicio());
            reserva.setDataHoraFim(dto.dataHoraFim());
            reserva.setStatus(dto.status());
            return ResponseEntity.ok(reservaService.save(reserva));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public Object deleteReserva(@PathVariable long id) {
        return reservaService.findById(id).map(reserva -> {
            reservaService.delete(reserva);
            return ResponseEntity.ok("Reserva deletada com sucesso");
        }).orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).body("Reserva não encontrada"));
    }
}
