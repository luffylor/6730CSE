//Xiao Han

import java.io.*;
import java.util.Random;


public class AirportSim {
    public static void main(String[] args) throws IOException {
//        // five different airports
//        Airport[] airports = new AirportsList().getAirports();
//        double temp = 55;
//        for (int i = 0; i <= 4; i++) {
//            airports[i].setM_requiredTimeOnGround(temp);
//        }
//        //n different types of airlines
//        String airplaneName;
//        for (int i = 0; i < 10; i++) {
//            int index = new Random().nextInt(5);
//            if (i < 10) {
//                airplaneName = "DL0" + i;
//            } else {
//                airplaneName = "DL" + i;
//            }
//            //int numberOfPlane = (int) (Math.random() * 101);
//            Airplane tempAirplane = new Airplane(airplaneName, 100, 600, new Random().nextInt(500) + 100);
//            AirportEvent tempEvent = new AirportEvent(0, airports[index], AirportEvent.PLANE_ARRIVES, airports[index], tempAirplane);
//            Simulator.schedule(tempEvent);
//        }
//        Simulator.stopAt(2000);
//        Simulator.run();
//        double averageCirclingTime = 0;
//        int totalReceivedPassenger = 0;
//        int totalSentPassenger = 0;
//        //int totalTravelPassenger = 0;
//        double totalCirclingTime = 0;
//
//        for (int i = 0; i < 5; i++) {
//            System.out.println(airports[i].getName() + " Total circling time is: " + Airport.formatDouble(airports[i].getcirclingTime()));
//            System.out.println(airports[i].getName() + " Average circling time is: " + airports[i].getcirclingTime() / airports[i].getpassAirplane());
//            System.out.println(airports[i].getName() + " Received passenger is: " + airports[i].getreceivedPassenger());
//            System.out.println(airports[i].getName() + " Sent passenger is: " + airports[i].getsentPassenger());
//            //System.out.println(airports[i].getName() + " Travel passenger is: " + totalReceivedPassenger + totalSentPassenger);
//            averageCirclingTime += airports[i].getcirclingTime() / airports[i].getpassAirplane();
//            totalReceivedPassenger += airports[i].getreceivedPassenger();
//            totalSentPassenger += airports[i].getsentPassenger();
//            //totalTravelPassenger = totalReceivedPassenger + totalSentPassenger;
//            totalCirclingTime += airports[i].getcirclingTime();
//        }
//        System.out.println(temp + " " + (int)averageCirclingTime + " " + totalReceivedPassenger);
//    }
//
        // five different airports

        Airport[] airports = new AirportsList().getAirports();
        double temp = 55;
        for (int i = 0; i <= 4; i++) {
            airports[i].setM_requiredTimeOnGround(temp);
        }
        //n different types of airlines
        String airplaneName;
        for (int i = 0; i < 10; i++) {
            int index = new Random().nextInt(5);
            if (i < 10) {
                airplaneName = "DL0" + i;
            } else {
                airplaneName = "DL" + i;
            }
            //int numberOfPlane = (int) (Math.random() * 101);
            Airplane tempAirplane = new Airplane(airplaneName, 100, 600, new Random().nextInt(500) + 100);
            AirportEvent tempEvent = new AirportEvent(0, airports[index], AirportEvent.PLANE_ARRIVES, airports[index], tempAirplane);
            Simulator.schedule(tempEvent);
        }
        Simulator.stopAt(200);
        Simulator.run();
        double averageCirclingTime = 0;
        int totalReceivedPassenger = 0;
        int totalSentPassenger = 0;
        //int totalTravelPassenger = 0;
        double totalCirclingTime = 0;

        for (int i = 0; i < 5; i++) {
            System.out.println(airports[i].getName() + " Total circling time is: " + Airport.formatDouble(airports[i].getcirclingTime()));
            System.out.println(airports[i].getName() + " Average circling time is: " + airports[i].getcirclingTime() / airports[i].getpassAirplane());
            System.out.println(airports[i].getName() + " Received passenger is: " + airports[i].getreceivedPassenger());
            System.out.println(airports[i].getName() + " Sent passenger is: " + airports[i].getsentPassenger());
            //System.out.println(airports[i].getName() + " Travel passenger is: " + totalReceivedPassenger + totalSentPassenger);
            averageCirclingTime += airports[i].getcirclingTime() / airports[i].getpassAirplane();
            totalReceivedPassenger += airports[i].getreceivedPassenger();
            totalSentPassenger += airports[i].getsentPassenger();
            //totalTravelPassenger = totalReceivedPassenger + totalSentPassenger;
            totalCirclingTime += airports[i].getcirclingTime();
        }
        System.out.println(temp + " " + (int)totalCirclingTime + " " + totalReceivedPassenger);
    }



//        File csv = new File("runwaytimetoland.csv"); //
//        BufferedWriter bw2 = new BufferedWriter(new FileWriter(csv, false)); //
//
//        int temp = 2;
//        while(temp < 45) {
//            Airport[] airports = new AirportsList().getAirports();
//            String airportName;
//            for (int i = 0; i < 70; i++) {
//                int index = new Random().nextInt(5);
//                if (i < 10) {
//                    airportName = "DL0" + i;
//                } else {
//                    airportName = "DL" + i;
//                }
//                Airplane tempAirplane = new Airplane(airportName, 100, 600, new Random().nextInt(500) + 100);
//                AirportEvent tempEvent = new AirportEvent(0, airports[index], AirportEvent.PLANE_ARRIVES, airports[index], tempAirplane);
//                Simulator.schedule(tempEvent);
//            }
//            Simulator.stopAt(200000);
//            Simulator.run();
//            double average_circlingtime = 0;
//            int total_arrivepassenger = 0;
//            int total_leavepassenger = 0;
//            double total_circlingtime = 0;
//            for (int i = 0; i < 5; i++) {
//                System.out.println(airports[i].getName() + " total circling time is: " + Airport.formatDouble(airports[i].getcirclingTime()));
//                System.out.println(airports[i].getName() + " average circling time is: " + airports[i].getcirclingTime() / airports[i].getpassAirplane());
//                System.out.println(airports[i].getName() + " recieved passenger is: " + airports[i].getreceivedPassenger());
//                System.out.println(airports[i].getName() + " recieved passenger is: " + airports[i].getsentPassenger());
//                average_circlingtime += airports[i].getcirclingTime() / airports[i].getpassAirplane();
//                total_arrivepassenger += airports[i].getreceivedPassenger();
//                total_leavepassenger += airports[i].getsentPassenger();
//                total_circlingtime += airports[i].getcirclingTime();
//
//            }
//            System.out.println(temp + ": " + (int)average_circlingtime / 5 + " : ");
//            bw2.write(temp + "," + average_circlingtime / 5 + "," + total_arrivepassenger / 5 + "," + total_leavepassenger / 5 + "," + total_circlingtime / 5);
//            bw2.newLine();
//            temp += 5;
//            for (int i = 0; i < 5; i++) {
//                airports[i].setreceivedPassenger(0);
//                airports[i].setFlyawaypassenger_number(0);
//                airports[i].setcirclingTime(0);
//                airports[i].setpassAirplane(0);
//            }
//        }
//
//        bw2.close();
//    }
}
