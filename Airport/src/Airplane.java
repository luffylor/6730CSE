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

    //add speed, maximum passengers
    public Airplane(String name, int max_passenger, int speed) {
        m_name = name;
        m_maxPassengers = max_passenger;
        m_speed = speed;
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
}
