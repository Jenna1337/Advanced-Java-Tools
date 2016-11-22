package advWindows.progs;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.OverlayLayout;
import advWindows.panels.FileSelector;

public class Installer extends JFrame implements ActionListener
{
	class PanelWelcome extends Panel
	{
		public PanelWelcome(String scname)
		{
			this.add(new Label("Welcome!"));
			// TODO Auto-generated constructor stub
		}
	}
	class PanelSeleDir extends Panel
	{
		private final FileSelector selector;
		
		public PanelSeleDir(String scname, String defDir)
		{
			this.add(new Label("Select a folder to install "+scname+"."));
			selector = new FileSelector(defDir, JFileChooser.DIRECTORIES_ONLY);
			selector.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(e.getSource().equals(selector))
					{
						File selection = selector.getSelectedFile();
						if(selection.exists() && selection.canWrite())
							;
						
						if(!selection.exists())
							;
					}
					System.out.println(e);
				}
			});
			this.add(selector);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void setVisible(boolean b)
		{
			super.setVisible(b);
			if(b)
				btn_next.setEnabled(false);
			else
				btn_next.setEnabled(true);
		}
	}
	class PanelDispPro extends Panel
	{
		public PanelDispPro(String scname)
		{
			this.add(new Label(
					"Installing \"" + scname + "\"... Please wait..."));
			JProgressBar p = new JProgressBar(0, 100);
			p.setStringPainted(true);
			this.add(p);
			
			// TODO Auto-generated constructor stub
		}
		@Override
		public void setVisible(boolean b)
		{
			super.setVisible(b);
			if(b)
			{
				btn_next.setEnabled(false);
				pan_next.setVisible(false);
				btn_inst.setEnabled(true);
				pan_inst.setVisible(true);
			}
			else
			{
				btn_next.setEnabled(true);
				pan_next.setVisible(true);
				btn_inst.setEnabled(false);
				pan_inst.setVisible(false);
			}
		}
	}
	class PanelFinDone extends Panel
	{
		public PanelFinDone(String scname)
		{
			this.add(new Label("Finished installing."));
			
			// TODO Auto-generated constructor stub
		}
		@Override
		public void setVisible(boolean b)
		{
			super.setVisible(b);
			if(b)
			{
				btn_prev.setEnabled(false);
				btn_prev.setVisible(false);
				btn_next.setEnabled(false);
				pan_next.setVisible(false);
				btn_fins.setEnabled(true);
				pan_fins.setVisible(true);
			}
		}
	}
	
	private final Panel pan_overlay, pan_next, pan_inst, pan_fins;
	private final PanelWelcome pan_Welcome;
	private final PanelSeleDir pan_SeleDir;
	private final PanelDispPro pan_DispPro;
	private final PanelFinDone pan_FinDone;
	private volatile boolean installing = false;
	private volatile int sc = 0;
	private final Button btn_next, btn_prev, btn_fins, btn_inst;
	
	/**
	 * 
	 * @param title the title of the window.
	 * @param res the {@link InputStream} of the file to install.
	 * @param defDir the default installation directory.
	 * @param exec the name of the file in {@code res} that the shortcut will
	 *            link to.
	 * @param scname the name of the shortcut to be created.
	 */
	public Installer(String title, InputStream res, File defDir, String exec,
			String scname)
	{
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setTitle(title);
		
		pan_Welcome = new PanelWelcome(scname);
		pan_SeleDir = new PanelSeleDir(scname, defDir.getAbsolutePath());
		pan_DispPro = new PanelDispPro(scname);
		pan_FinDone = new PanelFinDone(scname);
		
		Panel bttns = new Panel(new GridLayout(1, 2));
		
		Panel pan_prev = new Panel(new FlowLayout(FlowLayout.LEFT));
		btn_prev = new Button("Previous");
		pan_prev.add(btn_prev);
		btn_prev.setEnabled(false);
		btn_prev.addActionListener(this);
		
		pan_next = new Panel(new FlowLayout(FlowLayout.RIGHT));
		btn_next = new Button("Next");
		pan_next.add(btn_next);
		btn_next.addActionListener(this);
		
		pan_inst = new Panel(new FlowLayout(FlowLayout.RIGHT));
		btn_inst = new Button("Install");
		btn_inst.setEnabled(false);
		pan_inst.add(btn_inst);
		btn_inst.addActionListener(this);
		
		pan_fins = new Panel(new FlowLayout(FlowLayout.RIGHT));
		btn_fins = new Button("Finish");
		btn_fins.setEnabled(false);
		pan_fins.add(btn_fins);
		btn_fins.addActionListener(this);
		
		bttns.add(pan_prev);
		
		Panel bttns_r = new Panel();
		OverlayLayout overlayout = new OverlayLayout(bttns_r);
		bttns_r.setLayout(overlayout);
		
		bttns_r.add(pan_next);
		bttns_r.add(pan_inst);
		bttns_r.add(pan_fins);
		// bttns_r.getComponent(0).setVisible(false);
		bttns_r.getComponent(1).setVisible(false);
		bttns_r.getComponent(2).setVisible(false);
		bttns.add(bttns_r);
		this.add(bttns, BorderLayout.SOUTH);
		
		pan_overlay = new Panel();
		OverlayLayout layout = new OverlayLayout(pan_overlay);
		pan_overlay.setLayout(layout);
		
		pan_overlay.add(pan_Welcome);
		pan_overlay.add(pan_SeleDir);
		pan_overlay.add(pan_DispPro);
		pan_overlay.add(pan_FinDone);
		
		pan_overlay.getComponent(1).setVisible(false);
		pan_overlay.getComponent(2).setVisible(false);
		pan_overlay.getComponent(3).setVisible(false);
		
		this.add(pan_overlay, BorderLayout.NORTH);
	}
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			if(e.getSource() == btn_next)
			{
				pan_overlay.getComponent(sc).setVisible(false);
				pan_overlay.getComponent(sc += 1).setVisible(true);
			}
			if(e.getSource() == btn_prev)
			{
				pan_overlay.getComponent(sc).setVisible(false);
				pan_overlay.getComponent(sc -= 1).setVisible(true);
			}
			if(e.getSource() == btn_inst)
			{
				// this doesn't disable the button for some reason
				btn_prev.setEnabled(false);
				btn_prev.setVisible(false);
				installing = true;
				
				install();
				
				btn_inst.setEnabled(false);
				pan_inst.setVisible(false);
				btn_next.setEnabled(true);
				pan_next.setVisible(true);
			}
			if(e.getSource() == btn_fins)
			{
				// TODO finish
				
				System.exit(0);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		if(sc > pan_overlay.getComponentCount() - 2)
			btn_next.setEnabled(false);
		else
			btn_next.setEnabled(true);
		
		if(sc < 1 && !installing)
			btn_prev.setEnabled(false);
		else
			btn_prev.setEnabled(true);
		
		this.revalidate();
	}
	private void install()
	{
		// TODO Auto-generated method stub
		
	}
}
