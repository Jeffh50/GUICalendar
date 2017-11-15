package calendar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class CalendarData {

	
	/**
	 * this is the model/subject
	 * @param cal this is the gregorian calendar we will pull information from
	 * @param listeners this holds all the VIEWS. but it will only hold one in this case
	 * @param createdDays this holds a map of all createdDays.
	 */
	
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
	
	
	public HashMap<String, CreatedDay> getDays()
	{
		return createdDays;
	}
	
	/**
	 * @load
	 * this loads a previously created "events.txt" into our current calendar
	 * @precondition There should be an events.txt already there otherwise it wont do anything
	 * @postcondition The calendar now has a filled out arrayList
	 */
	public void load() 
	{
		File f = new File("events.txt");
		
			try
			{
				FileInputStream stream = new FileInputStream(f);
				ObjectInputStream in = new ObjectInputStream(stream);
				createdDays = (HashMap<String, CreatedDay>) in.readObject();
				
				in.close();
				System.out.println("Successfully loaded");
			} 
			catch (Exception e) {
				System.out.println("Load has failed, File does not exist");
			}
			
	}
	
	/**
	 * @quit
	 * quits the program while saving all of the current events into a text file called "events.txt"
	 * @precondition None
	 * @postcondition All current events are saved into "events.txt" and the program quits
	 */
	public void quit()
	{

		File newFile = new File("events.txt");
		newFile.setWritable(true);
		System.out.println(newFile.getAbsolutePath());
		
		try {
			FileOutputStream stream = new FileOutputStream(newFile);
			ObjectOutputStream out = new ObjectOutputStream(stream);
			out.writeObject(createdDays);
			out.flush();
			out.close();
		} catch (Exception e) 
		{
			System.out.println("Creation of new file has failed");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Successfully saved");
		System.exit(0);
	}
}
