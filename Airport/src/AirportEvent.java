//YOUR NAME HERE

//You need to add an Airplane parameter for the Airport events so you know which plane the event is related to.
public class AirportEvent extends Event {
    public static final int PLANE_ARRIVES = 0;
    public static final int PLANE_LANDED = 1;
    public static final int PLANE_DEPARTS = 2;
    public static final int PLANE_TAKEOFF = 3;

    private Airplane m_plane;
    //private Airport m_nextHandler;
    private double m_arrvingTime;
    private double m_arrivedTime;

    //add airplane parameter
    AirportEvent(Airplane plane, double delay, EventHandler curHandler, int eventType) {
        super(delay, curHandler, eventType);
        m_plane = plane;
        //m_nextHandler = nextHandler;
        m_arrvingTime = 0;
        m_arrivedTime = 0;
    }

    //add getAirplane
    public Airplane getAirplane() {return m_plane;}
    //public Airport getNextHandler() {return m_nextHandler;}

    public void setArrivingTime(double time) {m_arrvingTime = time; }
    public void setArrivedTime(double time) {m_arrivedTime = time; }
    public double getWaitTime() {return m_arrivedTime - m_arrvingTime; }
}
