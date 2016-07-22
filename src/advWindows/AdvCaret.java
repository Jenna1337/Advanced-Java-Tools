package advWindows;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

@SuppressWarnings("serial")
public class AdvCaret extends DefaultCaret
{
	protected Color bgColor = Color.RED;
	
	public AdvCaret(Color bgColor)
	{
		this(bgColor, 1000, USE_CHAR_HEIGHT, USE_CHAR_WIDTH);
	}
	public AdvCaret(Color bgColor, int blinkRate)
	{
		this(bgColor, blinkRate, USE_CHAR_HEIGHT, USE_CHAR_WIDTH);
	}
	public AdvCaret(Color bgColor, int blinkRate, int height, int width)
	{
		this.bgColor=bgColor;
		this.setBlinkRate(blinkRate);
		this.cheight = height;
		this.cwidth = width;
		if(height==USE_CHAR_HEIGHT)
			this.UseNextCharHeight=true;
		if(width==USE_CHAR_WIDTH)
			this.UseNextCharWidth=true;
		if(width==MONOSPACE_WIDTH)
			this.MonospaceWidth = true;
	}
	protected synchronized void damage(Rectangle r)
	{
		if (r == null)
			return;
		
		// give values to x,y,width,height (inherited from java.awt.Rectangle)
		x = r.x;
		y = r.y;
		height = r.height;
		// A value for width was probably set by paint(), which we leave alone.
		// But the first call to damage() precedes the first call to paint(), so
		// in this case we must be prepared to set a valid width, or else
		// paint()
		// will receive a bogus clip area and caret will not get drawn properly.
		if (width <= 0)
			width = getComponent().getWidth();
		
		repaint(); // calls getComponent().repaint(x, y, width, height)
	}
	public void paint(Graphics g)
	{
		JTextComponent comp = getComponent();
		if (comp == null)
			return;
		
		int dot = getDot();
		Rectangle r = null;
		char dotChar;
		try
		{
			r = comp.modelToView(dot);
			if (r == null)
				return;
			dotChar = comp.getText(dot, 1).charAt(0);
		} catch (BadLocationException e) {
			return;
		}
		
		if ((x != r.x) || (y != r.y))
		{
			// paint() has been called directly, without a previous call to
			// damage(), so do some cleanup. (This happens, for example, when
			// the
			// text component is resized.)
			repaint(); // erase previous location of caret
			x = r.x; // Update dimensions (width gets set later in this method)
			y = r.y;
			height = r.height;
			//width = width;
		}
		
		//g.setColor(new Color(CaretColor.getRGB()));
		g.setXORMode(this.bgColor);
		
		if(isVisible())
		{
			if(UseNextCharHeight)
				cheight = g.getFontMetrics().getHeight();
			if(UseNextCharWidth)
				cwidth = g.getFontMetrics().charWidth(dotChar);
			if(MonospaceWidth)
				cwidth = g.getFontMetrics().charWidth('_');
			g.fillRect(x, y, cwidth, cheight);
		}
	}
	public int cwidth, cheight;
	public boolean UseNextCharWidth = false;
	public boolean UseNextCharHeight = false;
	public boolean MonospaceWidth = false;
	public static int USE_CHAR_WIDTH = -592469;
	public static int USE_CHAR_HEIGHT= -195379;
	public static int MONOSPACE_WIDTH= -968019;
	
	public int getCaretHeight()
	{
		return height;
	}
	public void setCaretHeight(int height)
	{
		this.height = height;
	}
	public int getCaretWidth()
	{
		return width;
	}
	public void setCaretWidth(int width)
	{
		this.width = width;
	}
	public void setBGColor(Color bgColor)
	{
		this.bgColor = bgColor;
	}
	
	public static void main(String args[])
	{
		JFrame frame = new JFrame("FancyCaret demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextArea area = new JTextArea(8, 32);
		area.setBackground(Color.MAGENTA);
		area.setForeground(Color.ORANGE);
		
		Caret c = new AdvCaret(area.getBackground(), 500, AdvCaret.USE_CHAR_HEIGHT, AdvCaret.USE_CHAR_WIDTH);
		
		
		area.setCaret(c);
		area.setText("VI\tVirgin Islands \nVA      Virginia\nVT\tVermont");
		frame.getContentPane().add(new JScrollPane(area), BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
}




