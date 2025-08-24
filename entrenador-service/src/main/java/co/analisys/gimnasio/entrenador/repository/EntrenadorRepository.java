package co.analisys.gimnasio.entrenador.repository;

import co.analisys.gimnasio.entrenador.model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
}
