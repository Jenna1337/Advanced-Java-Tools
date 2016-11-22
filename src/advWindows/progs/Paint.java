package advWindows.progs;


import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import advWindows.panels.DrawingPanel;

public class Paint extends Component implements MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	public DrawingPanel panel;
	Graphics graphic;
	private boolean mousedown = false;
	public Paint(int height, int width)
	{
		this.panel = new DrawingPanel(width, height);
		this.panel.addMouseListener(this);
		this.addMouseListener(this);
		this.panel.addMouseMotionListener(this);
		this.addMouseMotionListener(this);
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
					this.panel.clear();
				}
			}
		}
	}
	public void mousePressed(MouseEvent e)
	{
		this.mousedown = true;
		this.draw(e);
	}
	public void mouseReleased(MouseEvent e)
	{
		this.mousedown = false;
		this.draw(e);
	}
	public void mouseMoved(MouseEvent e)
	{
		if(this.mousedown)
			this.draw(e);
		x2=e.getX();
		y2=e.getY();
	}
	public void mouseDragged(MouseEvent e)
	{
		if(this.mousedown)
			this.draw(e);
	}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
	int x2,y2;
	public void draw(MouseEvent e)
	{
		this.graphic = panel.getGraphics();
		int x1=e.getX(), y1=e.getY();
		this.graphic.drawLine(x1, y1, x2, y2);
		x2=x1;
		y2=y1;
	}
	
	
	
	
	public static void colorTest()
	{
		int sizemultiplyer = 1, colorchangespeedmulti=1;
		int boxsize=sizemultiplyer, x=0, y=0-boxsize, color=0, colormax=16777215;
		DrawingPanel panel = new DrawingPanel(256*sizemultiplyer, 255*sizemultiplyer);
		Graphics g = panel.getGraphics();
		
		for(;;)
		{
			while(x+boxsize<=panel.getWidth())
			{
				while(y+boxsize<=panel.getHeight())
				{
					g.setColor(Color.decode("#"+base(color+=1,10,16,false)));
					g.fillRect(x, y+=boxsize, boxsize, boxsize);
					if(color>colormax)
						color=0;
				}
				x+=boxsize;
				y=0-boxsize;
			}
			x=0;
			y=0-boxsize;
			color+=65536*(colorchangespeedmulti-1);
		}
	}
	public static String base(Object in, int from, int to, boolean up)
	{
		String out = Integer.toString(Integer.valueOf(in.toString(), from), to);
		if(up)
			out = out.toUpperCase();
		return out;
	}
	@Deprecated
	public static String base(Object in, int from, int to)
	{
		return base(in, from, to, true);
	}
}