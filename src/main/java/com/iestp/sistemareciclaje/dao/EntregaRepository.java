package com.iestp.sistemareciclaje.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.iestp.sistemareciclaje.modelo.Entrega;

public interface EntregaRepository extends JpaRepository<Entrega, Integer> {
    List<Entrega> findByCliente_IdOrderByFechaDesc(Integer clienteId);
    boolean existsByCliente_Id(Integer clienteId);
}