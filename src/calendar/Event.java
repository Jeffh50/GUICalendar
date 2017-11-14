package calendar;

import java.io.Serializable;

public class Event implements Comparable<Event>, Serializable {

	String startingTime;
	String endingTime;
	String theTitle;
	
	/**
	 * @Event
	 * Represents a singular event
	 * @param theTitle The title of the event
	 * @param startingTime The starting time of the event in String form
	 * @param endingTime The ending time of the event in String form
	 * @param theYear The year of the event
	 * @param theMonth The month of the event
	 * @param theDay The day of the event
	 */
	Event(String theTitle, String startingTime, String endingTime)
	{
		this.theTitle = theTitle;
		this.startingTime = startingTime;
		this.endingTime = endingTime;
		

	}
	
	/**
	 * @printEvent
	 * Prints this current event 
	 * @precondition The event should exist
	 * @postcondition None
	 */
	public void printEvent()
	{
		System.out.println(startingTime + "-" + endingTime
				+ "  " + theTitle);
	}
	
	/**
	 * @compareTo
	 * Is used to compare this event with another to be able to be sorted
	 * @precondition The object compared to and called on should be be of type Event
	 * @postcondition None
	 */
	public int compareTo(Event otherEvent)
	{
		if(startingTime.compareTo(otherEvent.startingTime) != 0)
		{
			return startingTime.compareTo(otherEvent.startingTime);
		}
		else 
		{
			return endingTime.compareTo(otherEvent.endingTime);
		}
	}
	
	public String getTitle()
	{
		return theTitle;
	}
}
