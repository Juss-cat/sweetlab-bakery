package com.sweetlab.repository;

import com.sweetlab.entity.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    List<Promocion> findByActivaTrue();
    Optional<Promocion> findByCodigo(String codigo);
}
