/*
 * NAME: Mahyar Tajeri
 * STUDENT NUMBER: 501039194
 * DATE: April 15th, 2021
 * */
public class Aircraft implements Comparable<Aircraft>
{
    // Instance variables
    int numEconomySeats;
    int numFirstClassSeats;
    String[][] seatLayout;
    String model;
    
    //Contructors
    public Aircraft(int seats, String model)
    {
        this.numEconomySeats = seats;
        this.numFirstClassSeats = 0;
        this.model = model;
        this.seatLayout = new String[4][seats/4];
        createSeatLayout();
    }
    
    public Aircraft(int economy, int firstClass, String model)
    {
        this.numEconomySeats = economy;
        this.numFirstClassSeats = firstClass;
        this.model = model;
        this.seatLayout = new String[4][(economy + firstClass)/4];
        createSeatLayout();
    }
    
    // Creates the seat numbers for each flight by making 4 rows 
    public void createSeatLayout(){
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int firstClassCount = this.numFirstClassSeats;
       
        // Nested loop for 2D seatLayout Array
        for(int i = 0; i < seatLayout.length; i++){
            for(int j = 0; j < seatLayout[i].length; j++){
                String seatNumber = "";
                seatNumber += Integer.toString((j+1));
                seatNumber += Character.toString(alphabet[(i % 4)]); // Letter is either A B C or D 
                if(firstClassCount > 0 && j < this.numFirstClassSeats/4){
                    seatNumber += "+";
                    firstClassCount --; 
                }
                seatLayout[i][j] = seatNumber;
            }
        }
    }
    
    // Getters and Setters
    public int getNumSeats()
    {
        return numEconomySeats;
    }
    
    public int getTotalSeats()
    {
        return numEconomySeats + numFirstClassSeats;
    }
    
    public int getNumFirstClassSeats()
    {
        return numFirstClassSeats;
    }
    
    public String getModel()
    {
        return model;
    }
    
    public void setModel(String model)
    {
        this.model = model;
    }
    
    // Prints a String representation of an Aircraft object
    public void print()
    {
        System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
    }
    
    // Method to compare two Aircraft objects based on seat numbers
    public int compareTo(Aircraft other)
    {
        if (this.numEconomySeats == other.numEconomySeats)
            return this.numFirstClassSeats - other.numFirstClassSeats;
        
        return this.numEconomySeats - other.numEconomySeats; 
    }
}
