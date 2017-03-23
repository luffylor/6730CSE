//Xiao Han

public class AirportEvent extends Event {
    public static final int PLANE_ARRIVES = 0;
    public static final int PLANE_LANDED = 1;
    public static final int PLANE_TAKEOFF = 2;
    public static final int PLANE_DEPARTS = 3;
    private Airplane currentAirplane;
    private Airport destinationAirport;

    public Airplane getcurrentAirplane() {
        return currentAirplane;
    }

    public Airport getdestinationAirport() {
        return destinationAirport;
    }

    AirportEvent(double delay, EventHandler handler, int eventType, Airport destination, Airplane airplane) {
        super(delay, handler, eventType);
        currentAirplane = airplane;
        destinationAirport = destination;
    }
}
