package calendar;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;

public class CalendarController {

	
	CalendarController()
	{
		GregorianCalendar cal = new GregorianCalendar();
		CalendarData data = new CalendarData(cal);
		CalendarView view = new CalendarView(data);
		data.attach(view);
	}
	
	
}
