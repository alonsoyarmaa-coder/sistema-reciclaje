package com.iestp.sistemareciclaje.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.iestp.sistemareciclaje.modelo.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findAllByOrderByNombreAsc();
}