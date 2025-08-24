package co.analisys.gimnasio.miembro.repository;

import co.analisys.gimnasio.miembro.model.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {
}
