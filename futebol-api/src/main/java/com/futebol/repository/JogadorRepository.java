package com.futebol.repository;

import com.futebol.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Integer> {

    // Busca jogadores cujo nome contenha o texto informado (case-insensitive)
    List<Jogador> findByNomeContainingIgnoreCase(String nome);

    // Busca por e-mail exato
    List<Jogador> findByEmail(String email);
}
