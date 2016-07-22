package advWindows.components;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import advWindows.panels.DrawingPanel;
import sys.System;

public class KeyButton extends JToggleButton implements KeyListener, MouseListener
{
	private static final long serialVersionUID = 1L;
	public DrawingPanel panel;
	public KeyButton(String label, int key)
	{
		init(label,key);
		this.addKeyListener(this);
		this.setFocusTraversalKeysEnabled(false);
		this.setText("<html>"+this.label+"<br>"+KeyEvent.getKeyText(KeyEvent.getExtendedKeyCodeForChar(key))+"</html>");
		this.addMouseListener(this);
	}
	public void init(String label, int key)
	{
		this.label = label;
		this.key = key;
	}
	private String label;
	private int key;
	public int getKey()
	{
		return this.key;
	}
	public void keyPressed(KeyEvent e)
	{
		if(this.isSelected())
		{
			this.key = e.getKeyCode();
			System.out.println(KeyEvent.getKeyText(e.getKeyCode())+" "+this.key);
			this.setText("<html>"+this.label+"<br>"+KeyEvent.getKeyText(e.getKeyCode())+"</html>");
			this.setSelected(false);
		}
		this.getTopLevelAncestor().addMouseListener(this);
	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	
	public static void main(String[] args)
	{
		JFrame f = new JFrame("kbt");
		JPanel p = new JPanel();
		KeyButton b1 = new KeyButton("lbl", '\u0007');
		KeyButton b2 = new KeyButton("lb2", 'G');
		p.add(b1);
		p.add(b2);
		f.add(p);
		f.pack(); f.setVisible(true);
	}
	boolean over=false, selected=false;
	public void mouseClicked(MouseEvent arg0)
	{
		if(over && !selected)
			selected = true;
		else
		{
			this.setSelected(false);
			selected = false;
		}
	}
	public void mouseEntered(MouseEvent arg0)
	{
		over=true;
	}
	public void mouseExited(MouseEvent arg0)
	{
		//this.setSelected(false);
		//selected = false;
		over=false;
	}
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
}
