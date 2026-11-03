package com.example.tsoft.demo.client;

import com.example.tsoft.demo.exception.ExternalApiException;
import com.example.tsoft.demo.exception.PetNotFoundException;
import com.example.tsoft.demo.model.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class PetClient {
    private static final Logger log = LoggerFactory.getLogger(PetClient.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public PetClient(
            RestTemplate restTemplate,
            @Value("${petstore.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public Pet getPet(Long petId) {
        try {
            log.info("Consultando Petstore: GET {}/pet/{}", baseUrl, petId);

            return restTemplate.getForObject(
                    baseUrl + "/pet/{id}",
                    Pet.class,
                    petId
            );

        } catch (HttpClientErrorException.NotFound ex) {
            throw new PetNotFoundException(petId);

        } catch (ResourceAccessException ex) {
            throw new ExternalApiException(
                    "Petstore no está disponible", ex);
        }
    }

    public Pet addPet(Pet pet) {
        try {
            log.info("Creando mascota en Petstore con id={}", pet.id());

            return restTemplate.postForObject(
                    baseUrl + "/pet",
                    pet,
                    Pet.class
            );

        } catch (ResourceAccessException ex) {
            throw new ExternalApiException(
                    "Petstore no está disponible", ex);
        }
    }
}