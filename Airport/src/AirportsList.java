/**
 * Created by xiaohan on 2/12/17.
 */
public class AirportsList {
    public static Airport lax = new Airport("LAX", 10, 10, 20, 16, 0);
    public static Airport sea = new Airport("SEA", 10, 10, 20, 16, 0);
    public static Airport nyc = new Airport("NYC", 10, 10, 20, 16, 0);
    public static Airport atl = new Airport("ATL", 10, 10, 20, 16, 0);
    public static Airport ord = new Airport("ORD", 10, 10, 20, 16, 0);
    public static Airport[] airports = {lax, sea, nyc, atl, ord};

    public Airport[] getAirports() {
        return airports;
    }
}
