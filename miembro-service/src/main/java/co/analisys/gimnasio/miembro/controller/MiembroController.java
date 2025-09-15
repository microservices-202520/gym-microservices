package co.analisys.gimnasio.miembro.controller;

import co.analisys.gimnasio.miembro.model.Miembro;
import co.analisys.gimnasio.miembro.service.MiembroService;
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
@RequestMapping("/api/miembros")
public class MiembroController {
    @Autowired
    private MiembroService miembroService;

    @Operation(summary = "Registrar miembro", description = "Permite registrar un nuevo miembro (solo ADMIN).")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Miembro registrarMiembro(@RequestBody Miembro miembro) {
        return miembroService.registrarMiembro(miembro);
    }

    @Operation(summary = "Listar miembros", description = "Devuelve todos los miembros registrados en el sistema.")
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<Miembro> obtenerTodosMiembros() {
        return miembroService.obtenerTodosMiembros();
    }

    @Operation(summary = "Obtener miembro por ID", description = "Devuelve la información de un miembro específico.")
    @ApiResponse(responseCode = "404", description = "Miembro no encontrado")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Miembro> obtenerMiembroPorId(@PathVariable Long id) {
        Optional<Miembro> miembro = miembroService.obtenerMiembroPorId(id);
        return miembro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar miembro", description = "Permite a un administrador actualizar los datos de un miembro.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Miembro actualizarMiembro(@PathVariable Long id, @RequestBody Miembro miembro) {
        return miembroService.actualizarMiembro(id, miembro);
    }

    @Operation(summary = "Eliminar miembro", description = "Permite a un administrador eliminar un miembro del sistema.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> eliminarMiembro(@PathVariable Long id) {
        miembroService.eliminarMiembro(id);
        return ResponseEntity.noContent().build();
    }
}
