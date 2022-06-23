package com.luxoft.proj.naceapi.exception;

import java.util.UUID;

import lombok.Data;

@Data
public class ErrorResponseDto
{
    private UUID id;
    private String code;

    public ErrorResponseDto(String code)
    {
        id = UUID.randomUUID();
        this.code = code;
    }
}
