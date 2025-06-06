package aforo.productrateplanservice.flatfee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flat-fees")
@RequiredArgsConstructor
public class FlatFeeController {

    private final FlatFeeService service;

    @PostMapping
    public ResponseEntity<FlatFeeDTO> create(@RequestBody FlatFeeCreateUpdateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlatFeeDTO> update(@PathVariable Long id, @RequestBody FlatFeeCreateUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlatFeeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<FlatFeeDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
