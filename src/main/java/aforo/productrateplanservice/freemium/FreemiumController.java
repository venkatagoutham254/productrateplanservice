package aforo.productrateplanservice.freemium;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/freemium")
@RequiredArgsConstructor
public class FreemiumController {

    private final FreemiumService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody FreemiumDTO dto) {
        service.create(dto);
        return ResponseEntity.ok("Freemium created");
    }

    @GetMapping
    public ResponseEntity<List<FreemiumDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FreemiumDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody FreemiumDTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok("Freemium updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Freemium soft deleted");
    }
}
