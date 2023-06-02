package com.example.demo.controller;

import com.example.demo.model.entity.UkPostcode;
import com.example.demo.model.request.CreatePostcodeRequest;
import com.example.demo.model.request.UpdatePostcodeRequest;
import com.example.demo.service.UkPostcodeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author [yun]
 */
@RestController
@RequestMapping("/v1/admin")
@AllArgsConstructor
public class AdminController {

    final private UkPostcodeService ukPostcodeService;

    @PutMapping("/ukpostcodes/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UkPostcode updatePostcode(
            @PathVariable final Long id,
            @RequestBody @Valid final UpdatePostcodeRequest updatePostcodeRequest
    ) {
        return ukPostcodeService.update(
                new UkPostcode()
                        .setId(id)
                        .setLatitude(updatePostcodeRequest.getLat())
                        .setLongitude(updatePostcodeRequest.getLon()));
    }

    @PostMapping("/ukpostcodes")
    @ResponseStatus(HttpStatus.CREATED)
    public UkPostcode createPostcode(@RequestBody @Valid final CreatePostcodeRequest createPostcodeRequest) {
        final UkPostcode ukPostcode = new UkPostcode();
        ukPostcode.setPostcode(createPostcodeRequest.getPostcode())
                .setLatitude(createPostcodeRequest.getLat())
                .setLongitude(createPostcodeRequest.getLon());

        return ukPostcodeService.create(ukPostcode);
    }

    @DeleteMapping("/ukpostcodes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePostcode(@PathVariable final Long id) {
        ukPostcodeService.delete(id);
    }
}
