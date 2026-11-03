package com.example.tsoft.demo.controller;

import com.example.tsoft.demo.model.Pet;
import com.example.tsoft.demo.model.PetCreatedResponse;
import com.example.tsoft.demo.model.PetRequest;
import com.example.tsoft.demo.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pet")
@Tag(name = "Pets", description = "Operaciones de mascotas")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{petId}")
    @Operation(summary = "Consultar una mascota")
    public Pet getPet(@PathVariable Long petId) {
        return petService.getPet(petId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una mascota")
    public PetCreatedResponse addPet(
            @Valid @RequestBody PetRequest request) {
        return petService.addPet(request);
    }
}