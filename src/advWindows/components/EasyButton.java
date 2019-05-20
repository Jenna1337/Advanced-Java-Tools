package advWindows.components;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class EasyButton implements ActionListener
{
	public JButton button;
	public Position position = null;
	public EasyButton(String label, int x, int y, int width, int height, Thread thread, String imagefile)
	{
		this(label, thread, width, height);
		this.setLocation(x, y);
		this.setIcon(imagefile);
	}
	public EasyButton(String label, int x, int y, int width, int height, Thread thread)
	{
		this(label, thread, width, height);
		this.setLocation(x, y);
	}
	public EasyButton(String label, Thread thread, int width, int height)
	{
		this.button =  new JButton();
		button.addActionListener(this);
		
		button.setAction(new ActionThread(button, thread));
		button.setText(label);
		button.setToolTipText(label);
		button.addActionListener(this);
		this.setSize(width, height);
	}
	public EasyButton(String label, Position position, Thread thread)
	{
		this(label, thread, 0, 0);
		this.position = position;
		this.button.setAlignmentX(position.getLayoutX());
		this.button.setAlignmentY(position.getLayoutY());
	}
	public void setIcon(String filename)
	{
		try
		{
			button.setIcon(new ImageIcon(ImageIO.read(new File(filename))));
		}
		catch(Exception e){}
	}
	public void setSize(int width, int height)
	{
		button.setSize(width, height);
	}
	public void setLocation(int x, int y)
	{
		button.setLocation(x, y);
	}
	@SuppressWarnings("unused")
	public void actionPerformed(ActionEvent arg0){}
}
