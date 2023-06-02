package com.example.demo.controller;

import com.example.demo.model.CustomPageable;
import com.example.demo.model.PostcodesDistanceInfo;
import com.example.demo.model.entity.UkPostcode;
import com.example.demo.service.UkPostcodeService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.example.demo.Constants.UK_POSTCODE_REGEX;

/**
 * @author [yun]
 */

@RestController
@Validated
@RequestMapping("/v1/ukpostcodes")
@Slf4j
@AllArgsConstructor
public class ApiController {
    final private UkPostcodeService ukPostcodeService;

    @GetMapping
    public CustomPageable<UkPostcode> getPostcodes(
            @RequestParam(required = false)
            @Min(1) @Max(1000)
            final Integer size,
            @RequestParam(required = false)
            @Min(1)
            final Integer page
    ){
        // Determine default if not provided.
        final int returnSize = Objects.isNull(size) ? 2 : size;
        final int returnPage = Objects.isNull(page) ? 1 : page;

        final var pageable = PageRequest.of(returnPage-1, returnSize);
        final var ukPostcodePage = ukPostcodeService.findAll(pageable);

        final var postcodeList = new CustomPageable<>(ukPostcodePage);
        return postcodeList;
    }

    @GetMapping("/distance")
    public PostcodesDistanceInfo getDistance(
            @RequestParam
            @NotBlank
            @Pattern(regexp = UK_POSTCODE_REGEX, message = "Invalid UK postcode")
            final String from,
            @RequestParam
            @NotBlank
            @Pattern(regexp = UK_POSTCODE_REGEX, message = "Invalid UK postcode")
            final String to
    ) {
        log.info("getDistance", StructuredArguments.value("from", from), StructuredArguments.value("to", to));
        return ukPostcodeService.calculateDistance(from, to);
    }
}
