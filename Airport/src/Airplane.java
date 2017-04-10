//YOUR NAME HERE

//TODO add number of passengers, speed
import java.util.Random;

//It should have at minimum properties of speed and maximum passenger capacity.
public class Airplane {
    private String m_name;
    private int m_numberPassengers;
    //add speed parameter
    private int m_speed;
    private int m_maxPassengers;

    //gas-----------------------------------------------------
    //***************************************today*********************************************
    private double gasVolume;
    private double gasConsumed = 0;
    public double gasSpeedTraveling;
    public double gasSpeedCirculing;
    //gas-----------------------------------------------------
    //***************************************today*********************************************

    //add speed, maximum passengers
    public Airplane(String name, int max_passenger, int speed, double volume, double gasSpeed1, double gasSpeed2) {
        m_name = name;
        m_maxPassengers = max_passenger;
        m_speed = speed;
        gasSpeedTraveling = gasSpeed1;
        gasSpeedCirculing = gasSpeed2;
        Random rand = new Random();
        m_numberPassengers = rand.nextInt(m_maxPassengers) + 1;
    }

    public String getName() {
        return m_name;
    }

    public int getNumberPassengers() {return m_numberPassengers; }
    public int getMaxPassengers() { return m_maxPassengers; }
    public int getSpeed() {return m_speed; }

    public void setNumberPassengers(){
        Random rand = new Random();
        m_numberPassengers = rand.nextInt(m_maxPassengers) + 1;
    }

    //gas-----------------------------------------------------
    //***************************************today*********************************************
    public double calGas(double duration, double speed){
        return gasConsumed = duration * speed;
    }
    //gas-----------------------------------------------------
    //***************************************today*********************************************
}
