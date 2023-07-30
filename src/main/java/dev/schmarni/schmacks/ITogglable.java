package dev.schmarni.schmacks;

/**
 * ITogglable
 */
public interface ITogglable {
	public boolean get_active();

	public void enable();

	public void disable();

	public default void set_active(boolean value) {
		if (value) {
			this.enable();
		} else {
			this.disable();
		}
	}
}
