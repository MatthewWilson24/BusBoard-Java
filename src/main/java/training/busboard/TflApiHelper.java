package training.busboard;

import org.glassfish.jersey.jackson.JacksonFeature;
import training.busboard.web.BusDisplay;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TflApiHelper {
    private Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

    public List<BusDisplay.StopDisplay> getBusStopDisplays(String postcode) {
        try {
            List<StopInfo.StopPoint> list = getStops(getCoordinates(postcode));

            return list.stream()
                    .map(this::getBusStopDisplay)
                    .collect(Collectors.toList());

        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }

    private PostcodeResult.Coordinates getCoordinates(String postcode) {
        String query = String.format("https://api.postcodes.io/postcodes/%s", postcode);

        PostcodeResult result = client.target(query)
                .request(MediaType.APPLICATION_JSON)
                .get(PostcodeResult.class);

        return result.getCoordinates();
    }

    private List<StopInfo.StopPoint> getStops(PostcodeResult.Coordinates coordinates) {
        String query = String.format("https://api.tfl.gov.uk/StopPoint?stopTypes=NaptanPublicBusCoachTram&lat=%f&lon=%f",
                coordinates.getLatitude(), coordinates.getLongitude());

        StopInfo stopInfo = client.target(query)
                .request(MediaType.APPLICATION_JSON)
                .get(StopInfo.class);

        return stopInfo.getStopPoints()
                .stream()
                .sorted(Comparator.comparingDouble(StopInfo.StopPoint::getDistance))
                .limit(2)
                .collect(Collectors.toList());
    }

    private BusDisplay.StopDisplay getBusStopDisplay(StopInfo.StopPoint stop) {
        String query = String.format("https://api.tfl.gov.uk/StopPoint/%s/Arrivals", stop.getNaptanId());

        List<BusInfo> infos = client.target(query)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<BusInfo>>() {});

        infos = infos.stream()
                .sorted(Comparator.comparingInt(BusInfo::getTimeToStation))
                .limit(5)
                .collect(Collectors.toList());

        return new BusDisplay.StopDisplay(stop.getCommonName(), stop.getDistance(), infos);
    }

}
