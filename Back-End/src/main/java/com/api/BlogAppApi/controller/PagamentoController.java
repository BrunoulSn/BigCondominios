
package com.api.BlogAppApi.controller;

import com.api.BlogAppApi.DTOs.PagamentoDTO;
import com.api.BlogAppApi.DTOs.PagamentoMultaResponseDTO;
import com.api.BlogAppApi.model.pagamentoDB;
import com.api.BlogAppApi.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<List<pagamentoDB>> getAllPagamentos() {
        List<pagamentoDB> pagamentos = pagamentoService.findAll();
        return pagamentos.isEmpty()
            ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(pagamentos)
            : ResponseEntity.ok(pagamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPagamentoDetails(@PathVariable long id) {
        return pagamentoService.findById(id)
            .<ResponseEntity<Object>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado"));
    }

    @PostMapping
    public ResponseEntity<Object> saveNewPagamento(@RequestBody @Valid PagamentoDTO dto) {
        var pagamento = new pagamentoDB();

        // Buscar o morador pelo ID informado no DTO
        var moradorOpt = pagamentoService.buscarMoradorPorId(dto.moradorId());
        if (moradorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Morador não encontrado para o ID: " + dto.moradorId());
        }

        pagamento.setMorador(moradorOpt.get());
        pagamento.setValor(dto.valor());
        pagamento.setDataPagamento(dto.dataPagamento());
        pagamento.setTipo(dto.tipo());
        pagamento.setStatus(dto.status());
        pagamento.setFormaPagamento(dto.formaPagamento());

        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoService.save(pagamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<pagamentoDB> editarPagamento(@PathVariable Long id, @RequestBody PagamentoDTO dto) {
        return pagamentoService.findById(id).map(pagamento -> {
            pagamento.setValor(dto.valor());
            pagamento.setDataPagamento(dto.dataPagamento());
            pagamento.setTipo(dto.tipo());
            pagamento.setStatus(dto.status());
            pagamento.setFormaPagamento(dto.formaPagamento());
            return ResponseEntity.ok(pagamentoService.save(pagamento));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public Object deletePagamento(@PathVariable long id) {
        return pagamentoService.findById(id).map(pagamento -> {
            pagamentoService.delete(pagamento);
            return ResponseEntity.ok("Pagamento deletado com sucesso");
        }).orElse(ResponseEntity.status(HttpStatus.NO_CONTENT).body("Pagamento não encontrado"));
    }

    @GetMapping("/completo")
    public ResponseEntity<PagamentoMultaResponseDTO> getPagamentosEAsMultasPagas() {
        var resposta = pagamentoService.buscarPagamentosEAsMultasPagas();
        return ResponseEntity.ok(resposta);
    }
}
