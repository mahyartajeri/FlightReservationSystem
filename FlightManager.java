/*
 * NAME: Mahyar Tajeri
 * STUDENT NUMBER: 501039194
 * DATE: April 15th, 2021
 * */

import java.util.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.io.*;


public class FlightManager
{
    // TreeMap to map flight numbers to Flight objects
    TreeMap<String, Flight> flightsMap = new TreeMap<String, Flight>();
    
    String[] cities  =  {"Dallas", "New York", "London", "Paris", "Tokyo"};
    final int DALLAS = 0;  final int NEWYORK = 1;  final int LONDON = 2;  final int PARIS = 3; final int TOKYO = 4;
    
    int[] flightTimes = { 3, // Dallas
        1, // New York
        7, // London
        8, // Paris
        16,// Tokyo
    };
    // Aircraft ArrayList
    ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();  
    // Randomizer
    Random random = new Random();
    
    
    public FlightManager() throws IOException
    {
       
        // Create some aircraft types with max seat capacities
        airplanes.add(new Aircraft(84, "Boeing 737"));
        airplanes.add(new Aircraft(100,"Airbus 320"));
        airplanes.add(new Aircraft(36, "Dash-8 100"));
        airplanes.add(new Aircraft(4, "Bombardier 5000"));
        airplanes.add(new Aircraft(84, 16, "Boeing 747"));
       
        // Populate the list of flights with flights from flights.txt
        File flightsfile = new File("flights.txt");
        Scanner reader = new Scanner(flightsfile);
        
        // Reads the flights.txt file and parses through each word to gain flight information
        while(reader.hasNextLine()){
            Scanner line = new Scanner(reader.nextLine());
            String airline = "";
            String dest = "";
            String time = "";
            boolean longHaul = false;
            int capacity = -1;
            // First word is airline
            if(line.hasNext()){
                airline = line.next();
                // Remove underscrore and replace with space to create 2 words
                airline = airline.replace("_", " "); 
            }
            // Second word should be destination
            if(line.hasNext()){
                dest = line.next();
                // Remove underscrore and replace with space to create 2 words
                dest = dest.replace("_", " ");
                // Tokyo flight will be longHaul
                if(dest.equalsIgnoreCase("Tokyo")) longHaul = true;
            }
            // Third word is departure time
            if(line.hasNext()){
                time = line.next();
            }
            // Last word is minimum required capacity of aircraft
            if(line.hasNext()){
                capacity = Integer.parseInt(line.next());
            }
            
            
            //Find appropriate Aircraft
            Aircraft craft = null;
            ArrayList<Aircraft> usedCrafts = new ArrayList<Aircraft>();
            int smallest = 10000;
            for(Aircraft a : airplanes){
                // For long haul flights
                if(longHaul){
                    if(a.getTotalSeats() - capacity >= 0 && a.getNumSeats() - capacity < smallest && a.getNumFirstClassSeats() > 0){
                        smallest = a.getTotalSeats() - capacity;
                        craft = a;
                    }
                }
                // Regular flights
                else if(a.getNumSeats() - capacity >= 0 && a.getNumSeats() - capacity < smallest){
                    smallest = a.getNumSeats() - capacity;
                    craft = a;
                }
            }
            
            
            // Create flight number and duration
            String flightNum = generateFlightNumber(airline);
            int flightDuration = random.nextInt(18) + 1;
            
            
            Flight flight;
            // For long haul
            if(longHaul){
                flight = new LongHaulFlight(flightNum, airline, dest, time, flightDuration, craft);
            }
            // For regular
            else flight = new Flight(flightNum, airline, dest, time, flightDuration, craft);
            
            // Map a flight number to its Flight Object
            flightsMap.put(flightNum, flight);
 
        }

    }
    
    // Method to generate a flight number based on airline String
    private String generateFlightNumber(String airline)
    {
        String word1, word2;
        Scanner scanner = new Scanner(airline);
        word1 = scanner.next();
        word2 = scanner.next();
        String letter1 = word1.substring(0, 1);
        String letter2 = word2.substring(0, 1);
        letter1.toUpperCase(); letter2.toUpperCase();
        
        // Generate random number between 101 and 300
        int flight = random.nextInt(200) + 101;
        while(flightsMap.keySet().contains(letter1 + letter2 + flight)){
            flight = random.nextInt(200) + 101;
        }
        String flightNum = letter1 + letter2 + flight;
        return flightNum;
    }
    
    // Prints all of the flights in the TreeMap
    public void printAllFlights()
    {
        for (String key : flightsMap.keySet())
            System.out.println(flightsMap.get(key));
    }
    
    // Method to return a reservation on a flight for a specified seat and passenger credentials if applicable 
    public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seat) throws FlightNotFoundException, 
        DuplicatePassengerException, InvalidSeatException, SeatOccupiedException, FlightFullException
    {
        Flight flight = flightsMap.get(flightNum);
        // Check if flight exists
        if (flight == null)
        {
            throw new FlightNotFoundException("Flight " + flightNum + " Not Found!");
        }
        
        // Create a passenger object based on values and call the reserveSeat method in the Flight class (polymorphism implemented for longHaulFlight)
        Passenger p = new Passenger(name, passport);
        flight.reserveSeat(p, seat);
        
        // Returns a Reservation object
        return new Reservation(flightNum, flight.toString(), name, passport, seat);
    }
    
    // Method to cancel a reservation on a flight for a specified Reservation object
    public void cancelReservation(Reservation res) throws FlightNotFoundException, PassengerNotInManifestException
    {
        // Check if flight associated with res exists
        Flight flight = flightsMap.get(res.getFlightNum());
        if (flight == null)
        {
            throw new FlightNotFoundException("Flight " + res.getFlightNum() + " Not Found");
        }
        // Calls the cancelSeat method in the Flight class (polymorphism implemented for longHaulFlight)
        flight.cancelSeat(new Passenger(res.passengerName, res.passengerPassport, res.seat));
    }
    
    /* No Sorting Required For Part 2 of The Assignment as it is not a Required Command in FlightReservationSystem.java
    public void sortByDeparture()
    {
        Collections.sort(flights, new DepartureTimeComparator());
    }
    
    private class DepartureTimeComparator implements Comparator<Flight>
    {
        public int compare(Flight a, Flight b)
        {
            return a.getDepartureTime().compareTo(b.getDepartureTime());   
        }
    }
    
    public void sortByDuration()
    {
        Collections.sort(flights, new DurationComparator());
    }
    
    private class DurationComparator implements Comparator<Flight>
    {
        public int compare(Flight a, Flight b)
        {
            return a.getFlightDuration() - b.getFlightDuration();
        }
    }
    
    public void sortAircraft()
    {
        Collections.sort(airplanes);
    }
    */
    
    // Prints a list of all Aircraft objects in the airplanes ArrayList
    public void printAllAircraft()
    {
        for (Aircraft craft : airplanes)
            craft.print();
    }
}
