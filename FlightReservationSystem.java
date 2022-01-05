import java.util.ArrayList;
import java.util.Scanner;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!


public class FlightReservationSystem
{
	public static void main(String[] args)
	{
		FlightManager manager = new FlightManager();

		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations


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

			Scanner commandLine = new Scanner(inputLine);
			String action = commandLine.next();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			else if (action.equals("Q") || action.equals("QUIT"))
				return;

			else if (action.equalsIgnoreCase("LIST"))
			{
				manager.printAllFlights(); 
			}

			//commands reservation
			else if (action.equalsIgnoreCase("RES"))
			{
				int characteristics = 0;
				String flightNum = null;
				String passengerName = "";
				String passport = "";
				String seat = "";

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
					characteristics++;
				}
				if (commandLine.hasNext())
				{
					passengerName = commandLine.next();
					characteristics++;
				}
				if (commandLine.hasNext())
				{
					passport = commandLine.next();
					characteristics++;
				}
				if (commandLine.hasNext())
				{
					seat = commandLine.next();
					characteristics++;
				}
				if (characteristics == 4){
					try{
						Reservation res = manager.reserveSeatOnFlight(flightNum.toUpperCase(), passengerName, passport.toUpperCase(), seat);
						if (res != null){
							myReservations.add(res);
							res.print();
						}else{
							System.out.println(manager.getErrorMessage());
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
					
				}else{
					System.out.println("Command should look like: RES flight name passport seat.");
				}
			}

			//cancels reservation
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
				
					int index = myReservations.indexOf(new Reservation(flightNum, passengerName, passport));
					if (index >= 0)
					{	
						try{
							manager.cancelReservation(myReservations.get(index));
							myReservations.remove(index);
						}catch(Exception e){
							System.out.print(e.getMessage());
						}
					}
					else
						System.out.println("Reservation on Flight " + flightNum + " Not Found");
				}
			}

			//commands seats on plane
			else if (action.equalsIgnoreCase("SEATS"))
			{
				String flightNum = "";
				String seatType = "";
				
				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
					Flight flight = manager.findFlight(flightNum);
					if (flight != null) {
						flight.printSeats();
					}
				}
				if (commandLine.hasNext())
				{
					seatType = commandLine.next();
					System.out.println(seatType);

					if (manager.seatsAvailable(flightNum, seatType)) // "FCL" or "ECO"
						System.out.println("Seats are available");
					else
						System.out.println("No " + seatType + " Seats Available");
				}
			}

			//prints passengers on flight
			else if(action.equalsIgnoreCase("PASMAN")){
				if(commandLine.hasNext()){
					String flightNum = commandLine.next();
					try{
						manager.printPassengerManifest(flightNum);
					}catch(Exception e){							//If there are any errors that occur doing the code above, catch them
						System.out.println(e.getMessage());							//Print the exception thrown
					}
				}else{
					System.out.println("Not enough variables entered");
				}
			}
			
			//displays your reservations
			else if (action.equalsIgnoreCase("MYRES"))
			{
				for (Reservation myres : myReservations)
					myres.print();
			}


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
			System.out.print("\n>");
		}
	}


}

