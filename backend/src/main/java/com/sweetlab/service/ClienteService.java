package com.sweetlab.service;

import com.sweetlab.entity.Cliente;
import com.sweetlab.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> obtenerActivos() {
        return clienteRepository.findByActivoTrue();
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Cliente crear(Cliente cliente) {
        // Validar que el email no exista
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente clienteActualizado) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setEmail(clienteActualizado.getEmail());
            cliente.setTelefono(clienteActualizado.getTelefono());
            cliente.setDireccion(clienteActualizado.getDireccion());
            cliente.setCiudad(clienteActualizado.getCiudad());
            cliente.setCodigoPostal(clienteActualizado.getCodigoPostal());
            cliente.setActivo(clienteActualizado.getActivo());
            return clienteRepository.save(cliente);
        }).orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
