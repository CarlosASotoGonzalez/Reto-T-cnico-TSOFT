package com.example.tsoft.demo.client;

import com.example.tsoft.demo.exception.ExternalApiException;
import com.example.tsoft.demo.exception.PetNotFoundException;
import com.example.tsoft.demo.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class PetClientTest {

    @Mock
    private RestTemplate restTemplate;

    private PetClient petClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        petClient = new PetClient(
                restTemplate,
                "https://petstore.swagger.io/v2"
        );
    }

    @Test
    void shouldGetPetFromExternalApi() {
        Pet expectedPet = new Pet(
                10000023L,
                "testingPet1",
                "available"
        );

        when(restTemplate.getForObject(
                "https://petstore.swagger.io/v2/pet/{id}",
                Pet.class,
                10000023L
        )).thenReturn(expectedPet);

        Pet result = petClient.getPet(10000023L);

        assertThat(result).isEqualTo(expectedPet);
    }

    @Test
    void shouldThrowPetNotFoundExceptionWhenExternalApiReturns404() {
        when(restTemplate.getForObject(
                anyString(),
                eq(Pet.class),
                eq(10000023L)
        )).thenThrow(HttpClientErrorException.NotFound.class);

        assertThatThrownBy(() -> petClient.getPet(10000023L))
                .isInstanceOf(PetNotFoundException.class)
                .hasMessageContaining("10000023");
    }

    @Test
    void shouldThrowExternalApiExceptionWhenExternalApiIsUnavailable() {
        when(restTemplate.getForObject(
                anyString(),
                eq(Pet.class),
                eq(10000023L)
        )).thenThrow(new ResourceAccessException("Connection refused"));

        assertThatThrownBy(() -> petClient.getPet(10000023L))
                .isInstanceOf(ExternalApiException.class)
                .hasMessage("Petstore no está disponible");
    }

    @Test
    void shouldAddPetInExternalApi() {
        Pet pet = new Pet(
                10000023L,
                "testingPet1",
                "available"
        );

        when(restTemplate.postForObject(
                "https://petstore.swagger.io/v2/pet",
                pet,
                Pet.class
        )).thenReturn(pet);

        Pet result = petClient.addPet(pet);

        assertThat(result).isEqualTo(pet);

        verify(restTemplate).postForObject(
                "https://petstore.swagger.io/v2/pet",
                pet,
                Pet.class
        );
    }
}