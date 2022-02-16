/*
 * NAME: Mahyar Tajeri
 * STUDENT NUMBER: 501039194
 * DATE: April 15th, 2021
 * */
public class Reservation
{
    // Instance variables 
    String flightNum;
    String flightInfo;
    boolean firstClass;
    String passengerName;
    String passengerPassport;
    String seat;
    
    // Constructors 
    public Reservation(String flightNum, String info)
    {
        this.flightNum = flightNum;
        this.flightInfo = info;
    }
    
    public Reservation(String flightNum, String passengerName, String passengerPassport)
    {
        this.flightNum = flightNum;
        this.passengerName = passengerName;
        this.passengerPassport = passengerPassport;
    }
    
    public Reservation(String flightNum, String info, String passengerName, String passengerPassport, String seat)
    {
        this.flightNum = flightNum;
        this.flightInfo = info;
        this.passengerName = passengerName;
        this.passengerPassport = passengerPassport;
        this.seat = seat;
    }
    
    // Getters and Setters
    public String getFlightNum()
    {
        return flightNum;
    }
    
    public String getFlightInfo()
    {
        return flightInfo;
    }
    
    public void setFlightInfo(String flightInfo)
    {
        this.flightInfo = flightInfo;
    }
    
    // Checks whether two Reservation objects are equal based on their flightNum, passengerName, and passengerPassport
    @Override
    public boolean equals(Object other)
    {
        Reservation otherRes = (Reservation) other;
        return flightNum.equals(otherRes.flightNum) &&  passengerName.equals(otherRes.passengerName) && passengerPassport.equals(otherRes.passengerPassport); 
    }
    
    // Prints a String representation of a Reservation object
    public void print()
    {
        System.out.println(flightInfo + "\tPassenger Name: " + passengerName + "\tSeat Number: " + seat);
    }
}
