package advWindows.progs;

import advWindows.components.EasyButton;
import advWindows.panels.EasyPanel;




public class EasyPanelTest
{
	
	public static void test()
	{
		final EasyPanel p = new EasyPanel(500, 500, "Test");
		p.frame.setResizable(true);
		p.addMenuBarMenu("Test", 't');
		p.addMenuBarItem("Test", "item", 'i', 
				new Thread(new Runnable()
				{
					public void run()
					{
						System.out.println("test");
					}
				}
						));
		p.addMenuBarItem("Test", "item2", 't', 
				new Thread(new Runnable()
				{
					public void run()
					{
						System.out.println("test2");
					}
				}
						));
		p.addMenuBarMenu("Test2", 'e');
		p.addMenuBarItem("Test2", "item", 'i', 
				new Thread(new Runnable()
				{
					public void run()
					{
						System.out.println("test3");
					}
				}
						));
		EasyButton b = new EasyButton("Button1", advWindows.components.Position.BOTTOM_CENTER,
				new Thread(new Runnable()
				{
					public void run()
					{
						System.out.println("button");
					}
				}
						));
		p.add(new EasyButton("Button2", 300, 50, 100, 320, 
				new Thread(new Runnable()
				{
					public void run()
					{
						p.frame.setResizable(true);
						System.out.println("button2");
					}
				}
						)));
		b.setIcon("test2.png");
		p.add(new EasyButton("Button3", advWindows.components.Position.BOTTOM_RIGHT,
				new Thread(new Runnable()
				{
					public void run()
					{
						System.out.println("button3");
					}
				}
						)));
		p.add(b);
		p.setIconImage("test4.png");
		p.finalizeAndShow();
	}
}
