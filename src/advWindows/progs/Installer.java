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
	private volatile File selected_dir;
	private volatile JProgressBar progress_bar;
	private class PanelWelcome extends Panel
	{
		public PanelWelcome(String scname)
		{
			this.add(new Label("Welcome!"));
			this.add(new Label("Click next to begin intalling "+scname+"."));
		}
	}
	private class PanelSeleDir extends Panel
	{
		private final FileSelector selector;
		
		public PanelSeleDir(String scname, final File defDir)
		{
			this.add(new Label("Select a folder to install "+scname+"."));
			selector = new FileSelector(defDir, JFileChooser.DIRECTORIES_ONLY);
			selector.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(e.getSource().equals(selector))
					{
						selected_dir = selector.getSelectedFile();
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
	private class PanelDispPro extends Panel
	{
		public PanelDispPro(String scname)
		{
			this.add(new Label(
					"Installing \"" + scname + "\"... Please wait..."));
			progress_bar = new JProgressBar(0, 100);
			progress_bar.setStringPainted(true);
			this.add(progress_bar);
			
			// TODO Auto-generated constructor stub
		}
		@Override
		public void setVisible(boolean b)
		{
			super.setVisible(b);
			if(b)
			{
				btn_next.setLabel(lbl_Install);
			}
			else
			{
				btn_next.setLabel(lbl_Next);
			}
		}
	}
	private class PanelFinDone extends Panel
	{
		public PanelFinDone(String scname)
		{
			this.add(new Label("Finished installing "+scname+"."));
			
			// TODO ask to make shortcut
			// TODO ask to launch program
		}
		@Override
		public void setVisible(boolean b)
		{
			super.setVisible(b);
			if(b)
			{
				btn_prev.setEnabled(false);
				btn_prev.setVisible(false);
				btn_next.setLabel("Finish");
			}
			else
				throw new IllegalAccessError("PanelFinDone should be the last panel displayed!");
		}
	}
	private static final String lbl_Next="Next", lbl_Previous="Previous", lbl_Install="Install", lbl_Finish="Finish";
	private final Panel pan_overlay;
	private final PanelWelcome pan_Welcome;
	private final PanelSeleDir pan_SeleDir;
	private final PanelDispPro pan_DispPro;
	private final PanelFinDone pan_FinDone;
	private volatile boolean installing = false;
	private volatile int sc = 0;
	private final Button btn_next, btn_prev;
	
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
		pan_SeleDir = new PanelSeleDir(scname, defDir);
		pan_DispPro = new PanelDispPro(scname);
		pan_FinDone = new PanelFinDone(scname);
		
		Panel bttns = new Panel(new GridLayout(1, 2));
		
		Panel pan_prev = new Panel(new FlowLayout(FlowLayout.LEFT));
		btn_prev = new Button(lbl_Previous);
		pan_prev.add(btn_prev);
		btn_prev.setEnabled(false);
		btn_prev.addActionListener(this);
		
		Panel pan_next = new Panel(new FlowLayout(FlowLayout.RIGHT));
		btn_next = new Button(lbl_Next);
		pan_next.add(btn_next);
		btn_next.addActionListener(this);
		
		bttns.add(pan_prev);
		bttns.add(pan_next);
		
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
			if(e.getSource() == btn_prev)
			{
				pan_overlay.getComponent(sc).setVisible(false);
				pan_overlay.getComponent(sc -= 1).setVisible(true);
			}
			if(e.getSource() == btn_next)
			{
				final String lbl = btn_next.getLabel();
				if(lbl.equals(lbl_Next))
				{
					pan_overlay.getComponent(sc).setVisible(false);
					pan_overlay.getComponent(sc += 1).setVisible(true);
				}
				if(lbl.equals(lbl_Install))
				{
					// this doesn't disable the button for some reason
					btn_prev.setEnabled(false);
					btn_prev.setVisible(false);
					installing = true;
					
					install();
					
					btn_next.setLabel(lbl_Next);
				}
				if(lbl.equals(lbl_Finish))
				{
					// TODO finish
					
					System.exit(0);
				}
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
		if(!selected_dir.exists())
			selected_dir.mkdirs();
		// TODO
		
	}
}
