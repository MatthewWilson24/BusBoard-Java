package training.busboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusStopInfo {
    private List<StopPoints> stopPoints;

    public List<StopPoints> getStopPoints() {
        return stopPoints;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StopPoints {
        private String naptanId;
        private double distance;

        public String getNaptanId() {
            return naptanId;
        }

        public double getDistance() {
            return distance;
        }
    }
}
