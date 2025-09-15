package co.analisys.gimnasio.clase.controller;

import co.analisys.gimnasio.clase.dto.ClaseConEntrenadorDto;
import co.analisys.gimnasio.clase.model.Clase;
import co.analisys.gimnasio.clase.service.ClaseService;
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
@RequestMapping("/api/clases")
public class ClaseController {
    @Autowired
    private ClaseService claseService;

    @Operation(summary = "Programar una nueva clase", description = "Permite a un administrador programar una clase en el sistema.")
    @ApiResponse(responseCode = "200", description = "Clase creada exitosamente")
    @ApiResponse(responseCode = "403", description = "Acceso denegado")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Clase programarClase(@RequestBody Clase clase) {
        return claseService.programarClase(clase);
    }

    @Operation(summary = "Obtener todas las clases", description = "Devuelve la lista de todas las clases registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<Clase> obtenerTodasClases() {
        return claseService.obtenerTodasClases();
    }

    @Operation(summary = "Obtener clases con entrenador", description = "Devuelve todas las clases junto con la información de su entrenador.")
    @GetMapping("/con-entrenador")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<ClaseConEntrenadorDto> obtenerClasesConEntrenador() {
        return claseService.obtenerClasesConEntrenador();
    }

    @Operation(summary = "Obtener clase por ID", description = "Devuelve la información de una clase específica.")
    @ApiResponse(responseCode = "404", description = "Clase no encontrada")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Clase> obtenerClasePorId(@PathVariable Long id) {
        Optional<Clase> clase = claseService.obtenerClasePorId(id);
        return clase.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener clase con entrenador por ID", description = "Devuelve una clase específica junto con su entrenador.")
    @ApiResponse(responseCode = "404", description = "Clase no encontrada")
    @GetMapping("/{id}/con-entrenador")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ClaseConEntrenadorDto> obtenerClaseConEntrenadorPorId(@PathVariable Long id) {
        Optional<ClaseConEntrenadorDto> clase = claseService.obtenerClaseConEntrenadorPorId(id);
        return clase.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar clase", description = "Permite a un administrador actualizar los datos de una clase.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Clase actualizarClase(@PathVariable Long id, @RequestBody Clase clase) {
        return claseService.actualizarClase(id, clase);
    }

    @Operation(summary = "Eliminar clase", description = "Permite a un administrador eliminar una clase del sistema.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> eliminarClase(@PathVariable Long id) {
        claseService.eliminarClase(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener clases por entrenador", description = "Devuelve todas las clases dictadas por un entrenador específico.")
    @GetMapping("/entrenador/{entrenadorId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<Clase> obtenerClasesPorEntrenador(@PathVariable Long entrenadorId) {
        return claseService.obtenerClasesPorEntrenador(entrenadorId);
    }
}
