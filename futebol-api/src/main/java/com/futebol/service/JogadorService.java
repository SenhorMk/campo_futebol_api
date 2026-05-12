package com.futebol.service;

import com.futebol.model.Jogador;
import com.futebol.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JogadorService {

    @Autowired
    private JogadorRepository jogadorRepository;

    //CRUD

    public List<Jogador> listarTodos() {
        return jogadorRepository.findAll();
    }

    public Optional<Jogador> buscarPorId(Integer id) {
        return jogadorRepository.findById(id);
    }

    public Jogador salvar(Jogador jogador) {
        return jogadorRepository.save(jogador);
    }

    public Jogador atualizar(Integer id, Jogador dadosNovos) {
        Jogador existente = jogadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado: " + id));

        existente.setNome(dadosNovos.getNome());
        existente.setEmail(dadosNovos.getEmail());
        existente.setDatanasc(dadosNovos.getDatanasc());

        return jogadorRepository.save(existente);
    }

    public void deletar(Integer id) {
        if (!jogadorRepository.existsById(id)) {
            throw new RuntimeException("Jogador não encontrado: " + id);
        }
        jogadorRepository.deleteById(id);
    }

    //Consultas extras

    public List<Jogador> buscarPorNome(String nome) {
        return jogadorRepository.findByNomeContainingIgnoreCase(nome);
    }
}
