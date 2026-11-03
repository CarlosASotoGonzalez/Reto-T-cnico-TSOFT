package com.example.tsoft.demo.controller;

import com.example.tsoft.demo.exception.GlobalExceptionHandler;
import com.example.tsoft.demo.exception.PetNotFoundException;
import com.example.tsoft.demo.model.Pet;
import com.example.tsoft.demo.model.PetCreatedResponse;
import com.example.tsoft.demo.service.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
@Import(GlobalExceptionHandler.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @Test
    void shouldReturnPet() throws Exception {
        when(petService.getPet(10000023L))
                .thenReturn(new Pet(
                        10000023L,
                        "testingPet1",
                        "available"
                ));

        mockMvc.perform(get("/api/pet/10000023"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10000023))
                .andExpect(jsonPath("$.name").value("testingPet1"))
                .andExpect(jsonPath("$.status").value("available"));
    }

    @Test
    void shouldReturn404WhenPetDoesNotExist() throws Exception {
        when(petService.getPet(10000023L))
                .thenThrow(new PetNotFoundException(10000023L));

        mockMvc.perform(get("/api/pet/10000023"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value("/api/pet/10000023"));
    }

    @Test
    void shouldCreatePet() throws Exception {
        when(petService.addPet(org.mockito.ArgumentMatchers.any()))
                .thenReturn(new PetCreatedResponse(
                        "550e8400-e29b-41d4-a716-446655440000",
                        "2026-11-03T00:00:00Z",
                        "available",
                        "testingPet1"
                ));

        String requestBody = """
                {
                    "id": 10000023,
                    "status": "available",
                    "name": "testingPet1"
                }
                """;

        mockMvc.perform(post("/api/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").exists())
                .andExpect(jsonPath("$.dateCreated").exists())
                .andExpect(jsonPath("$.status").value("available"))
                .andExpect(jsonPath("$.name").value("testingPet1"));
    }

    @Test
    void shouldReturn400WhenRequestIsInvalid() throws Exception {
        String invalidRequest = """
                {
                    "id": -1,
                    "status": "",
                    "name": ""
                }
                """;

        mockMvc.perform(post("/api/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/api/pet"));
    }
}