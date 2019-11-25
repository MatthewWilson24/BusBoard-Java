package training.busboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusInfo {
    private String lineId;
    private String destinationName;
    private int timeToStation;

    public String getLineId() {
        return lineId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public int getTimeToStation() {
        return timeToStation;
    }

    private String getMinsSecs() {
        return String.format("%d mins %d secs", timeToStation / 60, timeToStation % 60);
    }

    @Override
    public String toString() {
        return String.format("Number: %s, Destination: %s, Time: %s", lineId, destinationName, getMinsSecs());
    }
}
