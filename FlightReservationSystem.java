/*
 * NAME: Mahyar Tajeri
 * STUDENT NUMBER: 501039194
 * DATE: April 15th, 2021
 * */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!


public class FlightReservationSystem
{
    public static void main(String[] args)
    {
        // Creates a FlightManger object
        FlightManager manager = null;
        try{
            manager = new FlightManager();
        }
        catch(IOException e){
            System.out.println("File Reading Error! Please Fix and Restart.");
        }
        
        
        ArrayList<Reservation> myReservations = new ArrayList<Reservation>(); // my flight reservations
        
        // Scanner for user input
        Scanner scanner = new Scanner(System.in);
        System.out.print(">");
        
        while (scanner.hasNextLine())
        {
            String inputLine = scanner.nextLine();
            if (inputLine == null || inputLine.equals("")) 
            {
                System.out.print("\n>");
                continue;
            }
            // Another scanner to parse through each line of commands
            Scanner commandLine = new Scanner(inputLine);
            String action = commandLine.next();
            
            if (action == null || action.equals("")) 
            {
                System.out.print("\n>");
                continue;
            }
            // Quit program
            else if (action.equals("Q") || action.equals("QUIT"))
                return;
            // Print a list of flights
            else if (action.equalsIgnoreCase("LIST"))
            {
                manager.printAllFlights(); 
            }
            // Reserve a seat on a flight
            else if (action.equalsIgnoreCase("RES"))
            {
                String flightNum = null;
                String passengerName = "";
                String passport = "";
                String seat = "";
                
                if (commandLine.hasNext())
                {
                    flightNum = commandLine.next();
                }
                if (commandLine.hasNext())
                {
                    passengerName = commandLine.next();
                }
                if (commandLine.hasNext())
                {
                    passport = commandLine.next();
                }
                if (commandLine.hasNext())
                {
                    seat = commandLine.next();
                    
                    try{
                        Reservation res = manager.reserveSeatOnFlight(flightNum, passengerName, passport, seat);
                        if (res != null)
                        {
                            myReservations.add(res);
                            res.print();
                        }
                    }
                    // Catch Exceptions
                    catch(DuplicatePassengerException e1){
                        System.err.println(e1.getMessage());
                    }
                    catch(FlightNotFoundException e2){
                        System.err.println(e2.getMessage());
                    }
                    catch(FlightFullException e3){
                        System.err.println(e3.getMessage());
                    }
                    catch(InvalidSeatException e4){
                        System.err.println(e4.getMessage());
                    }
                    catch(SeatOccupiedException e5){
                        System.err.println(e5.getMessage());
                    }
                    
                    
                    
                }
            }
            // Cancel a seat on a flight
            else if (action.equalsIgnoreCase("CANCEL"))
            {
                Reservation res = null;
                String flightNum = null;
                String passengerName = "";
                String passport = "";
                
                
                if (commandLine.hasNext())
                {
                    flightNum = commandLine.next();
                }
                if (commandLine.hasNext())
                {
                    passengerName = commandLine.next();
                }
                if (commandLine.hasNext())
                {
                    passport = commandLine.next();
                    try{
                        int index = myReservations.indexOf(new Reservation(flightNum, passengerName, passport));
                        if (index >= 0)
                        {
                            manager.cancelReservation(myReservations.get(index));
                            myReservations.remove(index);
                        }
                        else{
                            throw new ReservationNotFoundException("Reservation on Flight " + flightNum + " Not Found!");
                        }
                    }
                    // Catch Exceptions
                    catch(FlightNotFoundException e1){
                        System.err.println(e1.getMessage());
                    }
                    catch(ReservationNotFoundException e2){
                        System.err.println(e2.getMessage());
                    }
                    catch(PassengerNotInManifestException e3){
                        System.err.println(e3.getMessage());
                    }
                }
            }
            // Print the seats on a flight
            else if (action.equalsIgnoreCase("SEATS"))
            {
                String flightNum = "";
                
                if (commandLine.hasNext())
                {
                    flightNum = commandLine.next();
                    try{
                        if(manager.flightsMap.get(flightNum) != null){
                            manager.flightsMap.get(flightNum).printSeats();
                        }
                        else throw new FlightNotFoundException("Flight " + flightNum + " Not Found!");
                    }
                    // Catch Exception
                    catch(FlightNotFoundException e1){
                        System.err.println(e1.getMessage());
                    }
                    
                    
                }
                
            }
            // Print all reservations made
            else if (action.equalsIgnoreCase("MYRES"))
            {
                for (Reservation myres : myReservations)
                    myres.print();
            }
            // Print all of the passengers on a flight
            else if (action.equalsIgnoreCase("PASMAN")){
                if(commandLine.hasNext()){
                    String flightNum = commandLine.next();
                    try{
                        if(manager.flightsMap.get(flightNum) != null){
                            manager.flightsMap.get(flightNum).printPassengerManifest();
                        }
                        else throw new FlightNotFoundException("Flight " + flightNum + " Not Found!");
                    }
                    // Catch Exception
                    catch(FlightNotFoundException e1){
                        System.err.println(e1.getMessage());
                    }
                }
                
                
                
            }
            
            /* These commands are not required for part 2
             else if (action.equalsIgnoreCase("SORTBYDEP"))
             {
             manager.sortByDeparture();
             }
             else if (action.equalsIgnoreCase("SORTBYDUR"))
             {
             manager.sortByDuration();
             }
             else if (action.equalsIgnoreCase("CRAFT"))
             {
             manager.printAllAircraft();
             }
             else if (action.equalsIgnoreCase("SORTCRAFT"))
             {
             manager.sortAircraft();
             }
             */
            System.out.print("\n>");
        }
    }
    
    
}

