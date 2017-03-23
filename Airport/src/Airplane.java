//Xiao Han

//TODO add number of passengers, speed

public class Airplane {
    //private int m_Id;
    //private int Id;
    //private int m_maxSpeed;
    //private int numberPassengers;
    private String m_name;
    private int m_numberPassengers;
    private double maxSpeed;
    private int capacity;
    private double startLandTime;

    public void setStartLandTime(double startLandTime) {
        this.startLandTime = startLandTime;
    }

    public double getStartLandTime() {
        return startLandTime;
    }

    public String getName() {
        return m_name;
    }

    public Airplane(String name, int numberPassengers, double speed, int cap) {
        m_name = name;
        m_numberPassengers = numberPassengers;
        maxSpeed = speed;
        capacity = cap;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getSpeed() {
        return maxSpeed;
    }

    public int getM_numberPassengers() {
        return m_numberPassengers;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public void setM_numberPassengers(int m_numberPassengers) {
        this.m_numberPassengers = m_numberPassengers;
    }

    public void setSpeed(double speed) {
        this.maxSpeed = speed;
    }
}

