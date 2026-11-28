package com.iestp.sistemareciclaje.routers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iestp.sistemareciclaje.modelo.Entrega;
import com.iestp.sistemareciclaje.servicio.EntregaService;

@RestController
@RequestMapping("/api/entregas")
public class EntregaController {

    private final EntregaService entregaService;

    public EntregaController(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

    @GetMapping
    public List<Entrega> listar() {
        return entregaService.listar();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Entrega> listarPorCliente(@PathVariable Integer clienteId) {
        return entregaService.listarPorCliente(clienteId);
    }

    @PostMapping
    public ResponseEntity<Entrega> registrar(@RequestBody Entrega datos) {
        if (datos.getCliente() == null || datos.getCliente().getId() == null) {
            throw new RuntimeException("Debe indicar el cliente de la entrega.");
        }
        Entrega creada = entregaService.registrar(
                datos.getCliente().getId(), datos.getTipo(), datos.getPeso());
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // Temporal: se reemplaza por @RestControllerAdvice en las Semanas 8-9
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarError(RuntimeException ex) {
        return ResponseEntity.badRequest().body(Map.of("detail", ex.getMessage()));
    }
}