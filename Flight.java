import java.util.ArrayList;
import java.util.Random;

/**		
 // ---- EXCEPTIONS ---
 */
//
//This exception is to recognize duplicate passengers
class DuplicatePassengerException extends Exception{
	public DuplicatePassengerException(Passenger p){
		super("Duplicate Passenger " + p.getName() + p.getPassport());
	}
}
//This exception is to recognize passengers not in manifest
class PassengerNotInManifestException extends Exception{
	public PassengerNotInManifestException(Passenger p){
		super("Passenger" + p.getName() + p.getPassport() + "not in flight manifest");
	}
}
//This exception is to recognize occupied seats
class SeatOccupiedException extends Exception{
	public SeatOccupiedException(String seat){
		super("Seat" + seat + " already occupied");
	}
}


public class Flight
{
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
	public static enum Type {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
	public static enum SeatType {ECONOMY, FIRSTCLASS, BUSINESS};

	private String flightNum;
	private String airline;
	private String origin, dest;
	private String departureTime;
	private Status status;
	private int flightDuration;
	protected Aircraft aircraft;
	protected int numPassengers;
	protected Type type;
	protected ArrayList<Passenger> manifest;
	protected ArrayList<String> occupiedSeats;
	//protected TreeMap <String, Passenger> seatMap;
		
	protected Random random = new Random();
	
	private String errorMessage = "";
	  
	public String getErrorMessage()
	{
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

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
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		occupiedSeats = new ArrayList<String>();
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
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		occupiedSeats = new ArrayList<String>(); //seats that are occupied
	}
	
	public Type getFlightType()
	{
		return type;
	}
	public Aircraft getAircraft() {
		return aircraft;
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
	
	public void assignSeat(Passenger p)
	{
		int seat = random.nextInt(aircraft.numEconomySeats);
		p.setSeat("ECO"+ seat);
	}
	
	public String getLastAssignedSeat()
	{
		if (!manifest.isEmpty())
			return manifest.get(manifest.size()-1).getSeat();
		return "";
	}
	
	public boolean seatsAvailable(String seatType)
	{
		if (!seatType.equalsIgnoreCase("ECO")) return false;
		return numPassengers < aircraft.numEconomySeats;
	}
	
	/**
	 * This method cancel seats based in the passenger provided only if the passenger is already in the manifest
	 */
	public boolean cancelSeat(Passenger p) throws PassengerNotInManifestException{
		for (Passenger passenger: manifest) {
			if (passenger.equals(p)) {
				occupiedSeats.remove(p.getSeat());
				manifest.remove(p);
				numPassengers--;
				return true;
			}
		}
		throw new PassengerNotInManifestException(p);
		//return false;
	}
	
	//This method is to reserve a seat for a passenger if the seat isn't already occupied and the passenger is not already on the flight
	public boolean reserveSeat(Passenger p,  String seat) throws SeatOccupiedException, DuplicatePassengerException
	{
		if(manifest.contains(p)){
			throw new DuplicatePassengerException(p);
		}
		if (occupiedSeats.contains(seat)) {
			throw new SeatOccupiedException(seat);
			//return false;
		}else {
			occupiedSeats.add(seat);
			assignSeat(p);
			manifest.add(p);
			numPassengers++;

			return true;
		}
	}

	//print all the passengers on the manifest
	public void printPassengerManifest(){
		for(int i = 0; i < manifest.size(); i++){
			System.out.println(manifest.get(i));
		}
	}

	public boolean equals(Object other)
	{
		Flight otherFlight = (Flight) other;
		return this.flightNum.equals(otherFlight.flightNum);
	}
	
	public String toString()
	{
		 return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
	}

	//this method prints the seat layout
	public void printSeats() {
		String[][] seatLayout = aircraft.getSeatLayout();
		int totalSeats = aircraft.getTotalSeats();
		for (int i = 0; i < 4; i++) {
			if (i == 2) {
				System.out.println();
			}
			for (int j = 0; j < totalSeats/4; j++) {
				if (occupiedSeats.contains(seatLayout[i][j])) {
					System.out.print("XX ");
				} else {
					System.out.print(seatLayout[i][j] + " ");
				}
			}
			System.out.println();
		}
	}
}
