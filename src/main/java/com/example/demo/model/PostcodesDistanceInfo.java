package com.example.demo.model;

import com.example.demo.model.entity.UkPostcode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * @author [yun]
 */
@Data
public class PostcodesDistanceInfo {

    private final Location from;
    private final Location to;
    private double distance;
    private String unit = "KM"; // default to KM, ideally should use Enum

    public PostcodesDistanceInfo(@NonNull final UkPostcode fromPostcode, @NonNull final UkPostcode toPostcode) {
        from = new Location(fromPostcode.getPostcode(), fromPostcode.getLatitude(), fromPostcode.getLongitude());
        to = new Location(toPostcode.getPostcode(), toPostcode.getLatitude(), toPostcode.getLongitude());
    }

    @Data
    @AllArgsConstructor
    static class Location {
        private String postcode;
        private double lat;
        private double lon;
    }
}
