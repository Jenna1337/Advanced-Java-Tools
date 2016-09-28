package advWindows.components;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public abstract class ActionButton extends JButton
{
	private final ActionThread action = new ActionThread(this, new Thread(){
		public void run(){
			onClick();
		}
	});
	public ActionButton(String label)
	{
		super();
		this.setAction(action);
		this.setText(label);
		this.setToolTipText(label);
	}
	public ActionButton(String label, int width, int height)
	{
		this(label);
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
	public abstract void onClick();
}