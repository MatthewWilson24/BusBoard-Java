package training.busboard;

import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

    public static void main(String args[]) {

        System.out.println("Enter Postcode: ");

        Scanner scanner = new Scanner(System.in);
        String postcode = scanner.nextLine();

        try {
            printAllBusInfo(getStops(getCoordinates(postcode)));
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private static PostcodeResult.Coordinates getCoordinates(String postcode) {
        String query = String.format("https://api.postcodes.io/postcodes/%s", postcode);

        PostcodeResult result = client.target(query)
                .request(MediaType.APPLICATION_JSON)
                .get(PostcodeResult.class);

        return result.getCoordinates();
    }

    private static List<BusStopInfo.StopPoint> getStops(PostcodeResult.Coordinates coordinates) {
        String query = String.format("https://api.tfl.gov.uk/StopPoint?stopTypes=NaptanPublicBusCoachTram&lat=%f&lon=%f",
                coordinates.getLatitude(), coordinates.getLongitude());

        BusStopInfo busStopInfo = client.target(query)
                .request(MediaType.APPLICATION_JSON)
                .get(BusStopInfo.class);

        return busStopInfo.getStopPoints()
                .stream()
                .sorted(Comparator.comparingDouble(BusStopInfo.StopPoint::getDistance))
                .limit(2)
                .collect(Collectors.toList());
    }

    private static void printAllBusInfo(List<BusStopInfo.StopPoint> list) {
        list.forEach(Main::printBusInfo);
    }

    private static void printBusInfo(BusStopInfo.StopPoint stop) {
        System.out.println(String.format("Bus Stop: %s, Distance: %.0f metres", stop.getCommonName(), stop.getDistance()));

        String query = String.format("https://api.tfl.gov.uk/StopPoint/%s/Arrivals", stop.getNaptanId());

        List<BusInfo> infos = client.target(query)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<BusInfo>>() {});

        infos.stream()
                .sorted(Comparator.comparingInt(BusInfo::getTimeToStation))
                .limit(5)
                .forEachOrdered(info -> System.out.println(info));

        System.out.println();
    }
}	
