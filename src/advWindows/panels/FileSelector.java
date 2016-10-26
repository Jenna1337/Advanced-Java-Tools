package advWindows.panels;
import java.awt.Component;
import java.awt.Panel;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import advWindows.components.ActionButton;

public class FileSelector extends Panel
{
	private final JFileChooser chooser = new JFileChooser();
	private final JButton button;
	private final FileSelector THIS = this;
	private final JTextField field;
	private volatile String pathname;
	
	/**
	 * 
	 * @param mode the type of files to be displayed:
	 * <ul>
	 * <li>JFileChooser.FILES_ONLY</li>
	 * <li>JFileChooser.DIRECTORIES_ONLY</li>
	 * <li>JFileChooser.FILES_AND_DIRECTORIES</li>
	 * </ul> 
	 */
	public FileSelector(String def, int mode)
	{
		chooser.setFileSelectionMode(mode);
		field = new JTextField(def);
		button = new ActionButton()
		{
			public void onClick()
			{
				chooser.showOpenDialog(THIS);
				pathname = chooser.getSelectedFile().getAbsolutePath();
			}
		};
		this.add(field);
		this.add(button);
	}
	public void setFileChooserVariable(String varname, Object val)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException
	{
		JFileChooser.class.getDeclaredField(varname).set(chooser, val);
	}
	public Object getFileChooserVariable(String varname)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		return JFileChooser.class.getDeclaredField(varname).get(chooser);
	}
	public File getSelectedFile()
	{
		return new File(pathname);
	}
}
