package com.futebol.controller;

import com.futebol.model.Pagamento;
import com.futebol.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pagamentos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<List<Pagamento>> listarTodos() {
        return ResponseEntity.ok(pagamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscarPorId(@PathVariable Integer id) {
        return pagamentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/jogador/{codJogador}")
    public ResponseEntity<List<Pagamento>> listarPorJogador(
            @PathVariable Integer codJogador,
            @RequestParam(required = false) Short ano,
            @RequestParam(required = false) Byte mes) {

        List<Pagamento> resultado;
        if (ano != null && mes != null) {
            resultado = pagamentoService.listarPorJogadorAnoEMes(codJogador, ano, mes);
        } else if (ano != null) {
            resultado = pagamentoService.listarPorJogadorEAno(codJogador, ano);
        } else {
            resultado = pagamentoService.listarPorJogador(codJogador);
        }
        return ResponseEntity.ok(resultado);
    }

    @PostMapping(value = "/jogador/{codJogador}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pagamento> criar(
            @PathVariable Integer codJogador,
            @Valid @RequestBody Pagamento pagamento) {
        try {
            Pagamento salvo = pagamentoService.salvar(codJogador, pagamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pagamento> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Pagamento pagamento) {
        try {
            return ResponseEntity.ok(pagamentoService.atualizar(id, pagamento));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            pagamentoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
