package advWindows.panels;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import advWindows.components.ActionButton;
import advWindows.components.DefaultIcons;

public class FileSelector extends JPanel
{
	public static final String SELECTION_CHANGED = "SelectionChanged";
	private final JTextField field;
	private final JButton button;
	private final JFileChooser chooser = new JFileChooser();
	private final ActionListener CHILD_LISTENER = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource().equals(field))
			{
				selection = new File(field.getText());
				chooser.setSelectedFile(selection);
				fireActionPerformed();
			}
			if(e.getSource().equals(chooser) && e.getActionCommand()
					.equals(JFileChooser.APPROVE_SELECTION))
			{
				selection = chooser.getSelectedFile();
				field.setText(selection.getAbsolutePath());
				fireActionPerformed();
			}
		}
	};
	private volatile File selection;
	
	/**
	 * 
	 * @param mode the type of files to be displayed:
	 *            <ul>
	 *            <li>JFileChooser.FILES_ONLY</li>
	 *            <li>JFileChooser.DIRECTORIES_ONLY</li>
	 *            <li>JFileChooser.FILES_AND_DIRECTORIES</li>
	 *            </ul>
	 */
	public FileSelector(String def, int mode)
	{
		chooser.setFileSelectionMode(mode);
		field = new JTextField(def);
		button = new ActionButton(DefaultIcons.general_Open.getIcon16())
		{
			public void onClick()
			{
				chooser.showOpenDialog(FileSelector.this);
				selection = chooser.getSelectedFile();
			}
		};
		field.addActionListener(CHILD_LISTENER);
		field.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent e)
			{
				CHILD_LISTENER.actionPerformed(new ActionEvent(e.getSource(),
						e.getID(), KeyEvent.getKeyText(e.getKeyCode()),
						e.getWhen(), e.getModifiers()));
			}
			public void keyPressed(KeyEvent e)
			{
			}
			public void keyReleased(KeyEvent e)
			{
			}
			
		});
		button.addActionListener(CHILD_LISTENER);
		chooser.addActionListener(CHILD_LISTENER);
		this.add(field);
		this.add(button);
	}
	public void setFileChooserVariable(String varname, Object val)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException
	{
		JFileChooser.class.getDeclaredField(varname).set(chooser, val);
	}
	public Object getFileChooserVariable(String varname)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException
	{
		return JFileChooser.class.getDeclaredField(varname).get(chooser);
	}
	public File getSelectedFile()
	{
		return selection;
	}
	/**
	 * Adds an <code>ActionListener</code> to the file selector.
	 * 
	 * @param l the <code>ActionListener</code> to be added
	 */
	public void addActionListener(ActionListener l)
	{
		listenerList.add(ActionListener.class, l);
	}
	private void fireActionPerformed()
	{
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		AWTEvent currentEvent = EventQueue.getCurrentEvent();
		int modifiers = 0;
		if(currentEvent instanceof ActionEvent)
			modifiers = ((ActionEvent) currentEvent).getModifiers();
		ActionEvent e = new ActionEvent(FileSelector.this,
				ActionEvent.ACTION_PERFORMED, SELECTION_CHANGED,
				EventQueue.getMostRecentEventTime(), modifiers);
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for(int i = listeners.length - 2; i >= 0; i -= 2)
			if(listeners[i] == ActionListener.class)
				((ActionListener) listeners[i + 1]).actionPerformed(e);
	}
}
