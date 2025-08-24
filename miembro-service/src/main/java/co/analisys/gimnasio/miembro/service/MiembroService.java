package co.analisys.gimnasio.miembro.service;

import co.analisys.gimnasio.miembro.model.Miembro;
import co.analisys.gimnasio.miembro.repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MiembroService {
    @Autowired
    private MiembroRepository miembroRepository;

    public Miembro registrarMiembro(Miembro miembro) {
        return miembroRepository.save(miembro);
    }

    public List<Miembro> obtenerTodosMiembros() {
        return miembroRepository.findAll();
    }

    public Optional<Miembro> obtenerMiembroPorId(Long id) {
        return miembroRepository.findById(id);
    }

    public Miembro actualizarMiembro(Long id, Miembro miembro) {
        miembro.setId(id);
        return miembroRepository.save(miembro);
    }

    public void eliminarMiembro(Long id) {
        miembroRepository.deleteById(id);
    }
}
