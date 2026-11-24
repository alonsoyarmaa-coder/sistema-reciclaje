package com.iestp.sistemareciclaje.servicio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iestp.sistemareciclaje.dao.ClienteRepository;
import com.iestp.sistemareciclaje.dao.EntregaRepository;
import com.iestp.sistemareciclaje.modelo.Cliente;
import com.iestp.sistemareciclaje.modelo.Entrega;

@Service
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final ClienteRepository clienteRepository;

    public EntregaService(EntregaRepository entregaRepository, ClienteRepository clienteRepository) {
        this.entregaRepository = entregaRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Entrega> listar() {
        return entregaRepository.findAll();
    }

    public List<Entrega> listarPorCliente(Integer clienteId) {
        buscarCliente(clienteId);
        return entregaRepository.findByCliente_IdOrderByFechaDesc(clienteId);
    }

    @Transactional
    public Entrega registrar(Integer clienteId, String tipo, Double peso) {
        Cliente cliente = buscarCliente(clienteId);
        validarPeso(peso);

        Entrega entrega = new Entrega();
        entrega.setCliente(cliente);
        entrega.setTipo(normalizarTipo(tipo));
        entrega.setPeso(peso);
        entrega.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return entregaRepository.save(entrega);
    }

    private Cliente buscarCliente(Integer clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id " + clienteId));
    }

    private void validarPeso(Double peso) {
        if (peso == null || peso < 0.01 || peso > 1000000) {
            throw new RuntimeException("El peso debe estar entre 0.01 y 1,000,000.");
        }
    }

    private String normalizarTipo(String tipo) {
        String t = (tipo == null) ? "" : tipo.trim().toLowerCase();
        if (t.equals("plástico") || t.equals("plastico")) {
            return "Plástico";
        }
        if (t.equals("vidrio")) {
            return "Vidrio";
        }
        if (t.equals("papel")) {
            return "Papel";
        }
        throw new RuntimeException("El tipo '" + tipo + "' no es reconocido.");
    }
}