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

    private boolean m_land1, m_land2, m_takeoff1;
    //weather-----------------------------------------------------
    //***************************************today*********************************************
    private boolean weather = true;
    private boolean[] weatherArray = new boolean[]{false, false, false, false,false, true,false, true, false, false};
    private double duration = 10.0;
    private double oldWeatherTime = 0.0;
    //weather-----------------------------------------------------
    //***************************************today*********************************************

    private double m_flightTime;
    private double m_runwayTimeToLand;
    private double m_requiredTimeOnGround;
    private double m_runwayTimeToTakeOff;

    //gas-----------------------------------------------------
    //***************************************today*********************************************
    public double gasConsumedTraveling = 0;
    public double gasConsumedCirculing = 0;
    //gas-----------------------------------------------------
    //***************************************today*********************************************

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
        m_land1 = true;
        m_land2 = true;
        m_takeoff1 = true;
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
// If condition to check weather true of false;
        switch(airEvent.getType()) {
            case AirportEvent.PLANE_ARRIVES:
                //weather if condition--------------------------------
                m_inTheAir++;

                //record arriving time
                airEvent.setArrivingTime(Simulator.getCurrentTime());
                System.out.println(Simulator.getCurrentTime() + ": " + airEvent.getAirplane().getName() + " arrived at " + this.getAirportName());
                //***************************************today*********************************************
                //weather-----------------------------------------------------
                if ((Simulator.getCurrentTime() - oldWeatherTime) >= duration) {
                    int rnd = new Random().nextInt(weatherArray.length);
                    weather = weatherArray[rnd];
                    duration = 10 - (Simulator.getCurrentTime() - (oldWeatherTime + duration)) % 10;
                    oldWeatherTime = Simulator.getCurrentTime();
                }
                if (!weather) {
                    AirportEvent temp = new AirportEvent(airEvent.getAirplane(), 1, this, AirportEvent.PLANE_ARRIVES,3);
                    Simulator.schedule(temp);

                }else {
                    arrivingQueue.add(airEvent);
                    if(m_land1) {
                        m_land1 = false;
                        AirportEvent firstArrivingEvent1 = arrivingQueue.poll();
                        System.out.println(Simulator.getCurrentTime() + ": " + airEvent.getAirplane().getName() + " start landing at the first landing runway of " + this.getAirportName());
                        //record circling time for a plane
                        firstArrivingEvent1.setArrivedTime(Simulator.getCurrentTime());
                        m_circlingTime += firstArrivingEvent1.getWaitTime();


                        //record arriving passengers
                        m_arrivingPassengers += firstArrivingEvent1.getAirplane().getNumberPassengers();

                        AirportEvent landedEvent1 = new AirportEvent(firstArrivingEvent1.getAirplane(), m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED, 0);
                        Simulator.schedule(landedEvent1);
                    }

                    else if(m_land2){
                        m_land2 = false;
                        AirportEvent firstArrivingEvent2 = arrivingQueue.poll();
                        System.out.println(Simulator.getCurrentTime() + ": " + airEvent.getAirplane().getName() + " start landing at the second landing runway of " + this.getAirportName());
                        //record circling time for a plane
                        firstArrivingEvent2.setArrivedTime(Simulator.getCurrentTime());
                        m_circlingTime += firstArrivingEvent2.getWaitTime();

                        //record arriving passengers
                        m_arrivingPassengers += firstArrivingEvent2.getAirplane().getNumberPassengers();

                        AirportEvent landedEvent2 = new AirportEvent(firstArrivingEvent2.getAirplane(), m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED, 1);
                        Simulator.schedule(landedEvent2);

                    }
                }
                //weather-----------------------------------------------------
                //***************************************today*********************************************
                break;

            case AirportEvent.PLANE_TAKEOFF:
                //***************************************today*********************************************
                //weather-----------------------------------------------------
                if ((Simulator.getCurrentTime() - oldWeatherTime) >= duration) {
                    int rnd = new Random().nextInt(weatherArray.length);
                    weather = weatherArray[rnd];
                    duration = 10 - (Simulator.getCurrentTime() - (oldWeatherTime + duration)) % 10;
                    oldWeatherTime = Simulator.getCurrentTime();
                }
                if (!weather) {
                    AirportEvent temp = new AirportEvent(airEvent.getAirplane(), 1, this, AirportEvent.PLANE_DEPARTS,3);
                    Simulator.schedule(temp);

                }else {
                    departureQueue.add(airEvent);
                    if(m_takeoff1) {
                        m_takeoff1 = false;
                        AirportEvent firstDepartureEvent = departureQueue.poll();
                        System.out.println(Simulator.getCurrentTime() + ": " + firstDepartureEvent.getAirplane().getName() + " takeoff from the first takeoff runway of" + this.getAirportName());
                        AirportEvent takeoffEvent = new AirportEvent(firstDepartureEvent.getAirplane(), m_runwayTimeToTakeOff, this, AirportEvent.PLANE_DEPARTS,2);
                        Simulator.schedule(takeoffEvent);
                    }
                }
                break;
            //weather-----------------------------------------------------
            //***************************************today*********************************************

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
                Airplane temp = airEvent.getAirplane();

                //***************************************today*********************************************
                // gas1-----------------------------------------------------
                gasConsumedTraveling += temp.calGas(m_flightTime, temp.gasSpeedTraveling);
                //----------------------------------------------------------
                //***************************************today*********************************************

                AirportEvent departEvent = new AirportEvent(temp, m_flightTime, destination, AirportEvent.PLANE_ARRIVES,3);
                Simulator.schedule(departEvent);
                if(departureQueue.size() != 0){
                    AirportEvent firstDepartureEvent = departureQueue.poll();
                    System.out.println(Simulator.getCurrentTime() + ": " + firstDepartureEvent.getAirplane().getName() + " takeoff from the first takeoff runway of " + this.getAirportName());
                    AirportEvent takeoffEvent = new AirportEvent(firstDepartureEvent.getAirplane(), m_runwayTimeToTakeOff, this, AirportEvent.PLANE_DEPARTS,2);
                    Simulator.schedule(takeoffEvent);
                }else{
                    m_takeoff1 = true;
                }
                break;


            case AirportEvent.PLANE_LANDED:
                m_inTheAir--;
                m_onTheGround++;
                System.out.println(Simulator.getCurrentTime() + ": " + airEvent.getAirplane().getName() + " lands at " + this.getAirportName());
                AirportEvent departureEvent = new AirportEvent(airEvent.getAirplane(), m_requiredTimeOnGround, this, AirportEvent.PLANE_TAKEOFF,2);
                Simulator.schedule(departureEvent);
                int runway = airEvent.getRunway();
                if (runway == 0) m_land1  = true;
                if (runway == 1) m_land2 = true;
                if(arrivingQueue.size() != 0)
                {
                    if(m_land1) {
                        m_land1 = false;
                        AirportEvent firstArrivingEvent1 = arrivingQueue.poll();
                        System.out.println(Simulator.getCurrentTime() + ": " + firstArrivingEvent1.getAirplane().getName() + " start landing at the first landing runway of " + this.getAirportName());
                        //record circling time for a plane
                        firstArrivingEvent1.setArrivedTime(Simulator.getCurrentTime());
                        m_circlingTime += firstArrivingEvent1.getWaitTime();

                        //***************************************today*********************************************
                        // gas2----------------------------------
                        Airplane temp1 = airEvent.getAirplane();

                        gasConsumedCirculing += temp1.calGas(firstArrivingEvent1.getWaitTime(), temp1.gasSpeedCirculing);
                        System.out.println("airport" + this.getAirportName() + "circuling time1" + m_circlingTime);
                        System.out.println("airport" + this.getAirportName() + "circuling gas consume" + gasConsumedCirculing);
                        //---------------------------------------
                        //***************************************today*********************************************

                        //record arriving passengers
                        m_arrivingPassengers += firstArrivingEvent1.getAirplane().getNumberPassengers();

                        AirportEvent landedEvent1 = new AirportEvent(firstArrivingEvent1.getAirplane(), m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED,0);
                        Simulator.schedule(landedEvent1);
                    }
                    else if(m_land2){
                        m_land2 = false;
                        AirportEvent firstArrivingEvent2 = arrivingQueue.poll();
                        System.out.println(Simulator.getCurrentTime() + ": " + firstArrivingEvent2.getAirplane().getName() + " start landing at the second landing runway of " + this.getAirportName());
                        //record circling time for a plane
                        firstArrivingEvent2.setArrivedTime(Simulator.getCurrentTime());
                        m_circlingTime += firstArrivingEvent2.getWaitTime();
                        //***************************************today*********************************************
                        // gas2----------------------------------
                        Airplane temp2 = airEvent.getAirplane();

                        gasConsumedCirculing += temp2.calGas(firstArrivingEvent2.getWaitTime(), temp2.gasSpeedCirculing);
                        System.out.println("airport" + this.getAirportName() + "circuling time2" + m_circlingTime);
                        System.out.println("airport" + this.getAirportName() + "circuling gas consume" + gasConsumedCirculing);
                        //---------------------------------------
                        //***************************************today*********************************************

                        //record arriving passengers
                        m_arrivingPassengers += firstArrivingEvent2.getAirplane().getNumberPassengers();

                        AirportEvent landedEvent2 = new AirportEvent(firstArrivingEvent2.getAirplane(), m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED,1);
                        Simulator.schedule(landedEvent2);

                    }
                }
// Random again the new weather;
                break;
        }
    }
}
