package advWindows.panels;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.FocusEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.TextEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.UIManager;
import advWindows.AWTEventListenerInterface;
import advWindows.components.ActionThread;
import advWindows.components.EasyButton;
import advWindows.components.Position;
import sys.System;



public class EasyPanel implements AWTEventListenerInterface, Runnable
{
	private static Thread shutdownThread = null;
	private static int instances = 0;
	public JFrame frame;                  // overall window frame
	private JPanel panel;                  // overall drawing surface
	private static final int MAX_SIZE              = 10000;   // max width/height
	private static final boolean DEBUG             = false;
	protected JMenuBar bar = new JMenuBar();
	private ArrayList<JMenu> menubarmenus = new ArrayList<JMenu>();
	public boolean mousedown = false;
	int mouseX,mouseY;
	
	public EasyPanel()
	{
		this(500,500,"Window");
	}
	public EasyPanel(int width, int height)
	{
		this(width, height, "Window");
	}
	public EasyPanel(int width, int height, String TITLE) {
		if (width < 0 || width > MAX_SIZE || height < 0 || height > MAX_SIZE) {
			throw new IllegalArgumentException("Illegal width/height: " + width + " x " + height);
		}
		
		synchronized (getClass()) {
			instances++;
			if (shutdownThread == null) {
				shutdownThread = new Thread(new Runnable() {
					// Runnable implementation; used for shutdown thread.
					public void run() {
						try {
							while (true) {
								// maybe shut down the program, if no more DrawingPanels are onscreen
								// and main has finished executing
								if (instances == 0 && !mainIsActive()) {
									try {
										System.exit(0);
									} catch (SecurityException sex) {}
								}
								
								Thread.sleep(250);
							}
						} catch (Exception e) {}
					}
				});
				shutdownThread.setPriority(Thread.MIN_PRIORITY);
				shutdownThread.start();
			}
		}
		if (DEBUG) System.out.println("w=" + width + ",h=" + height);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
		//panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		//panel.setBackground(backgroundColor);
		
		
		// main window frame
		frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		// listen
		frame.addComponentListener      (this);
		frame.addContainerListener      (this);
		frame.addFocusListener          (this);
		frame.addKeyListener            (this);
		frame.addMouseListener          (this);
		frame.addMouseMotionListener    (this);
		frame.addWindowListener         (this);
		frame.addWindowFocusListener    (this);
		frame.addWindowStateListener    (this);
		//frame.addActionListener         (this);
		//frame.addItemListener           (this);
		//frame.addAdjustmentListener     (this);
		//frame.addTextListener           (this);
		frame.addInputMethodListener    (this);
		frame.addHierarchyListener      (this);
		frame.addHierarchyBoundsListener(this);
		frame.addMouseWheelListener     (this);
		
		updateMenuBar();
		center();
		panel = new JPanel(null);
		LayoutManager overlay = new OverlayLayout(panel);
		pX = new JPanel();
		pX.setLayout(new BoxLayout(pX, BoxLayout.X_AXIS));
		pX.setPreferredSize(null);
		pX.setMinimumSize(null);
		pX.setMaximumSize(null);
		panel.setLayout(overlay);
	}
	private JPanel pX;
	private JPanel pY;
	public void finalizeAndShow()
	{
		pY = new JPanel();
		pX.setBackground(new Color(255,0,0,128));
		pY.setLayout(new BoxLayout(pY, BoxLayout.Y_AXIS));
		pY.setBackground(new Color(0,255,0,128));
		pY.add(pX, BorderLayout.CENTER);
		panel.add(pY, BorderLayout.CENTER);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		toFront(frame);
	}
	public void setIconImage(String filename)
	{
		try
		{
			frame.setIconImage(ImageIO.read(new File(filename)));
		}
		catch(Exception e){}
	}
	/**brings the given window to the front of the z-ordering*/
	private void toFront(final Window window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				if (window != null) {
					window.toFront();
					window.repaint();
				}
			}
		});
	}
	/**moves given jframe to center of screen*/
	private void center() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();
		
		int x = Math.max(0, (screen.width - frame.getWidth()) / 2);
		int y = Math.max(0, (screen.height - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}
	/**Returns whether the 'main' thread is still running.*/
	private static boolean mainIsActive() {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		int activeCount = group.activeCount();
		
		// look for the main thread in the current thread group
		Thread[] threads = new Thread[activeCount];
		group.enumerate(threads);
		for (Thread thread : threads) {
			String name = ("" + thread.getName()).toLowerCase();
			if (name.indexOf("main") >= 0 || 
					name.indexOf("testrunner-assignmentrunner") >= 0) {
				// found main thread!
				// (TestRunnerApplet's main runner also counts as "main" thread)
				return thread.isAlive();
			}
		}
		
		// didn't find a running main thread; guess that main is done running
		return false;
	}
	public void addMenuBarMenu(String menutext, int mnemonic)
	{
		JMenu menu = new JMenu(menutext);
		menu.setMnemonic(mnemonic);
		menubarmenus.add(menu);
		updateMenuBar();
	}
	public void addMenuBarItem(String parentmenu, String itemtext, int mnemonic, Thread thread)
	{
		int mi = this.getmenubarindex(parentmenu);
		JMenu menu;
		if(mi>=0)
			menu = menubarmenus.get(mi);
		else
		{
			throw new NullPointerException();
		}
		JMenuItem item = new JMenuItem();
		item.setAction(new ActionThread(item, thread));
		item.setText(itemtext);
		item.setMnemonic(mnemonic);
		item.addActionListener(this);
		//		item.addKeyListener(this);
		//		item.setAccelerator(KeyStroke.getKeyStroke(accelerator));
		menu.add(item);
		updateMenuBar();
	}
	public void add(EasyButton b)
	{
		JButton button = b.button;
		button.setLayout(null);
		//button.setOpaque(false);
		if(b.position!=null)
		{
			Position position = b.position;
//			button.setLayout(sections2[position.getIndex()].getLayout());
			button.setAlignmentX(position.getLayoutX());
			button.setAlignmentY(position.getLayoutY());
			this.pX.add(button);
//			sections[position.getIndex()].add(button);
		}
		else
		{
			JPanel bpane = new JPanel(null);
			bpane.add(button);
			bpane.setOpaque(false);
			panel.add(bpane);
		}
		panel.updateUI();
		b.button.addActionListener(this);
		frame.update(frame.getGraphics());
	}
	private int getmenubarindex(String item)
	{
		for(int i=0; i<menubarmenus.size(); ++i)
			if(menubarmenus.get(i).getText().equals(item))
				return i;
		return -1;
	}
	public void updateMenuBar()
	{
		bar.removeAll();
		for(int i=0; i<menubarmenus.size(); ++i)
			bar.add(menubarmenus.get(i));
		frame.setJMenuBar(bar);
		frame.paintComponents(frame.getGraphics());
	}
	public void run()
	{
		if (DEBUG) System.out.println("Running shutdown hook");
	}
	//MouseMotionListener
	public void mouseMoved(MouseEvent e)
	{
		mouseX=e.getX();
		mouseY=e.getY();
	}
	public void mouseDragged(MouseEvent e){}
	//MouseListener
	public void mousePressed(MouseEvent e)
	{
		this.mousedown = true;
	}
	public void mouseReleased(MouseEvent e)
	{
		this.mousedown = false;
	}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	//WindowListener
	public void windowClosing(WindowEvent e)
	{
		frame.setVisible(false);
		synchronized (getClass()) {
			instances--;
		}
		frame.dispose();
	}
	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}
	//ActionListener
	public void actionPerformed(ActionEvent e){}
	//ComponentListener
	public void componentHidden(ComponentEvent arg0){}
	public void componentMoved(ComponentEvent arg0){}
	public void componentResized(ComponentEvent arg0){}
	public void componentShown(ComponentEvent arg0){}
	//ContainerListener
	public void componentAdded(ContainerEvent arg0){}
	public void componentRemoved(ContainerEvent arg0){}
	//FocusListener
	public void focusGained(FocusEvent arg0){}
	public void focusLost(FocusEvent arg0){}
	//KeyListener
	public void keyPressed(KeyEvent arg0){}
	public void keyReleased(KeyEvent arg0){}
	public void keyTyped(KeyEvent arg0){}
	//WindowFocusListener
	public void windowGainedFocus(WindowEvent arg0){}
	public void windowLostFocus(WindowEvent arg0){}
	//WindowStateListener
	public void windowStateChanged(WindowEvent arg0){}
	//ItemListener
	public void itemStateChanged(ItemEvent arg0){}
	//AdjustmentListener
	public void adjustmentValueChanged(AdjustmentEvent arg0){}
	//TextListener
	public void textValueChanged(TextEvent e){}
	//InputMethodListener
	public void caretPositionChanged(InputMethodEvent event){}
	public void inputMethodTextChanged(InputMethodEvent event){}
	//HierarchyListener
	public void hierarchyChanged(HierarchyEvent arg0){}
	//HierarchyBoundsListener
	public void ancestorMoved(HierarchyEvent arg0){}
	public void ancestorResized(HierarchyEvent arg0){}
	//MouseWheelListener
	public void mouseWheelMoved(MouseWheelEvent e){}
}
