package com.example.tsoft.demo.service;

import com.example.tsoft.demo.client.PetClient;
import com.example.tsoft.demo.model.Pet;
import com.example.tsoft.demo.model.PetCreatedResponse;
import com.example.tsoft.demo.model.PetRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    private PetClient petClient;

    @InjectMocks
    private PetService petService;

    @Test
    void shouldGetPetSuccessfully() {
        Pet expectedPet = new Pet(10000023L, "testingPet1", "available");

        when(petClient.getPet(10000023L)).thenReturn(expectedPet);

        Pet result = petService.getPet(10000023L);

        assertThat(result).isEqualTo(expectedPet);
        verify(petClient).getPet(10000023L);
    }

    @Test
    void shouldCreatePetWithUuidV4AndCurrentDate() {
        PetRequest request = new PetRequest(
                10000023L,
                "available",
                "testingPet1"
        );

        Pet createdPet = new Pet(
                10000023L,
                "testingPet1",
                "available"
        );

        when(petClient.addPet(any(Pet.class))).thenReturn(createdPet);

        OffsetDateTime before = OffsetDateTime.now();

        PetCreatedResponse result = petService.addPet(request);

        OffsetDateTime after = OffsetDateTime.now();

        UUID transactionId = UUID.fromString(result.transactionId());
        OffsetDateTime dateCreated = OffsetDateTime.parse(result.dateCreated());

        assertThat(transactionId.version()).isEqualTo(4);
        assertThat(dateCreated).isBetween(before, after);
        assertThat(result.status()).isEqualTo("available");
        assertThat(result.name()).isEqualTo("testingPet1");

        verify(petClient).addPet(
                new Pet(10000023L, "testingPet1", "available")
        );
    }

    @Test
    void shouldPropagateExceptionWhenPetClientFails() {
        PetRequest request = new PetRequest(
                10000023L,
                "available",
                "testingPet1"
        );

        when(petClient.addPet(any(Pet.class)))
                .thenThrow(new RuntimeException("External API error"));

        org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> petService.addPet(request)
        );
    }
}