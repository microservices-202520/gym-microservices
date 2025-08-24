package co.analisys.gimnasio.entrenador.service;

import co.analisys.gimnasio.entrenador.model.Entrenador;
import co.analisys.gimnasio.entrenador.repository.EntrenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrenadorService {
    @Autowired
    private EntrenadorRepository entrenadorRepository;

    public Entrenador agregarEntrenador(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    public List<Entrenador> obtenerTodosEntrenadores() {
        return entrenadorRepository.findAll();
    }

    public Optional<Entrenador> obtenerEntrenadorPorId(Long id) {
        return entrenadorRepository.findById(id);
    }

    public Entrenador actualizarEntrenador(Long id, Entrenador entrenador) {
        entrenador.setId(id);
        return entrenadorRepository.save(entrenador);
    }

    public void eliminarEntrenador(Long id) {
        entrenadorRepository.deleteById(id);
    }
}
