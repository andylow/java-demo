package com.example.demo.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.example.demo.Constants.UK_POSTCODE_REGEX;

@Data
public class CreatePostcodeRequest {

    @NotBlank
    @Pattern(regexp = UK_POSTCODE_REGEX, message = "Invalid UK postcode")
    private String postcode;

    @NotNull(message = "'lat' is mandatory field.")
    private Double lat;

    @NotNull(message = "'lon' is mandatory field.")
    private Double lon;
}
