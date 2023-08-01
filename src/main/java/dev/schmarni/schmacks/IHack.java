package dev.schmarni.schmacks;

/**
 * IHack
 */
public interface IHack extends IScreenOption {
	/**
	 * is called 4 times a Second
	 */
	void slow_tick(int quater);

	/**
	 * is called every tick
	 */
	void tick();

	default void init() {};
}
