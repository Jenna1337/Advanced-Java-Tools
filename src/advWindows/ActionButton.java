package advWindows;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ActionButton extends JButton implements ActionListener
{
	public ActionButton(String label, Thread thread)
	{
		super();
		this.addActionListener(this);
		
		this.setAction(new ActionThread(this, thread));
		this.setText(label);
		this.setToolTipText(label);
		this.addActionListener(this);
	}
	public ActionButton(String label, Thread thread, int width, int height)
	{
		this(label, thread);
		this.setSize(width, height);
	}
	public void setIcon(String filename)
	{
		try
		{
			this.setIcon(new ImageIcon(ImageIO.read(new File(filename))));
		}
		catch(Exception e){}
	}
	public void actionPerformed(ActionEvent arg0){}
}