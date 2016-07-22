package advWindows;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class SliderTextFieldPane extends JPanel
{
	private final JTextField text = new JTextField(5);
	final JSlider slider;
	public SliderTextFieldPane(int min, int max, int init)
	{
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		slider = new JSlider(min, max, init);
		text.setText(""+slider.getValue());
		text.setFont(new Font("Lucida Console", Font.BOLD, 12));
		
		slider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				text.setText(""+slider.getValue());
			}
		});
		text.addKeyListener(new KeyAdapter(){
			@Override
			public void keyReleased(KeyEvent ke) {
				String typed = text.getText();
				if((!typed.matches("\\d+") && typed.lastIndexOf('-')!=0))
					return;
				int value = slider.getValue();
				try{
					value = Integer.parseInt(typed);
				}
				catch(java.lang.NumberFormatException nfe){}
				slider.setValue(value);
				text.setText(""+slider.getValue());
			}
		});
		this.add(text);
		this.add(slider);
	}
	public int getValue()
	{
		return slider.getValue();
	}
	public void addChangeListener(ChangeListener listener)
	{
		this.slider.addChangeListener(listener);
		//this.text.add
	}
}