package calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CreatedDay implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int theYear;
	int theMonth;
	int theDay;

	public ArrayList<Event> eventsForToday;
	
	/**
	 * Represents a singular day with 4 variables
	 * @param theWholeDate A string version of the date in MM/dd/YYYY format. 
	 * @param theYear The year in int form
	 * @param theMonth The month in int form. Keep in mind January starts at "1"
	 * @param theDay The day in int form.
	 */
	CreatedDay(int theYear, int theMonth, int theDay)
	{
		eventsForToday = new ArrayList<Event>();
		this.theYear = theYear;
		this.theMonth = theMonth;
		this.theDay = theDay;
	}
	
	/**
	 * @addEvent
	 * Adds a new event to the TreeMap in this class
	 * @param theTitle The title of the event
	 * @param theEvent An object of Event type
	 * @precondition None
	 * @postcondition eventsForToday gains a new key and value
	 */
	public boolean addEvent(String theTitle, Event theEvent)
	{
		int startingTime = Integer.parseInt(theEvent.startingTime);
		int endingTime = Integer.parseInt(theEvent.startingTime);
		for(Event entry : eventsForToday)
		{
			int otherStartingTime = Integer.parseInt(entry.startingTime);
			int otherEndingTime = Integer.parseInt(entry.endingTime);
			
			if(startingTime == otherStartingTime)
			{
				System.out.println("Starting time is same as another event, cannot add event");
				return false;
			}
			else if(startingTime < otherStartingTime && endingTime > otherStartingTime)
			{
				System.out.println("Another event starts during this one, cannot add event");
				return false;
			}
			else if(startingTime > otherStartingTime && endingTime > otherEndingTime)
			{
				System.out.println("Events starts during another event, cannot add event");
				return false;
			}
		}
		
		eventsForToday.add(theEvent);
		System.out.println("Event Added");
		return true;
		
	}
	
	/**
	 * @printAllEvents
	 * Prints all the events in the TreeMap
	 * @precondition eventsForToday should not be empty
	 * @postcondition None
	 */
	public void printAllEvents()
	{
		for(Event entry : eventsForToday)
		{
			Event theEvent = entry;
			theEvent.printEvent();
		}
	}
	
	public int compareTo(CreatedDay theDay)
	{
		String theString = "" + theYear + theMonth + theDay;
		String otherString = "" + theDay.theYear + theDay.theMonth + theDay.theDay;
		return theString.compareTo(otherString);
	}
	
}
