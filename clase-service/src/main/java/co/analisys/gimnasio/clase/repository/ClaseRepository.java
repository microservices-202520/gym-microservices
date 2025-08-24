package co.analisys.gimnasio.clase.repository;

import co.analisys.gimnasio.clase.model.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {
    List<Clase> findByEntrenadorId(Long entrenadorId);
}
