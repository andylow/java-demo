package com.example.demo.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePostcodeRequest {
    @NotNull(message = "'lat' is mandatory field.")
    private Double lat;

    @NotNull(message = "'lon' is mandatory field.")
    private Double lon;
}
