package com.iestp.sistemareciclaje.routers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iestp.sistemareciclaje.modelo.Cliente;
import com.iestp.sistemareciclaje.servicio.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Clientes", description = "Gestión de clientes del sistema de reciclaje")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Lista todos los clientes ordenados por nombre")
    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listar();
    }

    @Operation(summary = "Busca un cliente por su ID")
    @GetMapping("/{id}")
    public Cliente obtenerPorId(
            @Parameter(description = "ID del cliente a buscar") @PathVariable Integer id) {
        return clienteService.obtenerPorId(id);
    }

    @Operation(summary = "Registra un nuevo cliente")
    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente) {
        Cliente creado = clienteService.crear(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualiza los datos de un cliente existente")
    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Integer id, @RequestBody Cliente datos) {
        return clienteService.actualizar(id, datos);
    }

    @Operation(summary = "Elimina un cliente (solo si no tiene entregas registradas)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Temporal: se reemplaza por @RestControllerAdvice en las Semanas 8-9
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarError(RuntimeException ex) {
        return ResponseEntity.badRequest().body(Map.of("detail", ex.getMessage()));
    }
}