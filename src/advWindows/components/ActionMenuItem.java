package advWindows.components;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class ActionMenuItem extends JMenuItem implements ActionListener
{
	public ActionMenuItem(String label, Thread thread)
	{
		super();
		this.addActionListener(this);
		
		this.setAction(new ActionThread(this, thread));
		this.setText(label);
		this.setToolTipText(label);
		this.addActionListener(this);
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