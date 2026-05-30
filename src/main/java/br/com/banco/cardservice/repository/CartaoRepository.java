package br.com.banco.cardservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.banco.cardservice.entity.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}