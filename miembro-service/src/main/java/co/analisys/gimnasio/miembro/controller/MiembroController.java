package co.analisys.gimnasio.miembro.controller;

import co.analisys.gimnasio.miembro.model.Miembro;
import co.analisys.gimnasio.miembro.service.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/miembros")
public class MiembroController {
    @Autowired
    private MiembroService miembroService;

    @PostMapping
    public Miembro registrarMiembro(@RequestBody Miembro miembro) {
        return miembroService.registrarMiembro(miembro);
    }

    @GetMapping
    public List<Miembro> obtenerTodosMiembros() {
        return miembroService.obtenerTodosMiembros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Miembro> obtenerMiembroPorId(@PathVariable Long id) {
        Optional<Miembro> miembro = miembroService.obtenerMiembroPorId(id);
        return miembro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Miembro actualizarMiembro(@PathVariable Long id, @RequestBody Miembro miembro) {
        return miembroService.actualizarMiembro(id, miembro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMiembro(@PathVariable Long id) {
        miembroService.eliminarMiembro(id);
        return ResponseEntity.noContent().build();
    }
}
