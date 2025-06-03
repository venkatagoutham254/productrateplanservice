package aforo.productrateplanservice.minimumcommitment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/minimum-commitments")
@RequiredArgsConstructor
public class MinimumCommitmentController {

    private final MinimumCommitmentService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody MinimumCommitmentDTO dto) {
        service.create(dto);
        return ResponseEntity.ok("Minimum Commitment created");
    }

    @GetMapping
    public ResponseEntity<List<MinimumCommitmentDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MinimumCommitmentDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody MinimumCommitmentDTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok("Minimum Commitment updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Minimum Commitment soft deleted");
    }
}
