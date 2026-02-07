package com.sweetlab.repository;

import com.sweetlab.entity.ContactoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<ContactoEntity, Long> {
    List<ContactoEntity> findByAsunto(String asunto);
    List<ContactoEntity> findByEmail(String email);
    List<ContactoEntity> findByFechaContactoBetween(LocalDateTime inicio, LocalDateTime fin);
}
