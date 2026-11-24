package com.iestp.sistemareciclaje.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iestp.sistemareciclaje.modelo.Log;

public interface LogRepository extends JpaRepository<Log, Integer> {
}