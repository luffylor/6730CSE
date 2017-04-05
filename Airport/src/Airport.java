//YOUR NAME HERE
import java.util.PriorityQueue;
import java.util.Random;

public class Airport implements EventHandler {

    //TODO add landing and takeoff queues, random variables
    //For each airport, keep stats on the number of passengers arriving and departing. 
    //Also sum the total amount circling time for each airport.  This is time where an airplane is ready to land but is waiting for an opening
    //Develop a queuing system to ensure planes are not arriving and taking off at the same time on a single runway.
    private int m_inTheAir;
    private int m_onTheGround;

    private boolean m_freeToLand;


    private double m_flightTime;
    private double m_runwayTimeToLand;
    private double m_requiredTimeOnGround;
    private double m_runwayTimeToTakeOff;


    private String m_airportName;

    //add number of passengers arriving and departing
    private int m_arrivingPassengers;
    private int m_departingPassengers;

    //add circling time
    private double m_circlingTime;

    //add priorityqueue
    private PriorityQueue<AirportEvent> arrivingQueue = new PriorityQueue<AirportEvent>(100);
    private PriorityQueue<AirportEvent> departureQueue = new PriorityQueue<AirportEvent>(100);

    public Airport(String name, double runwayTimeToLand, double requiredTimeOnGround, double runwayTimeToTakeOff) {
        m_airportName = name;
        m_inTheAir =  0;
        m_onTheGround = 0;
        m_freeToLand = true;
        m_runwayTimeToLand = runwayTimeToLand;
        m_requiredTimeOnGround = requiredTimeOnGround;
        m_runwayTimeToTakeOff = runwayTimeToTakeOff;
        m_flightTime = 0;
        m_arrivingPassengers = 0;
        m_departingPassengers = 0;
        m_circlingTime = 0;
    }

    public String getAirportName() {
        return m_airportName;
    }
    public int getArrivingPassenger() {return m_arrivingPassengers; }
    public int getDepartingPassenger() {return m_departingPassengers; }
    public double getCirclingTime() {return m_circlingTime; }

    //distance between two airports
    public double calDistance(String airport1, String airport2){
        int a1 = 0, a2 = 0;
        switch(airport1){
            case "LAX":
                a1 = 0;
                break;
            case "ATL":
                a1 = 1;
                break;
            case "NYC":
                a1 = 2;
                break;
            case "IAH":
                a1 = 3;
                break;
            case "CHI":
                a1 = 4;
                break;
        }
        switch(airport2){
            case "LAX":
                a2 = 0;
                break;
            case "ATL":
                a2 = 1;
                break;
            case "NYC":
                a2 = 2;
                break;
            case "IAH":
                a2 = 3;
                break;
            case "CHI":
                a2 = 4;
                break;
        }
        //constrcut ditance matrix(miles)
        double [] laxarrays = {0, 1946, 2451, 1379, 1745};
        double [] atlarrays = {1946, 0, 746, 689, 588};
        double [] nycarrays = {2451, 746, 0, 1416, 713};
        double [] iaharrays = {1379, 689, 1416, 0, 925};
        double [] chiarrays = {1745, 588, 713, 925, 0};
        double [][] matrix = {laxarrays, atlarrays, nycarrays, iaharrays, chiarrays};

        return matrix[a1][a2];
    }

    public void handle(Event event) {
        AirportEvent airEvent = (AirportEvent)event;

        switch(airEvent.getType()) {
            case AirportEvent.PLANE_ARRIVES:
                m_inTheAir++;
                arrivingQueue.add(airEvent);
                //record arriving time
                airEvent.setArrivingTime(Simulator.getCurrentTime());
                System.out.println(Simulator.getCurrentTime() + ": " + airEvent.getAirplane().getName() + " arrived at " + this.getAirportName());
                if(m_freeToLand) {
                    m_freeToLand = false;
                    AirportEvent firstArrivingEvent = arrivingQueue.poll();
                    System.out.println(Simulator.getCurrentTime() + ": " + airEvent.getAirplane().getName() + " start landing " + this.getAirportName());
                    //record circling time for a plane
                    firstArrivingEvent.setArrivedTime(Simulator.getCurrentTime());
                    m_circlingTime += firstArrivingEvent.getWaitTime();

                    //record arriving passengers
                    m_arrivingPassengers += firstArrivingEvent.getAirplane().getNumberPassengers();

                    AirportEvent landedEvent = new AirportEvent(firstArrivingEvent.getAirplane(), m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED);
                    Simulator.schedule(landedEvent);
                }
                break;

            case AirportEvent.PLANE_TAKEOFF:
                departureQueue.add(airEvent);
                if(m_freeToLand) {
                    m_freeToLand = false;
                    AirportEvent firstDepartureEvent = departureQueue.poll();
                    System.out.println(Simulator.getCurrentTime() + ": " + firstDepartureEvent.getAirplane().getName() + " takeoff from " + this.getAirportName());
                    AirportEvent takeoffEvent = new AirportEvent(firstDepartureEvent.getAirplane(), m_runwayTimeToTakeOff, this, AirportEvent.PLANE_DEPARTS);
                    Simulator.schedule(takeoffEvent);
                }
                break;

            //For each plane departure, select a remote airport and use some sort of random distribution to calculate the number of passengers on the flight.
            case AirportEvent.PLANE_DEPARTS:
                m_onTheGround--;
                System.out.println(Simulator.getCurrentTime() + ": " + airEvent.getAirplane().getName() + " departs from " + this.getAirportName());
                //set random passengers
                airEvent.getAirplane().setNumberPassengers();
                //record departing passengers
                m_departingPassengers += airEvent.getAirplane().getNumberPassengers();

                //plane arrives in another airport
                //calculate flight time
                String airport1 = this.getAirportName();
                //select a random airport for destination
                Random rand = new Random();
                int index = rand.nextInt(AirportSim.airports.size());
                while(AirportSim.airports.get(index) == this){
                    index = rand.nextInt(AirportSim.airports.size());
                }
                Airport destination = AirportSim.airports.get(index);
                String airport2 = destination.getAirportName();
                double distance = calDistance(airport1, airport2);
                double speed = airEvent.getAirplane().getSpeed();
                m_flightTime = distance / speed;
                AirportEvent departEvent = new AirportEvent(airEvent.getAirplane(), m_flightTime, destination, AirportEvent.PLANE_ARRIVES);
                Simulator.schedule(departEvent);
                if(departureQueue.size() != 0){
                    AirportEvent firstDepartureEvent = departureQueue.poll();
                    System.out.println(Simulator.getCurrentTime() + ": " + firstDepartureEvent.getAirplane().getName() + " takeoff from " + this.getAirportName());
                    AirportEvent takeoffEvent = new AirportEvent(firstDepartureEvent.getAirplane(), m_runwayTimeToTakeOff, this, AirportEvent.PLANE_DEPARTS);
                    Simulator.schedule(takeoffEvent);
                }else if(arrivingQueue.size() != 0){
                    AirportEvent firstArrivingEvent = arrivingQueue.poll();
                    System.out.println(Simulator.getCurrentTime() + ": " + firstArrivingEvent.getAirplane().getName() + " start landing " + this.getAirportName());
                    //record arriving passengers
                    m_arrivingPassengers += firstArrivingEvent.getAirplane().getNumberPassengers();
                    //record circling time for a plane
                    firstArrivingEvent.setArrivedTime(Simulator.getCurrentTime());
                    m_circlingTime += firstArrivingEvent.getWaitTime();

                    AirportEvent landingEvent = new AirportEvent(firstArrivingEvent.getAirplane(), m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED);
                    Simulator.schedule(landingEvent);
                }else{
                    m_freeToLand = true;
                }
                break;


            case AirportEvent.PLANE_LANDED:
                m_inTheAir--;
                m_onTheGround++;
                System.out.println(Simulator.getCurrentTime() + ": " + airEvent.getAirplane().getName() + " lands at " + this.getAirportName());
                AirportEvent departureEvent = new AirportEvent(airEvent.getAirplane(), m_requiredTimeOnGround, this, AirportEvent.PLANE_TAKEOFF);
                Simulator.schedule(departureEvent);
                if(arrivingQueue.size() != 0)
                {
                    AirportEvent firstArrivingEvent = arrivingQueue.poll();
                    System.out.println(Simulator.getCurrentTime() + ": " + firstArrivingEvent.getAirplane().getName() + " start landing " + this.getAirportName());
                    //record arriving passengers
                    m_arrivingPassengers += firstArrivingEvent.getAirplane().getNumberPassengers();
                    //record circling time for a plane
                    firstArrivingEvent.setArrivedTime(Simulator.getCurrentTime());
                    m_circlingTime += firstArrivingEvent.getWaitTime();

                    AirportEvent landingEvent = new AirportEvent(firstArrivingEvent.getAirplane(), m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED);
                    Simulator.schedule(landingEvent);
                }else if(departureQueue.size() != 0){
                    AirportEvent firstDepartureEvent = departureQueue.poll();
                    System.out.println(Simulator.getCurrentTime() + ": " + firstDepartureEvent.getAirplane().getName() + " takeoff from " + this.getAirportName());
                    AirportEvent takeoffEvent = new AirportEvent(firstDepartureEvent.getAirplane(), m_runwayTimeToTakeOff, this, AirportEvent.PLANE_DEPARTS);
                    Simulator.schedule(takeoffEvent);
                }
                else
                {
                    m_freeToLand = true;
                }
                break;
        }
    }
}
