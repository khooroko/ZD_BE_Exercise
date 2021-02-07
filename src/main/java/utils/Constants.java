package main.java.utils;

import main.java.model.StationCode;
import org.joda.time.DateTime;

public class Constants {
    public static final float minLengthMultiplier = 1.5f;

    public static class TimeOfDay implements Comparable<TimeOfDay> {
        private final int hour;
        private final int minute;

        public TimeOfDay(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        public int getHour() {
            return hour;
        }

        public int getMinute() {
            return minute;
        }

        @Override
        public int compareTo(TimeOfDay other) {
            if (hour != other.hour) {
                return hour - other.hour;
            } else {
                return minute - other.minute;
            }
        }

        public boolean isBetween(TimeOfDay start, TimeOfDay end) {
            if (start.hour > end.hour || (start.hour == end.hour && start.minute > end.minute)) {
                return compareTo(start) >= 0 || compareTo(end) < 0;
            } else {
                return compareTo(start) >= 0 && compareTo(end) < 0;
            }
        }
    }

    public static class TravelTimes {

        public static class Peak {
            public static final TimeOfDay morningStart = new TimeOfDay(6, 0);
            public static final TimeOfDay morningEnd =  new TimeOfDay(9, 0);
            public static final TimeOfDay eveningStart = new TimeOfDay(18, 0);
            public static final TimeOfDay eveningEnd =  new TimeOfDay(21, 0);
            public static final int NS = 12;
            public static final int NE = 12;
            public static final int TRANSFER = 15;
            public static final int OTHERS = 10;
        }

        public static class Night {
            public static final TimeOfDay start = new TimeOfDay(22, 0);
            public static final TimeOfDay end =  new TimeOfDay(6, 0);
            public static final int DT = -1;
            public static final int CG = -1;
            public static final int CE = -1;
            public static final int TE = 8;
            public static final int TRANSFER = 10;
            public static final int OTHERS = 10;
        }

        public static class NonPeak {
            public static final int DT = 8;
            public static final int TE = 8;
            public static final int TRANSFER = 10;
            public static final int OTHERS = 10;
        }

        // returns travel time in minutes
        // -1 means the line does not operate at this time
        public static int getTravelTime(StationCode origin, StationCode dest, DateTime date) {
            TimeOfDay currentTime = new TimeOfDay(date.getHourOfDay(), date.getMinuteOfHour());
            if (currentTime.isBetween(Peak.morningStart, Peak.morningEnd)
                    || currentTime.isBetween(Peak.eveningStart, Peak.eveningEnd)) {
                if (!origin.getLine().equals(dest.getLine())) {
                   return Peak.TRANSFER;
                } else {
                    switch (origin.getLine()) {
                        case "NS":
                            return Peak.NS;
                        case "NE":
                            return Peak.NE;
                        default:
                            return Peak.OTHERS;
                    }
                }
            } else if (currentTime.isBetween(Night.start, Night.end)) {
                if (!origin.getLine().equals(dest.getLine())) {
                    return Night.TRANSFER;
                } else {
                    switch (origin.getLine()) {
                        case "DT":
                            return Night.DT;
                        case "CG":
                            return Night.CG;
                        case "CE":
                            return Night.CE;
                        case "TE":
                            return Night.TE;
                        default:
                            return Night.OTHERS;
                    }
                }
            } else {
                if (!origin.getLine().equals(dest.getLine())) {
                    return NonPeak.TRANSFER;
                } else {
                    switch (origin.getLine()) {
                        case "DT":
                            return NonPeak.DT;
                        case "TE":
                            return NonPeak.TE;
                        default:
                            return NonPeak.OTHERS;
                    }
                }
            }
        }
    }
}
