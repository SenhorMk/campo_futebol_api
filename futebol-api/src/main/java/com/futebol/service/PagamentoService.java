package com.futebol.service;

import com.futebol.model.Jogador;
import com.futebol.model.Pagamento;
import com.futebol.repository.JogadorRepository;
import com.futebol.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private JogadorRepository jogadorRepository;

    //CRUD

    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }

    public Optional<Pagamento> buscarPorId(Integer id) {
        return pagamentoRepository.findById(id);
    }

    public Pagamento salvar(Integer codJogador, Pagamento pagamento) {
        Jogador jogador = jogadorRepository.findById(codJogador)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado: " + codJogador));
        pagamento.setJogador(jogador);
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento atualizar(Integer id, Pagamento dadosNovos) {
        Pagamento existente = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado: " + id));

        existente.setAno(dadosNovos.getAno());
        existente.setMes(dadosNovos.getMes());
        existente.setValor(dadosNovos.getValor());

        return pagamentoRepository.save(existente);
    }

    public void deletar(Integer id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new RuntimeException("Pagamento não encontrado: " + id);
        }
        pagamentoRepository.deleteById(id);
    }

    //Consultas extras

    public List<Pagamento> listarPorJogador(Integer codJogador) {
        return pagamentoRepository.findByJogadorCodJogador(codJogador);
    }

    public List<Pagamento> listarPorJogadorEAno(Integer codJogador, Short ano) {
        return pagamentoRepository.findByJogadorCodJogadorAndAno(codJogador, ano);
    }

    public List<Pagamento> listarPorJogadorAnoEMes(Integer codJogador, Short ano, Byte mes) {
        return pagamentoRepository.findByJogadorCodJogadorAndAnoAndMes(codJogador, ano, mes);
    }
}
