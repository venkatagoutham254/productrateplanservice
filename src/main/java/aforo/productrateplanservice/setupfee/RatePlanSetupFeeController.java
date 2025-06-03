package aforo.productrateplanservice.setupfee;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rate-plan-setup-fee")
@RequiredArgsConstructor
public class RatePlanSetupFeeController {

    private final RatePlanSetupFeeService setupFeeService;

    @PostMapping
    public ResponseEntity<String> createSetupFee(@RequestBody RatePlanSetupFeeDTO dto) {
        setupFeeService.createSetupFee(dto);
        return ResponseEntity.ok("Setup Fee created successfully");
 
    }

    @GetMapping
public ResponseEntity<List<RatePlanSetupFeeDTO>> getAll() {
    return ResponseEntity.ok(setupFeeService.getAll());
}

@GetMapping("/{id}")
public ResponseEntity<RatePlanSetupFeeDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok(setupFeeService.getById(id));
}

@PutMapping("/{id}")
public ResponseEntity<String> update(@PathVariable Long id, @RequestBody RatePlanSetupFeeDTO dto) {
    setupFeeService.updateSetupFee(id, dto);
    return ResponseEntity.ok("Updated successfully");
}

@DeleteMapping("/{id}")
public ResponseEntity<String> delete(@PathVariable Long id) {
    setupFeeService.deleteSetupFee(id);
    return ResponseEntity.ok("Soft deleted successfully");
}
   
}
