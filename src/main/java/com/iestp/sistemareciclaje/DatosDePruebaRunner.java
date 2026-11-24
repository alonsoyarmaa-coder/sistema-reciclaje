package com.iestp.sistemareciclaje;

import com.iestp.sistemareciclaje.dao.ClienteRepository;
import com.iestp.sistemareciclaje.dao.EntregaRepository;
import com.iestp.sistemareciclaje.dao.LogRepository;
import com.iestp.sistemareciclaje.dao.UsuarioRepository;
import com.iestp.sistemareciclaje.modelo.Entrega;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatosDePruebaRunner implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final EntregaRepository entregaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LogRepository logRepository;

    public DatosDePruebaRunner(ClienteRepository clienteRepository,
                               EntregaRepository entregaRepository,
                               UsuarioRepository usuarioRepository,
                               LogRepository logRepository) {
        this.clienteRepository = clienteRepository;
        this.entregaRepository = entregaRepository;
        this.usuarioRepository = usuarioRepository;
        this.logRepository = logRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("PRUEBA clientes: " + clienteRepository.count());
        System.out.println("PRUEBA entregas: " + entregaRepository.count());
        System.out.println("PRUEBA usuarios: " + usuarioRepository.count());
        System.out.println("PRUEBA logs: " + logRepository.count());

        entregaRepository.findAll().stream().findFirst().ifPresent((Entrega e) -> {
            String nombre = (e.getCliente() != null) ? e.getCliente().getNombre() : "sin cliente";
            System.out.println("PRUEBA primera entrega: id=" + e.getId() + ", tipo=" + e.getTipo()
                    + ", peso=" + e.getPeso() + ", fecha=" + e.getFecha() + ", cliente=" + nombre);
        });
    }
}