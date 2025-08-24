package co.analisys.gimnasio.clase.controller;

import co.analisys.gimnasio.clase.dto.ClaseConEntrenadorDto;
import co.analisys.gimnasio.clase.model.Clase;
import co.analisys.gimnasio.clase.service.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clases")
public class ClaseController {
    @Autowired
    private ClaseService claseService;

    @PostMapping
    public Clase programarClase(@RequestBody Clase clase) {
        return claseService.programarClase(clase);
    }

    @GetMapping
    public List<Clase> obtenerTodasClases() {
        return claseService.obtenerTodasClases();
    }
    
    @GetMapping("/con-entrenador")
    public List<ClaseConEntrenadorDto> obtenerClasesConEntrenador() {
        return claseService.obtenerClasesConEntrenador();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clase> obtenerClasePorId(@PathVariable Long id) {
        Optional<Clase> clase = claseService.obtenerClasePorId(id);
        return clase.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}/con-entrenador")
    public ResponseEntity<ClaseConEntrenadorDto> obtenerClaseConEntrenadorPorId(@PathVariable Long id) {
        Optional<ClaseConEntrenadorDto> clase = claseService.obtenerClaseConEntrenadorPorId(id);
        return clase.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Clase actualizarClase(@PathVariable Long id, @RequestBody Clase clase) {
        return claseService.actualizarClase(id, clase);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarClase(@PathVariable Long id) {
        claseService.eliminarClase(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/entrenador/{entrenadorId}")
    public List<Clase> obtenerClasesPorEntrenador(@PathVariable Long entrenadorId) {
        return claseService.obtenerClasesPorEntrenador(entrenadorId);
    }
}
