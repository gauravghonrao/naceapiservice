package com.luxoft.proj.naceapi.validator;

public interface IValidator<T>
{
    public boolean isValid(T object);
}
