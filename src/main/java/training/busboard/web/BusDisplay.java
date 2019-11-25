package training.busboard.web;

import training.busboard.BusInfo;
import training.busboard.TflApiHelper;

import java.util.List;

public class BusDisplay {
    private final String postcode;
    private List<StopDisplay> stopDisplays;

    public BusDisplay(String postcode) {
        this.postcode = postcode;
        stopDisplays = new TflApiHelper().getBusStopDisplays(postcode);
    }

    public String getPostcode() {
        return postcode;
    }

    public List<StopDisplay> getStopDisplays() {
        return stopDisplays;
    }

    public boolean hasNextStop() {
        return !stopDisplays.isEmpty();
    }

    public static class StopDisplay {
        private String busStopName;
        private Double distance;
        private List<BusInfo> nextBuses;

        public StopDisplay(String busStopName, Double distance, List<BusInfo> nextBuses) {
            this.busStopName = busStopName;
            this.distance = distance;
            this.nextBuses = nextBuses;
        }

        public String getBusStopName() {
            return busStopName;
        }

        public Double getDistance() {
            return distance;
        }

        public List<BusInfo> getNextBuses() {
            return nextBuses;
        }

        public boolean hasNextBus() {
            return !nextBuses.isEmpty();
        }

        public String getDisplayDistance() {
            return String.format("%.0f metres", distance);
        }


    }
}
