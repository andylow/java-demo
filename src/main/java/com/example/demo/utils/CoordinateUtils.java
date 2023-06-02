package com.example.demo.utils;

/**
 * @author [yun]
 */
public class CoordinateUtils {

    private static final double NAUTICAL_MILES = 1.1515;
    private static final double KM_IN_MILES = 1.609344;

    /**
     * Return approximate distance in KM between two coordinates.
     *
     * @param fromLat
     * @param fromLon
     * @param toLat
     * @param toLon
     * @return distance in KM
     */
    public static double calculateDistance(
            final double fromLat, final double fromLon,
            final double toLat, final double toLon
    ) {
        double dist = calculate(fromLat, fromLon, toLat, toLon);

        // rad = toRadians(deg)
        // deg = nm / 60;
        // nm = km /  (1.1515 * 1.609344);
        dist = Math.toDegrees(dist);
        dist = dist * 60;
        return dist * NAUTICAL_MILES * KM_IN_MILES;
    }

    private static double calculate(final double lat1, final double lon1, final double lat2, final double lon2) {
        double theta = lon1 - lon2;
        return Math.acos(
                Math.sin(Math.toRadians(lat1)) *
                        Math.sin(Math.toRadians(lat2)) +
                        Math.cos(Math.toRadians(lat1)) *
                                Math.cos(Math.toRadians(lat2)) *
                                Math.cos(Math.toRadians(theta))
        );
    }
}
