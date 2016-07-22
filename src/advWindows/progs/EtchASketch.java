package advWindows.progs;


import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import advWindows.components.KeyButton;
import advWindows.panels.DrawingPanel;
import sys.System;

@SuppressWarnings("unused")
public class EtchASketch extends Component implements KeyListener
{
	private static final long serialVersionUID = 1L;
	public DrawingPanel panel;
	Graphics graphic;
	public EtchASketch(int height, int width)
	{
		this.panel = new DrawingPanel(width, height);
		this.panel.addKeyListener(this);
		this.panel.setFocusTraversalKeysEnabled(false);
		this.addKeyListener(this);
		this.setFocusTraversalKeysEnabled(false);
		this.graphic = panel.getGraphics();
		int windowWidth = this.panel.frame.getWidth();
		int windowHeight=this.panel.frame.getHeight();
		int wmod=this.panel.frame.getWidth() - this.panel.getWidth();
		int hmod=this.panel.frame.getHeight()-this.panel.getHeight();
		
		while(true)
		{
			if(windowWidth!=new Integer(this.panel.frame.getWidth())
			|| windowHeight!=new Integer(this.panel.frame.getHeight()))
			{
				windowWidth = this.panel.frame.getWidth();
				windowHeight=this.panel.frame.getHeight();
				try
				{
					this.panel.setSize(windowWidth-wmod, windowHeight-hmod);//78
				}
				catch(IllegalArgumentException iae)
				{
					this.panel.clear();;
				}
			}
		}
	}
	
	private int x2,y2;
	private int[] keys = {37,38,39,40,32};
	
	private void redefineKeys()
	{
		this.keys.clone();
		KeyGetter kg = new KeyGetter(this,new String[]{}, keys);
		this.keys=kg.getNewKeys();
		kg.close();
	}
	private void draw(int x1, int y1)
	{
		this.graphic = panel.getGraphics();
		this.graphic.drawLine(x1, y1, x2, y2);
		x2=x1;
		y2=y1;
	}
	private int getKey(Keys key)
	{
		return this.keys[key.ordinal()];
	}
	public void keyPressed(KeyEvent e)
	{
		int keyPressed = e.getKeyCode();
		int i = 0;
		for(i=0;i<keys.length;++i)
			if(keys[i] == keyPressed)
				break;
		
		switch(i)
		{
			case 0:
				draw(this.x2-1,
						this.y2);
				break;
			case 1:
				draw(this.x2,
						this.y2-1);
				break;
			case 2:
				draw(this.x2+1,
						this.y2);
				break;
			case 3:
				draw(this.x2,
						this.y2+1);
				break;
			case 4:
				panel.clear();
				break;
		}
		//draw(x,y);
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
	RIGHT,UP,LEFT,DOWN,CLEAR;
}
class KeyGetter extends Component implements AutoCloseable, ActionListener
{
	private static final long serialVersionUID = 1L;
	private int done = 0;
	private int[] vals;
	private JFrame frame = new JFrame("Key Selector");
	private JPanel keypane = new JPanel();
	private KeyButton[] buttons = null;
	private JButton OkButton = new JButton("OK"), CancelButton = new JButton("Cancel");
	KeyGetter(Component parent,String[] labels, int[] vals)
	{
		if(labels.length != vals.length)
		{
			new IllegalArgumentException();
		}
		this.vals = vals;
		this.buttons = new KeyButton[this.vals.length];
		
		for(int i=0;i<this.buttons.length;++i)
		{
			buttons[i] = new KeyButton(labels[i],vals[i]);
			keypane.add(buttons[i]);
		}
		//TODO
		
		OkButton.addActionListener(this);
		CancelButton.addActionListener(this);
		
		keypane.add(OkButton);
		keypane.add(CancelButton);
		
		frame.add(keypane);
		frame.pack();
		frame.setVisible(true);
	}
	public void close()
	{
		this.frame.dispose();
	}
	final Thread me = Thread.currentThread();
	private Thread t = new Thread(new Runnable(){
		public void run()
		{
			me.notify();
		}
	});
	;
	public int[] getNewKeys()
	{
		try
		{
			Thread.currentThread().wait();
			if(this.done==1)
			{
				int[] newvals = new int[vals.length];
				for(int i=0;i<newvals.length;++i)
					newvals[i] = buttons[i].getKey();
				return newvals;
			}
			else
				return vals;
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		System.out.println(cmd);
		if(cmd.equals(OkButton.getActionCommand()))
		{
			this.done = 1;
			this.frame.setVisible(false);
			t.run();
		}
		if(cmd.equals(CancelButton.getActionCommand()))
		{
			this.done = 2;
			this.frame.setVisible(false);
			t.run();
		}
	}
}