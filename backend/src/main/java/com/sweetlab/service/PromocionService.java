package com.sweetlab.service;

import com.sweetlab.entity.Promocion;
import com.sweetlab.repository.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    public List<Promocion> obtenerTodas() {
        return promocionRepository.findAll();
    }

    public Optional<Promocion> obtenerPorId(Long id) {
        return promocionRepository.findById(id);
    }

    public List<Promocion> obtenerActivas() {
        return promocionRepository.findByActivaTrue();
    }

    public Optional<Promocion> obtenerPorCodigo(String codigo) {
        return promocionRepository.findByCodigo(codigo);
    }

    public Promocion crear(Promocion promocion) {
        return promocionRepository.save(promocion);
    }

    public Promocion actualizar(Long id, Promocion promocionActualizada) {
        return promocionRepository.findById(id).map(promocion -> {
            promocion.setNombre(promocionActualizada.getNombre());
            promocion.setDescripcion(promocionActualizada.getDescripcion());
            promocion.setDescuento(promocionActualizada.getDescuento());
            promocion.setTipo(promocionActualizada.getTipo());
            promocion.setCodigo(promocionActualizada.getCodigo());
            promocion.setActiva(promocionActualizada.getActiva());
            promocion.setFechaInicio(promocionActualizada.getFechaInicio());
            promocion.setFechaFin(promocionActualizada.getFechaFin());
            return promocionRepository.save(promocion);
        }).orElseThrow(() -> new RuntimeException("Promoción no encontrada con id: " + id));
    }

    public void eliminar(Long id) {
        promocionRepository.deleteById(id);
    }
}
