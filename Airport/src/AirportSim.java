//YOUR NAME HERE

import java.util.Arrays;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Random;

//Pick 5 airports in the world and add them to the simulator.  You'll need the distance between them to calculate flight time for each flight.
//Adjust the simulator to run for an appropriate period and adjust timings (timeToLand, timeOnGround etc) accordingly
public class AirportSim {
    public static Airport lax = new Airport("LAX", 0.5, 2, 0.3);
    //add 4 airports: Atlanta, NewYork, Houston, Chicago
    public static Airport atl = new Airport("ATL", 0.5, 2, 0.3);
    public static Airport nyc = new Airport("NYC", 0.5, 2, 0.3);
    public static Airport iah = new Airport("IAH", 0.5, 2, 0.3);
    public static Airport chi = new Airport("CHI", 0.5, 2, 0.3);

    //add airplane
    public static Airplane plane1 = new Airplane("plane1", 506, 640);//A380
    public static Airplane plane2 = new Airplane("plane2", 147, 583);//Boeing 737
    public static Airplane plane3 = new Airplane("plane3", 314, 562);//Boeing 777
    public static Airplane plane4 = new Airplane("plane4", 277, 541);//A330
    public static Airplane plane5 = new Airplane("plane5", 290, 567);//Boeing 787
    public static Airplane plane6 = new Airplane("plane6", 506, 640);//A380
    public static Airplane plane7 = new Airplane("plane7", 147, 583);//Boeing 737
    public static Airplane plane8 = new Airplane("plane8", 314, 562);//Boeing 777
    public static Airplane plane9 = new Airplane("plane9", 277, 541);//A330
    public static Airplane plane10 = new Airplane("plane10", 290, 567);//Boeing 787


    public static ArrayList<Airplane> airplanes = new ArrayList<Airplane>(Arrays.asList(plane1, plane2, plane3, plane4, plane5, plane6, plane7, plane8, plane9, plane10));
    public static ArrayList<Airport> airports = new ArrayList<Airport>(Arrays.asList(lax, atl, nyc, iah, chi));


    public static void main(String[] args) {
        Random rand = new Random();
        for (int i = 0; i < 400; i++) {
            int  n1 = rand.nextInt(airplanes.size());
            int  n2 = rand.nextInt(airports.size());
            Airplane tempAirplane = airplanes.get(n1);
            AirportEvent tempEvent = new AirportEvent(tempAirplane, 5, airports.get(n2), AirportEvent.PLANE_ARRIVES);
            airplanes.add(tempAirplane);
            Simulator.schedule(tempEvent);
        }


        //add airport events
        /*AirportEvent landingEvent = new AirportEvent(plane1, 5, lax, atl, AirportEvent.PLANE_ARRIVES);
        Simulator.schedule(landingEvent);

        AirportEvent landingEvent2 = new AirportEvent(plane2, 5, lax, nyc , AirportEvent.PLANE_ARRIVES);
        Simulator.schedule(landingEvent2);

        AirportEvent landingEvent3 = new AirportEvent(plane3, 5, atl, iah , AirportEvent.PLANE_ARRIVES);
        Simulator.schedule(landingEvent3);

        AirportEvent landingEvent4 = new AirportEvent(plane4, 5, chi, nyc , AirportEvent.PLANE_ARRIVES);
        Simulator.schedule(landingEvent4);

        AirportEvent landingEvent5 = new AirportEvent(plane5, 5, chi, iah , AirportEvent.PLANE_ARRIVES);
        Simulator.schedule(landingEvent5);

        AirportEvent landingEvent6 = new AirportEvent(plane6, 9, iah, atl, AirportEvent.PLANE_ARRIVES);
        Simulator.schedule(landingEvent6);

        AirportEvent landingEvent7 = new AirportEvent(plane7, 8, iah, nyc, AirportEvent.PLANE_ARRIVES);
        Simulator.schedule(landingEvent7);

        AirportEvent landingEvent8 = new AirportEvent(plane8, 10, atl, chi, AirportEvent.PLANE_ARRIVES);
        Simulator.schedule(landingEvent8);

        AirportEvent landingEvent9 = new AirportEvent(plane9, 10, atl, atl, AirportEvent.PLANE_ARRIVES);
        Simulator.schedule(landingEvent9);

        AirportEvent landingEvent10 = new AirportEvent(plane10, 5, nyc, atl, AirportEvent.PLANE_ARRIVES);
        Simulator.schedule(landingEvent10);*/


        Simulator.stopAt(1000);
        Simulator.run();

        System.out.println("Airport LAX arriving passengers: " + lax.getArrivingPassenger());
        System.out.println("Airport LAX departing passengers: " + lax.getDepartingPassenger());
        System.out.println("Airport LAX total circling time: " + lax.getCirclingTime());

        System.out.println("Airport ATL arriving passengers: " + atl.getArrivingPassenger());
        System.out.println("Airport ATL departing passengers: " + atl.getDepartingPassenger());
        System.out.println("Airport ATL total circling time: " + atl.getCirclingTime());

        System.out.println("Airport NYC arriving passengers: " + nyc.getArrivingPassenger());
        System.out.println("Airport NYC departing passengers: " + nyc.getDepartingPassenger());
        System.out.println("Airport NYC total circling time: " + nyc.getCirclingTime());

        System.out.println("Airport IAH arriving passengers: " + iah.getArrivingPassenger());
        System.out.println("Airport IAH departing passengers: " + iah.getDepartingPassenger());
        System.out.println("Airport IAH total circling time: " + iah.getCirclingTime());

        System.out.println("Airport CHI arriving passengers: " + chi.getArrivingPassenger());
        System.out.println("Airport CHI departing passengers: " + chi.getDepartingPassenger());
        System.out.println("Airport CHI total circling time: " + chi.getCirclingTime());

        int sum = lax.getArrivingPassenger() + atl.getArrivingPassenger() + nyc.getArrivingPassenger() + iah.getArrivingPassenger() + chi.getArrivingPassenger();
        System.out.println("Total passenger number: " + sum);
    }
}
