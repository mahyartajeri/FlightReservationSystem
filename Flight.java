/*
 * NAME: Mahyar Tajeri
 * STUDENT NUMBER: 501039194
 * DATE: April 15th, 2021
 * */

import java.util.*;
import java.util.Random;

public class Flight
{
    // Enums used for status, type of flight, and type of seat
    public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
    public static enum FlightType {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
    public static enum SeatType {ECONOMY, FIRSTCLASS, BUSINESS};
    
    // Instance variables
    private String flightNum;
    private String airline;
    private String origin, dest;
    private String departureTime;
    private Status status;
    private int flightDuration;
    protected Aircraft aircraft;
    protected int numPassengers;
    protected FlightType flightType;
    protected ArrayList<Passenger> manifest;
    protected TreeMap<String, Passenger> seatMap;
    
    // Constructors
    public Flight()
    {
        this.flightNum = "";
        this.airline = "";
        this.dest = "";
        this.origin = "Toronto";
        this.departureTime = "";
        this.flightDuration = 0;
        this.aircraft = null;
        numPassengers = 0;
        status = Status.ONTIME;
        flightType = FlightType.MEDIUMHAUL;
        manifest = new ArrayList<Passenger>();
        seatMap = new TreeMap<String, Passenger>();
    }
    
    public Flight(String flightNum)
    {
        this.flightNum = flightNum;
    }
    
    public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
    {
        this.flightNum = flightNum;
        this.airline = airline;
        this.dest = dest;
        this.origin = "Toronto";
        this.departureTime = departure;
        this.flightDuration = flightDuration;
        this.aircraft = aircraft;
        numPassengers = 0;
        status = Status.ONTIME;
        flightType = FlightType.MEDIUMHAUL;
        manifest = new ArrayList<Passenger>();
        seatMap = new TreeMap<String, Passenger>();
    }
    
    //Getters and Setters
    public FlightType getFlightType()
    {
        return flightType;
    }
    
    public void setFlightType(FlightType flightType){
        this.flightType = flightType;
    }
    
    public String getFlightNum()
    {
        return flightNum;
    }
    public void setFlightNum(String flightNum)
    {
        this.flightNum = flightNum;
    }
    public String getAirline()
    {
        return airline;
    }
    public void setAirline(String airline)
    {
        this.airline = airline;
    }
    public String getOrigin()
    {
        return origin;
    }
    public void setOrigin(String origin)
    {
        this.origin = origin;
    }
    public String getDest()
    {
        return dest;
    }
    public void setDest(String dest)
    {
        this.dest = dest;
    }
    public String getDepartureTime()
    {
        return departureTime;
    }
    public void setDepartureTime(String departureTime)
    {
        this.departureTime = departureTime;
    }
    
    public Status getStatus()
    {
        return status;
    }
    public void setStatus(Status status)
    {
        this.status = status;
    }
    public int getFlightDuration()
    {
        return flightDuration;
    }
    public void setFlightDuration(int dur)
    {
        this.flightDuration = dur;
    }
    
    public int getNumPassengers()
    {
        return numPassengers;
    }
    public void setNumPassengers(int numPassengers)
    {
        this.numPassengers = numPassengers;
    }
    
    // Sets a Passenger object's seat value 
    public void assignSeat(Passenger p, String seat)
    {
        p.setSeat(seat);
    }
    
    // Returns the most recent seat that was assigned to a passenger
    public String getLastAssignedSeat()
    {
        if (!manifest.isEmpty())
            return manifest.get(manifest.size()-1).getSeat();
        return "";
    }
    
    // Method to cancel a passenger's seat on a flight
    public void cancelSeat(Passenger p) throws PassengerNotInManifestException
    {
       
        // Check if passenger Exists
        if (manifest.indexOf(p) == -1) 
        {
            throw new PassengerNotInManifestException("Passenger " + p.getName() + " " + p.getPassport() + " Not Found!");               
        }
        
        // Remove passsenger from manifest and seatMap
        manifest.remove(p);
        seatMap.remove(p.getSeat());
        numPassengers--;
        
    }
    
    // Method to reserve a seat for a passenger on a flight
    public void reserveSeat(Passenger p, String seat) throws FlightFullException, InvalidSeatException, DuplicatePassengerException, SeatOccupiedException
    {
        
        // Check for errors 
        if (numPassengers >= aircraft.getNumSeats())
        {
            throw new FlightFullException("Flight " + flightNum + " is Full!");
        }
        if (seat.contains("+")) 
        {
            throw new InvalidSeatException("Invalid Seat Type Request!");
            
        } 
        
        // Check for duplicate passenger
        if (manifest.indexOf(p) >= 0)
        {
            throw new DuplicatePassengerException("Duplicate Passenger " + p.getName() + " " + p.getPassport());
        }
        
        // Check if seat exists
        boolean found = false;
        outerloop:
        for(int i = 0; i < this.aircraft.seatLayout.length; i++){
            for(int j = 0; j < this.aircraft.seatLayout[i].length; j++){
                if(this.aircraft.seatLayout[i][j].equalsIgnoreCase(seat)){
                    found = true;
                    break outerloop;
                }
            }
        }
        // Verify if seat is found
        if(!found) throw new InvalidSeatException("Seat Number Not Found!");
        
        // Check if seat is occupied
        if(!seatMap.isEmpty()){
            for(String key : this.seatMap.keySet()){
                if(key.equals(seat)) throw new SeatOccupiedException("Seat " + seat + " is Occupied!");
            }
        }
        
        // Add passenger to manifest and seatMap
        assignSeat(p, seat);
        manifest.add(p);
        seatMap.put(seat, p);
        numPassengers++;
    }
    
    // Method to print each seat number on this flight in a neat fashion
    public void printSeats(){
        // Nested for loop since seatLayout in aircraft is a 2D Array of Strings
        for(int i = 0; i < this.aircraft.seatLayout.length; i++){
            for(int j = 0; j < this.aircraft.seatLayout[i].length; j++){
                // Another loop to check if seat is occupied
                boolean occupied = false;
                for(int k = 0; k < manifest.size(); k++){
                    if(manifest.get(k).getSeat().equals(this.aircraft.seatLayout[i][j])){
                        occupied = true;
                        break;
                    }
                }
                // Occupied seats will print XX instead of the seat Number 
                if(occupied) System.out.print("XX" + "\t");
                else System.out.print(this.aircraft.seatLayout[i][j] + "\t");
            }
            System.out.println();
            // Add a space for the aisle of the plane 
            if(i % 2 == 1) System.out.println();
        }
        
    }
    // Prints a list of every Passenger on this flight
    public void printPassengerManifest(){
        for(Passenger p : this.manifest) System.out.println(p.toString());
       
    }
    
    // Checks if two flights are equal based on their flightNum
    @Override
    public boolean equals(Object other)
    {
        Flight otherFlight = (Flight) other;
        return this.flightNum.equals(otherFlight.flightNum);
    }
    
    // Prints a string representation of a Flight object
    public String toString()
    {
        String info = airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " 
            + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status + "\tCapacity: " + this.aircraft.getTotalSeats();
        
        return info;
    }
}
// Exception Classes
class DuplicatePassengerException extends Exception{
    public DuplicatePassengerException(String errorMessage){
        super(errorMessage);
    }
}

class PassengerNotInManifestException extends Exception{
    public PassengerNotInManifestException(String errorMessage){
        super(errorMessage);
    }
}

class SeatOccupiedException extends Exception{
    public SeatOccupiedException(String errorMessage){
        super(errorMessage);
    }
}

class FlightFullException extends Exception{
    public FlightFullException(String errorMessage){
        super(errorMessage);
    }
}

class InvalidSeatException extends Exception{
    public InvalidSeatException(String errorMessage){
        super(errorMessage);
    }
}

class FlightNotFoundException extends Exception{
    public FlightNotFoundException(String errorMessage){
        super(errorMessage);
    }
}

class ReservationNotFoundException extends Exception{
    public ReservationNotFoundException(String errorMessage){
        super(errorMessage);
    }
}


















