/*
 * NAME: Mahyar Tajeri
 * STUDENT NUMBER: 501039194
 * DATE: April 15th, 2021
 * */
public class Passenger
{
    // Instance variables
    private String name;
    private String passport;
    private String seat;
    private String seatType;
    
    // Constructors
    public Passenger(String name, String passport, String seat)
    {
        this.name = name;
        this.passport = passport;
        this.seat = seat;
        if(seat.contains("+")){
            this.seatType = Flight.SeatType.FIRSTCLASS.toString();
        }
        else{
            this.seatType = Flight.SeatType.ECONOMY.toString();
        }
    }
    
    public Passenger(String name, String passport)
    {
        this.name = name;
        this.passport = passport;
    }
    
    // Method to check if two Passenger objects are equal based on their name and passport 
    @Override
    public boolean equals(Object other)
    {
        Passenger otherP = (Passenger) other;
        return name.equals(otherP.name) && passport.equals(otherP.passport);
    }
    
    // Getters and Setters
    public String getSeatType()
    {
        return seatType;
    }
    
    public void setSeatType(String seatType)
    {
        this.seatType = seatType;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPassport()
    {
        return passport;
    }
    
    public void setPassport(String passport)
    {
        this.passport = passport;
    }
    
    public String getSeat()
    {
        return seat;
    }
    
    public void setSeat(String seat)
    {
        this.seat = seat;
    }
    
    // Returns a String representation of the Passenger object
    public String toString()
    {
        return name + " " + passport + " " + seat;
    }
}
