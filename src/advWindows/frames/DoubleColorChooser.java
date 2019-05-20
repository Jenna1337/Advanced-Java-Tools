package advWindows.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import advWindows.AdvCaret;

@SuppressWarnings({"serial","unused"})
public class DoubleColorChooser extends JFrame implements WindowListener, ChangeListener
{
	protected Color fgColor;
	protected Color bgColor;
	protected JColorChooser fgColorChooser;
	protected JColorChooser bgColorChooser;
	protected JTabbedPane tabpane = new JTabbedPane();
	protected JTextArea testarea = new JTextArea(8, 32);
	
	public DoubleColorChooser(Color fgColor, Color bgColor)
	{
		this.setTitle("Double Color Chooser");
		this.addWindowListener(this);
		this.fgColor=fgColor;
		this.bgColor=bgColor;
		
		this.fgColorChooser=new JColorChooser(fgColor);
		this.fgColorChooser.getSelectionModel().addChangeListener(this);
		this.bgColorChooser=new JColorChooser(bgColor);
		this.bgColorChooser.getSelectionModel().addChangeListener(this);
		
		this.tabpane.addTab("Foreground Color", fgColorChooser);
		this.tabpane.addTab("Background Color", bgColorChooser);
		
		this.testarea.setCaret(new AdvCaret(bgColor));
		this.getContentPane().add(new JScrollPane(testarea), BorderLayout.SOUTH);
		this.getContentPane().add(tabpane, BorderLayout.NORTH);
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
	public Color[] getColors()
	{
		return new Color[]{this.fgColor, this.bgColor};
	}
	public static void main(String args[]) throws Exception
	{
		DoubleColorChooser c = new DoubleColorChooser(Color.RED, Color.BLUE);
		
		c.waitfor();
		Color[] grounds = c.getColors();
		System.out.println(Integer.toHexString(grounds[0].getRGB()) +"\n"+ Integer.toHexString(grounds[1].getRGB()));
	}
	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowClosing(WindowEvent e)
	{
		active = true;
	}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}
	public void stateChanged(ChangeEvent arg0)
	{
		testarea.setForeground(fgColor = fgColorChooser.getColor());
		testarea.setBackground(bgColor = bgColorChooser.getColor());
	}
}

