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
    public static Airplane plane1 = new Airplane("plane1", 506, 640, 1000, 2, 1);//A380
    public static Airplane plane2 = new Airplane("plane2", 147, 583, 1000,2,1);//Boeing 737
    public static Airplane plane3 = new Airplane("plane3", 314, 562,1000, 2, 1);//Boeing 777
    public static Airplane plane4 = new Airplane("plane4", 277, 541,1000, 2, 1);//A330
    public static Airplane plane5 = new Airplane("plane5", 290, 567,1000, 2, 1);//Boeing 787
    public static Airplane plane6 = new Airplane("plane6", 506, 640,1000, 2, 1);//A380
    public static Airplane plane7 = new Airplane("plane7", 147, 583,1000, 2, 1);//Boeing 737
    public static Airplane plane8 = new Airplane("plane8", 314, 562,1000, 2, 1);//Boeing 777
    public static Airplane plane9 = new Airplane("plane9", 277, 541,1000, 2, 1);//A330
    public static Airplane plane10 = new Airplane("plane10", 290, 567,1000, 2, 1);//Boeing 787


    public static ArrayList<Airplane> airplanes = new ArrayList<Airplane>(Arrays.asList(plane1, plane2, plane3, plane4, plane5, plane6, plane7, plane8, plane9, plane10));
    public static ArrayList<Airport> airports = new ArrayList<Airport>(Arrays.asList(lax, atl, nyc, iah, chi));


    public static void main(String[] args) {
        Random rand = new Random();
        /*for (int i = 0; i < 10; i++) {
            int  n1 = rand.nextInt(airplanes.size());
            int  n2 = rand.nextInt(airports.size());
            Airplane tempAirplane = airplanes.get(n1);
            AirportEvent tempEvent = new AirportEvent(tempAirplane, 5, airports.get(n2), AirportEvent.PLANE_ARRIVES,3);
            airplanes.add(tempAirplane);
            Simulator.schedule(tempEvent);
        }*/

        AirportEvent landingEvent = new AirportEvent(plane1, 5, lax, AirportEvent.PLANE_ARRIVES, 3);
        Simulator.schedule(landingEvent);

        AirportEvent landingEvent2 = new AirportEvent(plane2, 5, lax, AirportEvent.PLANE_ARRIVES, 3);
        Simulator.schedule(landingEvent2);

        AirportEvent landingEvent3 = new AirportEvent(plane3, 5, lax, AirportEvent.PLANE_ARRIVES, 3);
        Simulator.schedule(landingEvent3);

        AirportEvent landingEvent4 = new AirportEvent(plane4, 5, lax, AirportEvent.PLANE_ARRIVES, 3);
        Simulator.schedule(landingEvent4);

        AirportEvent landingEvent5 = new AirportEvent(plane5, 5, lax, AirportEvent.PLANE_ARRIVES, 3);
        Simulator.schedule(landingEvent5);



        Simulator.stopAt(1000);
        Simulator.run();

        //weather-----------------------------------------------------
        //***************************************today*********************************************
        System.out.println("Airport LAX traveling gas consumed: " + lax.gasConsumedTraveling);
        System.out.println("Airport LAX circuling gas consumed: " + lax.gasConsumedCirculing);

        System.out.println("Airport ATL traveling gas consumed: " + atl.gasConsumedTraveling);
        System.out.println("Airport ATL circuling gas consumed: " + atl.gasConsumedCirculing);

        System.out.println("Airport NYC traveling gas consumed: " + nyc.gasConsumedTraveling);
        System.out.println("Airport NYC circuling gas consumed: " + nyc.gasConsumedCirculing);

        System.out.println("Airport IAN traveling gas consumed: " + iah.gasConsumedTraveling);
        System.out.println("Airport IAN circuling gas consumed: " + iah.gasConsumedCirculing);

        System.out.println("Airport CHI traveling gas consumed: " + chi.gasConsumedTraveling);
        System.out.println("Airport CHI circuling gas consumed: " + chi.gasConsumedCirculing);
        //weather-----------------------------------------------------
        //***************************************today*********************************************

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
