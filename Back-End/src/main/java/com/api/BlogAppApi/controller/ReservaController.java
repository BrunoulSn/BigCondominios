
package com.api.BlogAppApi.controller;

import com.api.BlogAppApi.DTOs.ReservaDTO;
import com.api.BlogAppApi.DTOs.ReservaResponseDTO;
import com.api.BlogAppApi.model.ReservaDB;
import com.api.BlogAppApi.service.AreaComumService;
import com.api.BlogAppApi.service.MoradorService;
import com.api.BlogAppApi.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private AreaComumService areaComumService;

    @Autowired
    private MoradorService moradorService;

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

        var areaOpt = areaComumService.findById(dto.areaId());
        var moradorOpt = moradorService.findById(dto.moradorId());

        if (areaOpt.isEmpty() || moradorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Morador ou área comum não encontrados.");
        }

        reserva.setArea(areaOpt.get());
        reserva.setMorador(moradorOpt.get());
        reserva.setDataReserva(dto.data());
        reserva.setStatus("pendente"); // <- status atribuído internamente aqui

        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.save(reserva));
    }

    @DeleteMapping("/{id}")
    public Object deleteReserva(@PathVariable long id) {
        return reservaService.findById(id).map(reserva -> {
            reservaService.delete(reserva);
            return ResponseEntity.ok("Reserva deletada com sucesso");
        }).orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).body("Reserva não encontrada"));
    }

    @GetMapping("/futuras")
    public ResponseEntity<List<ReservaResponseDTO>> listarReservasFuturas() {
        List<ReservaResponseDTO> futuras = reservaService.findAll().stream()
            .filter(r -> !r.getStatus().equalsIgnoreCase("cancelada"))
            .map(ReservaResponseDTO::fromEntity)
            .toList();

        return ResponseEntity.ok(futuras);
    }


}
