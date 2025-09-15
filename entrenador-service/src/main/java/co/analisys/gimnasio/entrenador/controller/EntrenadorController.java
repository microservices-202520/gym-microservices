package co.analisys.gimnasio.entrenador.controller;

import co.analisys.gimnasio.entrenador.model.Entrenador;
import co.analisys.gimnasio.entrenador.service.EntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entrenadores")
public class EntrenadorController {
    @Autowired
    private EntrenadorService entrenadorService;

    @Operation(summary = "Agregar entrenador", description = "Crea un nuevo entrenador en el sistema (solo ADMIN).")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Entrenador agregarEntrenador(@RequestBody Entrenador entrenador) {
        return entrenadorService.agregarEntrenador(entrenador);
    }

    @Operation(summary = "Obtener todos los entrenadores", description = "Lista todos los entrenadores registrados.")
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_TRAINER')")
    public List<Entrenador> obtenerTodosEntrenadores() {
        return entrenadorService.obtenerTodosEntrenadores();
    }

    @Operation(summary = "Obtener entrenador por ID", description = "Devuelve la información de un entrenador específico.")
    @ApiResponse(responseCode = "404", description = "Entrenador no encontrado")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public ResponseEntity<Entrenador> obtenerEntrenadorPorId(@PathVariable Long id) {
        Optional<Entrenador> entrenador = entrenadorService.obtenerEntrenadorPorId(id);
        return entrenador.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar entrenador", description = "Permite a un administrador actualizar los datos de un entrenador.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Entrenador actualizarEntrenador(@PathVariable Long id, @RequestBody Entrenador entrenador) {
        return entrenadorService.actualizarEntrenador(id, entrenador);
    }

    @Operation(summary = "Eliminar entrenador", description = "Permite a un administrador eliminar un entrenador del sistema.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> eliminarEntrenador(@PathVariable Long id) {
        entrenadorService.eliminarEntrenador(id);
        return ResponseEntity.noContent().build();
    }
}
