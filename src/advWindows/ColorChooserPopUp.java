package advWindows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ColorChooserPopUp extends JFrame implements WindowListener
{
	protected Color color;
	protected JColorChooser colorChooser;
	
	public ColorChooserPopUp(Color init)
	{
		this.setTitle("Double Color Chooser");
		this.addWindowListener(this);
		this.color=init;
		
		this.colorChooser=new JColorChooser(init);
		this.getContentPane().add(colorChooser, BorderLayout.NORTH);
		this.pack();
		this.setVisible(true);
	}
	public volatile boolean active;
	public void waitfor() throws InterruptedException
	{
		while(!active)
		{
			Thread.sleep(1000);
		}
	}
	public Color getColor()
	{
		return this.color;
	}
	public static void main(String args[]) throws Exception
	{
		ColorChooserPopUp cc = new ColorChooserPopUp(Color.BLUE);
		
		cc.waitfor();
		Color c = cc.getColor();
		System.out.println(Integer.toHexString(c.getRGB()));
	}
	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowClosing(WindowEvent e)
	{
		color = colorChooser.getColor();
		active = true;
	}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}
}

