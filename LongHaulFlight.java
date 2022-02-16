/*
 * NAME: Mahyar Tajeri
 * STUDENT NUMBER: 501039194
 * DATE: April 15th, 2021
 * */

/*
 * A Long Haul Flight is a flight that travels a long distance and has two types of seats (First Class and Economy)
 */

public class LongHaulFlight extends Flight
{
    // Instance varible
    int firstClassPassengers;
    
    // Contructors
    public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
    {
        super(flightNum, airline, dest, departure, flightDuration, aircraft);
        flightType = Flight.FlightType.LONGHAUL;
    }
    
    public LongHaulFlight()
    {
        firstClassPassengers = 0;
        flightType = Flight.FlightType.LONGHAUL;
    }
    
    // Sets a Passenger object's seat value
    public void assignSeat(Passenger p, String seat)
    {
        p.setSeat(seat);
    }
    
    // Method to reserve a seat on a flight
    public void reserveSeat(Passenger p, String seat) throws FlightFullException, InvalidSeatException, DuplicatePassengerException, SeatOccupiedException
    {
        // Check for first class
        if (seat.contains("+"))
        {
            // Check for errors
            if (firstClassPassengers >= aircraft.getNumFirstClassSeats())
            {
                throw new FlightFullException("No First Class Seats Available!");
            }
            
            if (manifest.indexOf(p) >=  0)
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
                if(!found) throw new InvalidSeatException("Seat Number Not Found!");
                
            // Check if seat is occupied
            for(String key : this.seatMap.keySet()){
                if(key.equals(seat)) throw new SeatOccupiedException("Seat " + seat + " is Occupied!");
            }
            
            // add the passenger object to the manifest and seatMap
            assignSeat(p, seat);
            manifest.add(p);
            seatMap.put(p.getSeat(), p);
            firstClassPassengers++;
            
        }
        else // economy passenger
            super.reserveSeat(p, seat);
    }
    
    // Method to cancel a passenger's seat
    public void cancelSeat(Passenger p) throws PassengerNotInManifestException
    {
        // Check if passenger is in first class seat
        if (p.getSeat().contains("+"))
        {
            // Check if passenger exists
            if (manifest.indexOf(p) == -1) 
            {
                throw new PassengerNotInManifestException("Passenger " + p.getName() + " " + p.getPassport() + " Not Found!");
            }
            // Remove passenger from manifest and seatMap
            manifest.remove(p);
            seatMap.remove(p.getSeat());
            // Decrement the number of first class passnegers
            if (firstClassPassengers > 0) firstClassPassengers--;
            
        }
        // For economy seats
        else
            super.cancelSeat(p);
    }
    // Returns passengerCount
    public int getPassengerCount()
    {
        return getNumPassengers() +  firstClassPassengers;
    }
    
    // Returns flightType which is an Enum
    @Override 
    public FlightType getFlightType(){
        return Flight.FlightType.LONGHAUL;
    }
    
    // Returns the toString() of the parent class + "LongHaul" to indicate that this is a long haul flight
    public String toString()
    {
        return super.toString() + "\t LongHaul";
    }
}
