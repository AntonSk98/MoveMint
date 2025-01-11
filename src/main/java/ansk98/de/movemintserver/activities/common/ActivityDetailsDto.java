package ansk98.de.movemintserver.activities.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generic DTO containing additional activity details.
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public class ActivityDetailsDto {


    private final Map<String, Object> activityDetails = new HashMap<>();

    public ActivityDetailsDto addDetail(String key, Object value) {
        activityDetails.put(key, value);
        return this;
    }

    public Map<String, Object> getActivityDetails() {
        return Collections.unmodifiableMap(activityDetails);
    }
}
