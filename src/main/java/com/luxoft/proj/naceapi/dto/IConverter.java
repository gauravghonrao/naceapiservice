package com.luxoft.proj.naceapi.dto;

import com.luxoft.proj.naceapi.entity.NaceEntity;

public interface IConverter
{
    NaceDTO convert(NaceEntity entity);
    NaceEntity convert(NaceDTO dto);
}
