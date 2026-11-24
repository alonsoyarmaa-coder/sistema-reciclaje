package com.iestp.sistemareciclaje.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iestp.sistemareciclaje.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}