package aforo.productrateplanservice.discount;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody DiscountDTO dto) {
        service.create(dto);
        return ResponseEntity.ok("Discount created");
    }

    @GetMapping
    public ResponseEntity<List<DiscountDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody DiscountDTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok("Discount updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Discount soft deleted");
    }
}
