package advWindows.progs;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Field;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import advWindows.components.KeyButton;
import advWindows.panels.DrawingPanel;

@SuppressWarnings("unused")
public class EtchASketch extends Component implements KeyListener
{
	private static final long serialVersionUID = 1L;
	Graphics graphic;
	DrawingPanel panel;
	
	public EtchASketch(int height, int width)
	{
		this.panel = new DrawingPanel(width, height);
		this.panel.addKeyListener(this);
		this.panel.setFocusTraversalKeysEnabled(false);
		this.panel.addKeyListener(this);
		this.panel.setFocusTraversalKeysEnabled(false);
		this.graphic = this.getGraphics();
		
		Thread t = new Thread(() ->
		{
			int windowWidth = this.panel.frame.getWidth();
			int windowHeight = this.panel.frame.getHeight();
			int wmod = this.panel.frame.getWidth() - this.getWidth();
			int hmod = this.panel.frame.getHeight() - this.getHeight();
			while(true)
			{
				if(windowWidth != new Integer(this.panel.frame.getWidth())
						|| windowHeight != new Integer(this.panel.frame.getHeight()))
				{
					windowWidth = this.panel.frame.getWidth();
					windowHeight = this.panel.frame.getHeight();
					try
					{
						this.setSize(windowWidth - wmod, windowHeight - hmod);// 78
					}
					catch(IllegalArgumentException iae)
					{
						this.panel.clear();
					}
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	private int x2, y2;
	private Keys[] keys = Keys.values();
	private void draw(int x1, int y1)
	{
		this.graphic = panel.getGraphics();
		this.graphic.drawLine(x1, y1, x2, y2);
		x2 = x1;
		y2 = y1;
	}
	public void keyPressed(KeyEvent e)
	{
		int keyPressed = e.getKeyCode();
		int i = 0;
		for(i = 0; i < keys.length; ++i)
			if(keys[i].getKey() == keyPressed)
				break;
			
		switch(keys[i])
		{
			case LEFT:
				draw(this.x2 - 1, this.y2);
				break;
			case DOWN:
				draw(this.x2, this.y2 - 1);
				break;
			case RIGHT:
				draw(this.x2 + 1, this.y2);
				break;
			case UP:
				draw(this.x2, this.y2 + 1);
				break;
			case CLEAR:
				panel.clear();
				break;
			default:
				break;
		}
		// draw(x,y);
	}
	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e)
	{
	}
}

enum Keys
{
	RIGHT(39), UP(40), LEFT(37), DOWN(38), CLEAR(32);
	private int key;
	private Keys(int key){
		this.key=key;
	}
	public int getKey()
	{
		return key;
	}
	public void setKey(int key)
	{
		this.key = key;
	}
}
