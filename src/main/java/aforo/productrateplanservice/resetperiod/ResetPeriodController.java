package aforo.productrateplanservice.resetperiod;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reset-periods")
@RequiredArgsConstructor
public class ResetPeriodController {

    private final ResetPeriodService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ResetPeriodDTO dto) {
        service.create(dto);
        return ResponseEntity.ok("Reset Period created");
    }

    @GetMapping
    public ResponseEntity<List<ResetPeriodDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResetPeriodDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ResetPeriodDTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok("Reset Period updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Reset Period soft deleted");
    }
}
