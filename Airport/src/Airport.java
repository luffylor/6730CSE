//Xiao Han

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.PriorityQueue;
import java.util.Random;

public class Airport implements EventHandler {

    //TODO add landing and takeoff queues, random variables
    //landing queues
    PriorityQueue<AirportEvent> landingQueue = new PriorityQueue<AirportEvent>();


    //takeoff queues
    PriorityQueue<AirportEvent> takeoffQueue = new PriorityQueue<AirportEvent>();

    private int m_inTheAir;
    private int m_onTheGround;
    private boolean m_freeToLand;
    private double m_flightTime;
    private double m_runwayTimeToLand;
    private double m_requiredTimeOnGround;
    private double m_takeofftime;
    private int receivedPassenger;
    private int sentPassenger;
    //private int travelPassenger;
    private int passAirplane = 0;
    private double circlingTime;
    private double landStartTime;
    private String m_airportName;
    private int airportID;
    private int[][] distance = {{0, 1399, 2475, 1937, 1745},
            {1399, 0, 2421, 2182, 1721},
            {2475, 2421, 0, 761, 733},
            {1937, 2182, 761, 0, 606},
            {1745, 1721, 733, 606, 0}};

    public Airport(String name, double runwayTimeToLand, double requiredTimeOnGround, double flightTime, double takeofftime, int id) {
        m_airportName = name;
        m_inTheAir = 0;
        m_onTheGround = 0;
        m_freeToLand = true;
        m_runwayTimeToLand = runwayTimeToLand;
        m_flightTime = flightTime;
        m_takeofftime = takeofftime;
        airportID = id;
        this.m_requiredTimeOnGround = requiredTimeOnGround;//????
    }

    //Constructors
    public void setM_runwayTimeToLand(double m_runwayTimeToLand) {
        this.m_runwayTimeToLand = m_runwayTimeToLand;
    }

    public void setM_requiredTimeOnGround(double m_requiredTimeOnGround) {
        this.m_requiredTimeOnGround = m_requiredTimeOnGround;
    }

//    public Queue<AirportEvent> getLandingQueue() {
//        return landingQueue;
//    }
//
//    public Queue<AirportEvent> getTakeoffQueue() {
//        return takeoffQueue;
//    }

    public double getcirclingTime() {
        return circlingTime;
    }

    public int getreceivedPassenger() {
        return receivedPassenger;
    }

    public int getsentPassenger() {
        return sentPassenger;
    }

//    public int getTravelPassenger() {
//        return travelPassenger;
//    }

    public String getName() {
        return m_airportName;
    }

    public int getpassAirplane() {
        return passAirplane;
    }

    public static String formatDouble(double d) {
        DecimalFormat df = new DecimalFormat("#.000");
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format(d);
    }

    public void setpassAirplane(int passAirplane) {
        this.receivedPassenger = passAirplane;
    }

    public void setreceivedPassenger(int receivedPassenger) {
        this.receivedPassenger = receivedPassenger;
    }

    public void setFlyawaypassenger_number(int flyawaypassenger_number) {
        this.sentPassenger = flyawaypassenger_number;
    }

    public void setcirclingTime(double circlingTime) {
        this.circlingTime = circlingTime;
    }

    public void handle(Event event) {

        AirportEvent airEvent = (AirportEvent) event; //向上转类???
        Airplane tempAirplane = airEvent.getcurrentAirplane();
        Airport port_to = airEvent.getdestinationAirport();
        Airport[] list = AirportsList.airports;

        switch (airEvent.getType()) {
            case AirportEvent.PLANE_ARRIVES:
                m_inTheAir++;
                passAirplane++;
                System.out.println(formatDouble(Simulator.getCurrentTime()) + ": "+ tempAirplane.getName()+" arrived at airport: " + this.getName());
                this.landingQueue.add(airEvent);
                if (m_freeToLand) {
                    AirportEvent landedEvent = new AirportEvent(m_runwayTimeToLand, airEvent.getHandler(),
                            AirportEvent.PLANE_LANDED, airEvent.getdestinationAirport(), tempAirplane);
                    Simulator.schedule(landedEvent);
                    m_freeToLand = false;
                }
                this.landStartTime = airEvent.getTime();
                tempAirplane.setStartLandTime(landStartTime);
                break;

            case AirportEvent.PLANE_LANDED:
                m_inTheAir--;
                this.landingQueue.remove();
                System.out.println(formatDouble(Simulator.getCurrentTime()) + ": "+ tempAirplane.getName()+ " lands at airport: "+this.getName());
                AirportEvent takeoffEvent = new AirportEvent(m_requiredTimeOnGround, airEvent.getHandler(), AirportEvent.PLANE_TAKEOFF, port_to, tempAirplane);
                Simulator.schedule(takeoffEvent);
                this.circlingTime = this.circlingTime + airEvent.getTime() - tempAirplane.getStartLandTime() - m_runwayTimeToLand;
                tempAirplane.setStartLandTime(0);
                this.receivedPassenger += airEvent.getcurrentAirplane().getM_numberPassengers();
                int tempPassengerNumber = new Random().nextInt(tempAirplane.getCapacity() - 99) + 100;
                airEvent.getcurrentAirplane().setM_numberPassengers(tempPassengerNumber);
                if ((m_onTheGround > 0) && (m_inTheAir > 0)) {
                    AirportEvent fly = takeoffQueue.element();
                    AirportEvent land = landingQueue.element();
                    if (fly.getTime() <= land.getTime()) {
                        AirportEvent takingoffEvent = new AirportEvent(m_takeofftime, airEvent.getHandler(), AirportEvent.PLANE_DEPARTS, port_to, fly.getcurrentAirplane());
                        Simulator.schedule(takingoffEvent);
                        m_freeToLand = false;
                    } else {
                        AirportEvent landedEvent = new AirportEvent(m_runwayTimeToLand, airEvent.getHandler(), AirportEvent.PLANE_LANDED, port_to, land.getcurrentAirplane());
                        Simulator.schedule(landedEvent);
                        m_freeToLand = false;
                    }
                } else if ((m_inTheAir == 0) && (m_onTheGround > 0)) {
                    AirportEvent fly = takeoffQueue.element();
                    AirportEvent takingoffEvent = new AirportEvent(m_takeofftime, airEvent.getHandler(), AirportEvent.PLANE_DEPARTS, port_to, fly.getcurrentAirplane());
                    Simulator.schedule(takingoffEvent);
                    m_freeToLand = false;
                } else if ((m_inTheAir > 0) && (m_onTheGround == 0)) {
                    AirportEvent land = landingQueue.element();
                    AirportEvent landedEvent = new AirportEvent(m_runwayTimeToLand, airEvent.getHandler(), AirportEvent.PLANE_LANDED, port_to, land.getcurrentAirplane());
                    Simulator.schedule(landedEvent);
                    m_freeToLand = false;
                } else {
                    m_freeToLand = true;
                }
                break;

            case AirportEvent.PLANE_TAKEOFF:
                m_onTheGround++;
                this.takeoffQueue.add(airEvent);
                System.out.println(formatDouble(Simulator.getCurrentTime()) + ": " + tempAirplane.getName() + " takeoff from airport: " + this.getName());
                if (m_freeToLand) {
                    AirportEvent departEvent = new AirportEvent(m_takeofftime, airEvent.getHandler(), AirportEvent.PLANE_DEPARTS, airEvent.getdestinationAirport(), airEvent.getcurrentAirplane());
                    Simulator.schedule(departEvent);
                    m_freeToLand = false;
                }
                break;

            case AirportEvent.PLANE_DEPARTS:
                m_onTheGround--;
                this.takeoffQueue.remove();
                Airport next_port = list[new Random().nextInt(5)];
                while (next_port.getName().equals(this.getName())) {
                    next_port = list[new Random().nextInt(5)];
                }
                m_flightTime = (distance[this.airportID][next_port.airportID] / airEvent.getcurrentAirplane().getSpeed()) * 60;
                this.sentPassenger += tempAirplane.getM_numberPassengers();
                System.out.println(formatDouble(Simulator.getCurrentTime()) + ": "+ tempAirplane.getName()+ " departs from airport: " +this.getName());
                AirportEvent arriveEvent = new AirportEvent(m_flightTime, next_port, AirportEvent.PLANE_ARRIVES, airEvent.getdestinationAirport(), tempAirplane);
                Simulator.schedule(arriveEvent);
                if ((m_onTheGround > 0) && (m_inTheAir > 0)) {
                    AirportEvent fly = takeoffQueue.element();
                    AirportEvent land = landingQueue.element();
                    if (fly.getTime() <= land.getTime()) {
                        AirportEvent takingoffEvent = new AirportEvent(m_takeofftime, airEvent.getHandler(), AirportEvent.PLANE_DEPARTS, port_to, fly.getcurrentAirplane());
                        Simulator.schedule(takingoffEvent);
                        m_freeToLand = false;
                    } else {
                        AirportEvent landedEvent = new AirportEvent(m_runwayTimeToLand, airEvent.getHandler(), AirportEvent.PLANE_LANDED, port_to, land.getcurrentAirplane());
                        Simulator.schedule(landedEvent);
                        m_freeToLand = false;
                    }
                } else if ((m_inTheAir == 0) && (m_onTheGround > 0)) {
                    AirportEvent fly = takeoffQueue.element();
                    AirportEvent takingoffEvent = new AirportEvent(m_takeofftime, airEvent.getHandler(), AirportEvent.PLANE_DEPARTS, port_to, fly.getcurrentAirplane());
                    Simulator.schedule(takingoffEvent);
                    m_freeToLand = false;
                } else if ((m_inTheAir > 0) && (m_onTheGround == 0)) {
                    AirportEvent land = landingQueue.element();
                    AirportEvent landedEvent = new AirportEvent(m_runwayTimeToLand, airEvent.getHandler(), AirportEvent.PLANE_LANDED, port_to, land.getcurrentAirplane());
                    Simulator.schedule(landedEvent);
                    m_freeToLand = false;
                } else {
                    m_freeToLand = true;
                }
                break;
        }
    }
}
