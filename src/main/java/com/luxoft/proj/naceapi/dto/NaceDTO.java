package com.luxoft.proj.naceapi.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NaceDTO
{
    @NotNull
    int orderId;

    int level;
    String code;
    String parent;
    String description;
    String includes;
    String also_includes;
    String rulings;
    String excludes;
    String referenceISIC_Rev4;
}
