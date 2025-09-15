package co.analisys.gimnasio.equipo.controller;

import co.analisys.gimnasio.equipo.model.Equipo;
import co.analisys.gimnasio.equipo.service.EquipoService;
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
@RequestMapping("/api/equipos")
public class EquipoController {
    @Autowired
    private EquipoService equipoService;

    @Operation(summary = "Registrar equipo", description = "Agrega un nuevo equipo (solo ADMIN).")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Equipo agregarEquipo(@RequestBody Equipo equipo) {
        return equipoService.agregarEquipo(equipo);
    }

    @Operation(summary = "Listar equipos", description = "Devuelve todos los equipos registrados.")
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<Equipo> obtenerTodosEquipos() {
        return equipoService.obtenerTodosEquipos();
    }

    @Operation(summary = "Obtener equipo por ID", description = "Devuelve la información de un equipo específico.")
    @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Equipo> obtenerEquipoPorId(@PathVariable Long id) {
        Optional<Equipo> equipo = equipoService.obtenerEquipoPorId(id);
        return equipo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar equipo", description = "Permite a un administrador actualizar los datos de un equipo.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Equipo actualizarEquipo(@PathVariable Long id, @RequestBody Equipo equipo) {
        return equipoService.actualizarEquipo(id, equipo);
    }

    @Operation(summary = "Eliminar equipo", description = "Permite a un administrador eliminar un equipo del sistema.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable Long id) {
        equipoService.eliminarEquipo(id);
        return ResponseEntity.noContent().build();
    }
}
