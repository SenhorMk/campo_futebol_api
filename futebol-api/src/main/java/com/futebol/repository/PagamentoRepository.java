package com.futebol.repository;

import com.futebol.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    // Todos os pagamentos de um jogador específico
    List<Pagamento> findByJogadorCodJogador(Integer codJogador);

    // Pagamentos de um jogador filtrados por ano
    List<Pagamento> findByJogadorCodJogadorAndAno(Integer codJogador, Short ano);

    // Pagamentos de um jogador filtrados por ano e mês
    List<Pagamento> findByJogadorCodJogadorAndAnoAndMes(Integer codJogador, Short ano, Byte mes);
}
