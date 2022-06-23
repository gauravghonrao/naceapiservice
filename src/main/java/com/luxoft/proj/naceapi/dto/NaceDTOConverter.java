package com.luxoft.proj.naceapi.dto;

import com.luxoft.proj.naceapi.entity.NaceEntity;

public class NaceDTOConverter implements IConverter
{
    @Override
    public NaceDTO convert(NaceEntity entity)
    {
        return NaceDTO.builder()
                      .orderId(entity.getId())
                      .level(entity.getLevel())
                      .code(entity.getCode())
                      .parent(entity.getParent())
                      .description(entity.getDescription())
                      .also_includes(entity.getAlso_includes())
                      .includes(entity.getIncludes())
                      .excludes(entity.getExcludes())
                      .rulings(entity.getRulings())
                      .referenceISIC_Rev4(entity.getReferences())
                      .build();
    }

    @Override
    public NaceEntity convert(NaceDTO dto)
    {
        return NaceEntity.builder()
                         .id(dto.getOrderId())
                         .level(dto.getLevel())
                         .code(dto.getCode())
                         .parent(dto.getParent())
                         .description(dto.getDescription())
                         .also_includes(dto.getAlso_includes())
                         .includes(dto.getIncludes())
                         .excludes(dto.getExcludes())
                         .rulings(dto.getRulings())
                         .references(dto.getReferenceISIC_Rev4())
                         .build();
    }
}
