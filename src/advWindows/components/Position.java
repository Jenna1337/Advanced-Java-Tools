package advWindows.components;

import java.awt.Component;

public enum Position
{
	TOP_LEFT,    TOP_CENTER,    TOP_RIGHT,
	CENTER_LEFT, CENTER_CENTER, CENTER_RIGHT,
	BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT;
	public float getLayoutX()
	{
		return XPosition.valueOf(this.toString().split("_")[1]).getLayout();
	}
	public float getLayoutY()
	{
		return YPosition.valueOf(this.toString().split("_")[0]).getLayout();
	}
	public int getIndex()
	{
		return this.ordinal();
	}
}
enum XPosition
{
	LEFT, CENTER, RIGHT;
	private float[] layouts = {
			Component.LEFT_ALIGNMENT,
			Component.CENTER_ALIGNMENT,
			Component.RIGHT_ALIGNMENT};
	public float getLayout()
	{
		return layouts[this.ordinal()];
	}
}
enum YPosition
{
	TOP, CENTER, BOTTOM;
	private float[] layouts = {
			Component.TOP_ALIGNMENT,
			Component.CENTER_ALIGNMENT,
			Component.BOTTOM_ALIGNMENT,
			};
	public float getLayout()
	{
		return layouts[this.ordinal()];
	}
}