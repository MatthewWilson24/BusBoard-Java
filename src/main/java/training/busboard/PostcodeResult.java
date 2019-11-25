package training.busboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostcodeResult {

    @XmlElement(name = "result")
    private Coordinates coordinates;

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coordinates {
        private double longitude;
        private double latitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
