package advWindows.components;

import javax.swing.ImageIcon;

public enum DefaultIcons
{
	//development TODO
	//general TODO
	general_About,
	general_Add,
	general_AlignBottom,
	general_AlignCenter,
	general_Copy,
	general_Cut,
	general_Delete,
	general_Edit,
	general_New,
	general_Open,
	general_Save,
	general_SaveAll,
	general_SaveAs,
	//media
	media_FastForward,
	media_Movie,
	media_Pause,
	media_Play,
	media_Rewind,
	media_StepBack,
	media_StepForward,
	media_Stop,
	media_Volume,
	//navigation TODO
	//table TODO
	//text TODO
	;
	private final ImageIcon icon16;
	private final ImageIcon icon24;
	
	private DefaultIcons()
	{
		String basepath = "defaultIcons/" + this.name().replaceFirst("_", "/");
		icon16 = getIcon(basepath + "16.gif");
		icon24 = getIcon(basepath + "24.gif");
	}
	
	private static ImageIcon getIcon(String name)
	{
		try
		{
			return new ImageIcon(javax.imageio.ImageIO
					.read(ClassLoader.getSystemResourceAsStream(name)));
		}
		catch(Exception e)
		{
			throw new InternalError(e);
		}
	}
	public ImageIcon getIcon16()
	{
		return icon16;
	}
	public ImageIcon getIcon24()
	{
		return icon24;
	}
}
