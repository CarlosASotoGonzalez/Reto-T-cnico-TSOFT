package com.example.tsoft.demo.exception;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(Long petId) {
        super("No se encontró la mascota con id: " + petId);
    }
}