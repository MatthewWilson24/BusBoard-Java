package training.busboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusStopInfo {
    private List<StopPoint> stopPoints;

    public List<StopPoint> getStopPoints() {
        return stopPoints;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StopPoint {
        private String naptanId;
        private String commonName;
        private double distance;

        public String getNaptanId() {
            return naptanId;
        }

        public String getCommonName() {
            return commonName;
        }

        public double getDistance() {
            return distance;
        }
    }
}
