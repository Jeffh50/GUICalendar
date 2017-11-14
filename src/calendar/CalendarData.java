package calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class CalendarData {

	
	//this is the model/subject
	
	
	private GregorianCalendar cal;
	private ArrayList<ChangeListener> listeners;
	private HashMap<String, CreatedDay> createdDays;
	
	CalendarData(GregorianCalendar cal)
	{
		this.cal = cal;
		
		listeners = new ArrayList<ChangeListener>();
		createdDays = new HashMap<String, CreatedDay>();
		
	}
	
	public GregorianCalendar getData()
	{
		return cal;
	}
	
	public void attach(ChangeListener e)
	{
		listeners.add(e);
	}
	
	public void update()
	{

		for(ChangeListener e: listeners)
		{
			e.stateChanged(new ChangeEvent(this));
		}
	}
	
	public void delete()
	{
		
	}
	
	public HashMap<String, CreatedDay> getDays()
	{
		return createdDays;
	}
}
