package calendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;


/**
* This is the view part. Starts by making everything
* @param theData this is the model class
* @param cal this is the Gregorian calendar that we will pull info from
* @param monthYear this is simply just the month and year in MM/YYYY format
* @param dayViewDate is the day and the month in DD/MM format
* @param selectedDay this is the day we are selecting
*/

public class CalendarView extends JFrame implements ChangeListener{

	//this is the view/observer
	
	public CalendarData theData;
	public GregorianCalendar cal;
		
	private JTextArea monthYear;
	private JTextArea dayViewDate;
	private JTextArea eventDisplay;
	private CreatedDay selectedDay;
	
	CalendarView(CalendarData theData)
	{
		this.theData = theData;
		cal = theData.getData();
		System.out.println("making everything");
		
		//these next lines have to do with monthViewPanel
		
		//these few lines lines gets us the number of rows in this month, we need to number to make the grid layout of dayViewPanel
		
		//finds first day of month in number form
		
		JPanel monthViewPanel = new JPanel();
		
		populateMonthView(monthViewPanel);
		
		
		//making the center panel will hold the month view and the create button
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(monthViewPanel, BorderLayout.CENTER);
		JButton createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							//go to event that makes the box for us
							createEventBox();
						}
	
					}
					
			);
		centerPanel.add(createButton,BorderLayout.NORTH);
		
		monthYear = new JTextArea("Month:" + (cal.get(Calendar.MONTH) + 1) + "  Year:" + cal.get(Calendar.YEAR));

		//monthYear.addChangeListener(monthYearListener);
		monthYear.setEditable(false);
		centerPanel.add(monthYear, BorderLayout.SOUTH);
		
		
		//making the left panel this will hold the day view panel and the date at the top
		JPanel leftSidePanel = new JPanel();
		leftSidePanel.setLayout(new BorderLayout());
		dayViewDate = new JTextArea("DD/MM: " + cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH)+1));
		dayViewDate.setEditable(false);
		leftSidePanel.add(dayViewDate, BorderLayout.NORTH);
		
		eventDisplay = new JTextArea();
		eventDisplay.setPreferredSize(new Dimension(200, 200));
		eventDisplay.setEditable(false);
		JScrollPane eventScroll = new JScrollPane(eventDisplay);
		eventScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		leftSidePanel.add(eventScroll, BorderLayout.CENTER);
		//still have to make dayViewDate import events somehow
		
		
		//Making the top panel, this will hold the two arrows
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		JButton leftButton = new JButton("<");
		leftButton.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							//gotta update everything everything i press this button
							
							//checking in advance if we are already at day 1
							boolean flag = false;
							if(cal.get(Calendar.DAY_OF_MONTH) == 1)
							{
								flag = true;
							}
							cal.add(Calendar.DATE, -1);
							theData.update();
							monthYear.setText("Month:" + (cal.get(Calendar.MONTH) + 1) + "  Year:" + cal.get(Calendar.YEAR));;
							dayViewDate.setText("DD/MM: " + cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH)+1));;
							
							
							//changing selected day
							String theKey = "" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH);
							
							
							if(theData.getDays().containsKey(theKey) == false)
							{
								selectedDay = new CreatedDay(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
								theData.getDays().put(theKey, selectedDay);
							}
							else //the day is already in there thus we will just retrieve it
							{
								selectedDay = theData.getDays().get(theKey);
							}
							//here we populate the eventDisplay with the events in the day
							eventDisplay.setText("");
							eventDisplay.setText(selectedDay.printAllEvents());
							
							//this checks if we are at the first day 
							if(flag == true)
							{
								populateMonthView(monthViewPanel);
								flag = false;
							}
						}
					}
			);
		rightPanel.add(leftButton, BorderLayout.WEST);
		JButton rightButton = new JButton(">");
		rightButton.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							
							//gotta update everything everything i press this button
							cal.add(Calendar.DATE, 1);
							theData.update();
							monthYear.setText("Month:" + (cal.get(Calendar.MONTH) + 1) + "  Year:" + cal.get(Calendar.YEAR));;
							dayViewDate.setText("DD/MM: " + cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH)+1));;
							
							//checking we we moved up a month
							if(cal.get(Calendar.DAY_OF_MONTH) == 1)
							{
								populateMonthView(monthViewPanel);
							}
							
							
							String theKey = "" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH);
							
							
							if(theData.getDays().containsKey(theKey) == false)
							{
								selectedDay = new CreatedDay(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
								theData.getDays().put(theKey, selectedDay);
							}
							else //the day is already in there thus we will just retrieve it
							{

								selectedDay = theData.getDays().get(theKey);
							}
							//here we populate the eventDisplay with the events in the day
							eventDisplay.setText("");
							eventDisplay.setText(selectedDay.printAllEvents());
							
						}
					}
			);
		rightPanel.add(rightButton,BorderLayout.EAST);
		//making quit button work
		JButton quitButton = new JButton("QUIT");
		quitButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				theData.quit();
			}
		});
		rightPanel.add(quitButton,BorderLayout.SOUTH);
	
		
		//this makes our current day our first selected day. No need for checking if alreayd here because it is not
		selectedDay = new CreatedDay(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		String theKey = "" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH); 
		theData.getDays().put(theKey, selectedDay);
		
		//setting the base of this to FlowLayout
		this.setLayout(new FlowLayout());
		this.setSize(1000,300);

		//adding everything to this big frame
		this.add(leftSidePanel);
		this.add(centerPanel);
		this.add(rightPanel);

		//this because this is a Jframe, we gotta do the same thing with settingthe same 3 stuff
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		this.setVisible(true);
		
	}

	/**
	 *@populateMonthView
	 * populates the month view with buttons that updates everything
	 * @param monthViewPanel this panel will get filled with buttons
	 * @precondition monthViewPanel exists
	 * @postcondition monthViewPanel is now filled with buttons
	 * **/
	private void populateMonthView(JPanel monthViewPanel)
	{
		//deletes everything here first because this will get called again
		monthViewPanel.removeAll();
		
		//this is for finding the first day of the week in number form
		int theDay = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.set(Calendar.DAY_OF_MONTH, theDay);
		
		//rows can be any number of rows, while columns are forced to 7. 
		monthViewPanel.setLayout(new GridLayout(0, 7));
		
		
		//the first few days have to be empty since it doesn't belong in the month
		for (int j = 0; j < firstDayOfWeek - 1; j++)
		{
			monthViewPanel.add(new JButton());

		}
		//now we make then numebrs for the days and add buttons to it
		for( int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
		{
			int j = i;
			JButton b = new JButton(Integer.toString(i));
			b.addActionListener(new ActionListener()
					{
						final int k = j;
						public void actionPerformed(ActionEvent e)
						{
							if(k == cal.get(Calendar.DAY_OF_MONTH))
							{
								//do nothing
							
							}
							else //all this does is save ourselves from extra work from pushing the button when its already on that date
							{
								cal.set(Calendar.DAY_OF_MONTH, k); //this makes the button turn the cal to this date.
								theData.update();
								monthYear.setText("Month:" + (cal.get(Calendar.MONTH) + 1) + "  Year:" + cal.get(Calendar.YEAR));;
								dayViewDate.setText("DD/MM: " + cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH)+1));;
								
								
								//setting the day
								String theKey = "" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH);
								if(theData.getDays().containsKey(theKey) == false) //if the hashmap DOESNT have the item, add it.
								{
						
									selectedDay = new CreatedDay(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
									theData.getDays().put(theKey, selectedDay);
								}
								else //the day is already in there thus we will just retrieve it
								{
						
									selectedDay = theData.getDays().get(theKey);
								}
								
								//here we populate the eventDisplay with the events in the day
								eventDisplay.setText("");
								eventDisplay.setText(selectedDay.printAllEvents());
							}
							
						}
					}
			);
			monthViewPanel.add(b);
		}
	}
	
	/**
	 *@stateChanged
	 * this is what is called from the model that it has been changed
	 * @param e this is the changeevent
	 * @precondition None
	 * @postcondition the gregorian calendar is now updated
	 * **/
	public void stateChanged(ChangeEvent e) {
		cal = theData.getData();
		this.repaint(); //we call repaint because this is a Jframe which can do that
		
	}

	/**
	 *@createEventBox
	 * Creates the create an event box frame.
	 * @precondition None
	 * @postcondition potentiall a new event is added to the hashmap in the model
	 * **/
	private void createEventBox()
	{
	
		JFrame EventBox = new JFrame();
		EventBox.setLayout(new FlowLayout());
		
		JTextField eventTitle = new JTextField("Untitled Event");
		eventTitle.setSize(50, 50);
		EventBox.add(eventTitle); //you type the event name here
		
		JTextField startingTime = new JTextField("Starting Time: HHMM format");
		startingTime.setSize(50, 50);
		EventBox.add(startingTime); //typing starting time here
		
		JTextField endingTime = new JTextField("Ending time: HHMM format");
		endingTime.setSize(50, 50);
		EventBox.add(endingTime); //type ending time here
		
		
		//for the add button
		
		JButton addButton = new JButton("ADD");
		addButton.addActionListener(new ActionListener() {
			
			//this has to go in here, because when you press the button, it has to make the event then and there
			
			public void actionPerformed(ActionEvent e) {
				final Event theEvent = new Event(eventTitle.getText(), startingTime.getText(), endingTime.getText());
				if(selectedDay.addEvent(theEvent.getTitle(), theEvent) == true)
				{
					eventDisplay.setText(selectedDay.printAllEvents());
					EventBox.dispose();
				}
			}
			
		});
		EventBox.add(addButton);
		
		//for the quit button
		JButton quitButton = new JButton("QUIT");
		quitButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				EventBox.dispose();
			}
			
		});
		EventBox.add(quitButton);
		
		EventBox.pack();
		EventBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		EventBox.setVisible(true);
	}
	
	
}
