/*
 * A Long Haul Flight is a flight that travels a long distance and has two types of seats (First Class and Economy)
 */
//First class flights
public class LongHaulFlight extends Flight
{
	int firstClassPassengers;
		
	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		super(flightNum, airline, dest, departure, flightDuration, aircraft);
		type = Flight.Type.LONGHAUL;
	}

	public LongHaulFlight()
	{
		firstClassPassengers = 0;
		type = Flight.Type.LONGHAUL;
	}
	
	public void assignSeat(Passenger p)
	{
		int seat = random.nextInt(aircraft.getNumFirstClassSeats());
		p.setSeat("FCL"+ seat);
	}
	
	public boolean reserveSeat(String name, String passport, String seatType)
	{
		if (seatType.equalsIgnoreCase("FCL"))
		{
			if (firstClassPassengers >= aircraft.getNumFirstClassSeats())
			{
				setErrorMessage("No First Class Seats Available");
				return false;
			}
			Passenger p = new Passenger(name, passport, "", seatType);
			
			if (manifest.indexOf(p) >=  0)
			{
				setErrorMessage("Duplicate Passenger " + p.getName() + " " + p.getPassport());
				return false;
			}
			
			assignSeat(p);
			manifest.add(p);
			firstClassPassengers++;
			return true;
		}
		else // economy passenger
			return super.reserveSeat(name, passport, seatType);
	}
	
	public boolean cancelSeat(String name, String passport, String seatType)
	{
		if (seatType.equalsIgnoreCase("FCL"))
		{
			Passenger p = new Passenger(name, passport);
			if (manifest.indexOf(p) == -1) 
			{
				throw new PassengerNotInManifestException;
				return false;
			}
			manifest.remove(p);
			if (firstClassPassengers > 0)	firstClassPassengers--;
			return true;
		}
		else
			return super.cancelSeat(name, passport, seatType);
	}
	
	public int getPassengerCount()
	{
		return getNumPassengers() +  firstClassPassengers;
	}
	
	
	public boolean seatsAvailable(String seatType)
	{
		if (seatType.equals("FCL"))
			return firstClassPassengers < aircraft.getNumFirstClassSeats();
		else
			return super.seatsAvailable(seatType);
	}
	
	public String toString()
	{
		 return super.toString() + "\t LongHaul";
	}
}
