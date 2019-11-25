package training.busboard;

import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {

        System.out.println("Enter Bus Stop ID: ");

        Scanner scanner = new Scanner(System.in);
        String code = "490008660N";  // scanner.nextLine();


        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
        try {
            List<BusInfo> infos = client.target("https://api.tfl.gov.uk/StopPoint/" + code + "/Arrivals")
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<BusInfo>>() {});

            infos.stream()
                    .sorted(Comparator.comparingInt(BusInfo::getTimeToStation))
                    .limit(5)
                    .forEachOrdered(info -> System.out.println(info));


            PostcodesApiResult postcode = client.target("https://api.postcodes.io/postcodes/NW51TL")
                    .request(MediaType.APPLICATION_JSON)
                    .get(PostcodesApiResult.class);

            System.out.println(postcode.getResult().getLatitude());

            String nearestStopQuery = String.format("https://api.tfl.gov.uk/StopPoint?stopTypes=NaptanPublicBusCoachTram&lat=%f&lon=%f",
                    postcode.getResult().getLatitude(), postcode.getResult().getLongitude());
            BusStopInfo busStopInfo = client.target(nearestStopQuery)
                    .request(MediaType.APPLICATION_JSON)
                    .get(BusStopInfo.class);

            busStopInfo.getStopPoints().stream()
                    .forEachOrdered(info -> System.out.println(info.getDistance()));

        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}	
