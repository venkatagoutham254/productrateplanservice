package aforo.productrateplanservice.overagecharges;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/overage-charges")
@RequiredArgsConstructor
public class OverageChargeController {

    private final OverageChargeService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody OverageChargeDTO dto) {
        service.create(dto);
        return ResponseEntity.ok("Overage Charge created");
    }

    @GetMapping
    public ResponseEntity<List<OverageChargeDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OverageChargeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody OverageChargeDTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok("Overage Charge updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Overage Charge soft deleted");
    }
}
