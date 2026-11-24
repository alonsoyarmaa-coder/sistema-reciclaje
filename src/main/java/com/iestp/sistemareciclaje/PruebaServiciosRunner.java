package com.iestp.sistemareciclaje;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.iestp.sistemareciclaje.modelo.Cliente;
import com.iestp.sistemareciclaje.modelo.Entrega;
import com.iestp.sistemareciclaje.servicio.ClienteService;
import com.iestp.sistemareciclaje.servicio.EntregaService;

@Component
public class PruebaServiciosRunner implements CommandLineRunner {

    private final ClienteService clienteService;
    private final EntregaService entregaService;

    public PruebaServiciosRunner(ClienteService clienteService, EntregaService entregaService) {
        this.clienteService = clienteService;
        this.entregaService = entregaService;
    }

    @Override
    public void run(String... args) {
        // 1. Clientes (solo lectura)
        List<Cliente> clientes = clienteService.listar();
        System.out.println("PRUEBA 1 - Clientes encontrados: " + clientes.size());
        if (clientes.isEmpty()) {
            System.out.println("PRUEBA - No hay clientes, se detiene la prueba.");
            return;
        }
        Cliente primero = clientes.get(0);
        System.out.println("PRUEBA 1 - Primero (orden alfabetico): " + primero.getNombre());

        // 2. Buscar por id existente
        Cliente buscado = clienteService.obtenerPorId(primero.getId());
        System.out.println("PRUEBA 2 - obtenerPorId(" + primero.getId() + "): " + buscado.getNombre());

        // 3. Buscar por id inexistente (debe dar error)
        try {
            clienteService.obtenerPorId(999);
        } catch (RuntimeException e) {
            System.out.println("PRUEBA 3 - Error esperado: " + e.getMessage());
        }

        // 4. Entregas (solo lectura)
        List<Entrega> entregas = entregaService.listar();
        System.out.println("PRUEBA 4 - Entregas encontradas: " + entregas.size());

        // 5. Entregas de un cliente (de la mas reciente a la mas antigua)
        List<Entrega> deCliente = entregaService.listarPorCliente(primero.getId());
        System.out.println("PRUEBA 5 - Entregas de " + primero.getNombre() + ": " + deCliente.size());
        if (!deCliente.isEmpty()) {
            Entrega e = deCliente.get(0);
            System.out.println("PRUEBA 5 - Mas reciente: " + e.getTipo() + " " + e.getPeso() + " kg, " + e.getFecha());
        }

        // 6. Validaciones de registrar (todas fallan ANTES de guardar, no insertan nada)
        try {
            entregaService.registrar(999, "Vidrio", 5.0);
        } catch (RuntimeException e) {
            System.out.println("PRUEBA 6a - Error esperado: " + e.getMessage());
        }
        try {
            entregaService.registrar(primero.getId(), "Vidrio", 0.0);
        } catch (RuntimeException e) {
            System.out.println("PRUEBA 6b - Error esperado: " + e.getMessage());
        }
        try {
            entregaService.registrar(primero.getId(), "Metal", 5.0);
        } catch (RuntimeException e) {
            System.out.println("PRUEBA 6c - Error esperado: " + e.getMessage());
        }
    }
}