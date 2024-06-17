package org.LogAggregatorTool.operations;

import org.LogAggregatorTool.constants.LogAggregatorConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Date;

/**
 * This class sorts the timestamps using Comparator Interface.
 */
public class TimestampSorter {
    private static final List<SimpleDateFormat> DATE_FORMATS = Arrays.asList(
            new SimpleDateFormat(LogAggregatorConstants.DATE_FORMAT),
            new SimpleDateFormat(LogAggregatorConstants.NEW_DATE_FORMAT)
    );

    private static Date parseDate(String dateString) {
        for (SimpleDateFormat dateFormat : DATE_FORMATS) {
            try {
                return dateFormat.parse(dateString);
            } catch (ParseException parseException) {
                //parseException iterates for every mismatch.So, not printing anything.
            }
        }
        return new Date(LogAggregatorConstants.DEFAULT_INT_VALUE);
    }

    private static Comparator<String> timestampComparator = (dateString1, dateString2) -> {
        Date date1 = parseDate(dateString1);
        Date date2 = parseDate(dateString2);
        return date1.compareTo(date2);
    };

    public void sortTimeStamps(ArrayList<String> sortedTimestampsList) {
        sortedTimestampsList.sort(timestampComparator);
    }
}
