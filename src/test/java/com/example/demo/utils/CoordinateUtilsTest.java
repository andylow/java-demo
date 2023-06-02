package com.example.demo.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author [yun]
 */
class CoordinateUtilsTest {

    @Test
    void Given_twoCoordinates_WhenRun_calculateDistance_ShouldGet_distanceInKM() {
        final double expectedDistance = 100.71823253308419;

        final double lat1 = 51.495373, lon1 = -0.147421;
        final double lat2 = 50.860371, lon2 = -1.177887;

        double actualDistance = CoordinateUtils.calculateDistance(lat1, lon1, lat2, lon2);

        assertEquals(expectedDistance, actualDistance);
    }
}