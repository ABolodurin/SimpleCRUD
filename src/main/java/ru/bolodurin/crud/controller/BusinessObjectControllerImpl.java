package ru.bolodurin.crud.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.crud.config.SwaggerConfig;
import ru.bolodurin.crud.model.dto.BusinessObjectDTO;
import ru.bolodurin.crud.service.BusinessObjectService;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = SwaggerConfig.TAG)
public class BusinessObjectControllerImpl implements BusinessObjectController {
    private final BusinessObjectService objectService;

    @Override
    @PostMapping
    @Operation(summary = "Add new object",
            responses = @ApiResponse(responseCode = "201", description = "Created"))
    public @ResponseBody ResponseEntity<List<BusinessObjectDTO>> create(
            @Valid @RequestBody BusinessObjectDTO objectDTO) {
        return ResponseEntity.status(201).body(objectService.add(objectDTO));
    }

    @Override
    @GetMapping
    @Operation(summary = "Show all objects",
            responses = @ApiResponse(responseCode = "200", description = "Successful"))
    public @ResponseBody ResponseEntity<List<BusinessObjectDTO>> read() {
        return ResponseEntity.ok(objectService.show());
    }

    @Override
    @PutMapping
    @Operation(summary = "Update existing object",
            responses = @ApiResponse(responseCode = "200", description = "Successful"))
    public @ResponseBody ResponseEntity<List<BusinessObjectDTO>> update(
            @Valid @RequestBody BusinessObjectDTO objectDTO) {
        return ResponseEntity.ok(objectService.update(objectDTO));
    }

    @Override
    @DeleteMapping
    @Operation(summary = "Delete existing object",
            responses = @ApiResponse(responseCode = "200", description = "Successful"))
    public @ResponseBody ResponseEntity<List<BusinessObjectDTO>> delete(
            @Valid @RequestBody BusinessObjectDTO objectDTO) {
        return ResponseEntity.ok(objectService.delete(objectDTO));
    }

}
