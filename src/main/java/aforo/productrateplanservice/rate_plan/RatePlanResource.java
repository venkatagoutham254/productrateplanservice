package aforo.productrateplanservice.rate_plan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import java.util.List;


@RestController
@RequestMapping("/api/rate-plans")
@RequiredArgsConstructor
@Tag(name = "Rate Plans", description = "APIs for managing rate plans")
public class RatePlanResource {

    private final RatePlanService ratePlanService;

    @Operation(summary = "Create a new rate plan")
    @PostMapping
    public ResponseEntity<RatePlanDTO> create(@RequestBody CreateRatePlanRequest request) {
        return ResponseEntity.ok(ratePlanService.createRatePlan(request));
    }

    @Operation(summary = "Get a rate plan by ID")
    @GetMapping("/{id}")
    public ResponseEntity<RatePlanDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ratePlanService.getRatePlanById(id));
    }

    @Operation(summary = "List all rate plans")
    @GetMapping
    public ResponseEntity<List<RatePlanDTO>> getAll() {
        return ResponseEntity.ok(ratePlanService.getAllRatePlans());
    }

    @Operation(summary = "Update a rate plan")
    @PutMapping("/{id}")
    public ResponseEntity<RatePlanDTO> update(@PathVariable Long id, @RequestBody UpdateRatePlanRequest request) {
        return ResponseEntity.ok(ratePlanService.updateRatePlan(id, request));
    }

    @Operation(summary = "Delete a rate plan by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ratePlanService.deleteRatePlan(id);
        return ResponseEntity.noContent().build();
    }
}
