package org.opencds.cqf.ruler.plugin.utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * This interface provides utilities for handling FHIR Types.
 */
public interface TypeUtilities {
    /**
     * This function converts a string representation of a FHIR period date to a java.util.Date.
     * 
     * @param date the date to convert
     * @param start whether the date is the start of a period
     * @return the FHIR period date as a java.util.Date type
     */
    public default Date resolveRequestDate(String date, boolean start) {
        // split it up - support dashes or slashes
        String[] dissect = date.contains("-") ? date.split("-") : date.split("/");
        List<Integer> dateVals = new ArrayList<>();
        for (String dateElement : dissect) {
            dateVals.add(Integer.parseInt(dateElement));
        }

        if (dateVals.isEmpty()) {
            throw new IllegalArgumentException("Invalid date");
        }

        // for now support dates up to day precision
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.set(Calendar.YEAR, dateVals.get(0));
        if (dateVals.size() > 1) {
            // java.util.Date months are zero based, hence the negative 1 -- 2014-01 == February 2014
            calendar.set(Calendar.MONTH, dateVals.get(1) - 1);
        }
        if (dateVals.size() > 2)
            calendar.set(Calendar.DAY_OF_MONTH, dateVals.get(2));
        else {
            if (start) {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
            }
            else {
                // get last day of month for end period
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.DATE, -1);
            }
        }
        return calendar.getTime();
    }
}
