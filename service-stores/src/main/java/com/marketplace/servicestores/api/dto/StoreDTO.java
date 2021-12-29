package com.marketplace.servicestores.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public final class StoreDTO {
    private String name;
    private Date registration;
}
