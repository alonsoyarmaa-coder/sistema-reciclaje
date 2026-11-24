package com.iestp.sistemareciclaje.servicio;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.iestp.sistemareciclaje.dao.ClienteRepository;
import com.iestp.sistemareciclaje.dao.EntregaRepository;
import com.iestp.sistemareciclaje.modelo.Cliente;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EntregaRepository entregaRepository;

    public ClienteService(ClienteRepository clienteRepository,
                          EntregaRepository entregaRepository) {
        this.clienteRepository = clienteRepository;
        this.entregaRepository = entregaRepository;
    }

    public List<Cliente> listar() {
        return clienteRepository.findAllByOrderByNombreAsc();
    }

    public Cliente obtenerPorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id " + id));
    }

    @Transactional
    public Cliente crear(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente actualizar(Integer id, Cliente datos) {
        Cliente existente = obtenerPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setTelefono(datos.getTelefono());
        existente.setEmail(datos.getEmail());
        return clienteRepository.save(existente);
    }

    @Transactional
    public void eliminar(Integer id) {
        Cliente existente = obtenerPorId(id);
        // regla de negocio: no se elimina un cliente que tenga entregas
        if (entregaRepository.existsByCliente_Id(id)) {
            throw new RuntimeException("No se puede eliminar: el cliente tiene entregas registradas");
        }
        clienteRepository.delete(existente);
    }
}