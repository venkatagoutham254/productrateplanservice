package aforo.productrateplanservice.tieredpricing;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiered-pricing")
@RequiredArgsConstructor
public class TieredPricingController {

    private final TieredPricingService service;

    @PostMapping
    public ResponseEntity<TieredPricingDTO> create(@RequestBody TieredPricingDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TieredPricingDTO> update(@PathVariable Long id, @RequestBody TieredPricingDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TieredPricingDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<TieredPricingDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
