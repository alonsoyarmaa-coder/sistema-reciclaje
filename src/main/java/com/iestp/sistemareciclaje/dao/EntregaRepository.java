package com.iestp.sistemareciclaje.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iestp.sistemareciclaje.modelo.Entrega;

public interface EntregaRepository extends JpaRepository<Entrega, Integer> {
}