package com.example.tsoft.demo.model;

public record PetCreatedResponse(String transactionId, String dateCreated, String status, String name) {}