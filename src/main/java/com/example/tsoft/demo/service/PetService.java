package com.example.tsoft.demo.service;

import com.example.tsoft.demo.client.PetClient;
import com.example.tsoft.demo.model.Pet;
import com.example.tsoft.demo.model.PetCreatedResponse;
import com.example.tsoft.demo.model.PetRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class PetService {

    private static final Logger log = LoggerFactory.getLogger(PetService.class);

    private final PetClient petClient;

    public PetService(PetClient petClient) {
        this.petClient = petClient;
    }

    public Pet getPet(Long petId) {
        Pet pet = petClient.getPet(petId);

        log.info("Pet obtenido: {}", pet);

        return pet;
    }

    public PetCreatedResponse addPet(PetRequest request) {
        Pet pet = new Pet(request.id(), request.name(), request.status());
        Pet createdPet = petClient.addPet(pet);

        PetCreatedResponse response = new PetCreatedResponse(
                UUID.randomUUID().toString(),
                OffsetDateTime.now().toString(),
                createdPet.status(),
                createdPet.name()
        );

        log.info("Pet creado: {}", response);

        return response;
    }
}