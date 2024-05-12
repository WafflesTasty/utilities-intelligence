package waffles.utils.intel.graphs.automata.life;

/**
 * A {@code LifeAutoBound} defines boundary check parameters for a {@code LifeAutomaton}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 */
public interface LifeAutoBound
{
	/**
	 * The {@code Behavior} enum defines different {@code LifeAutoBound} behavior.
	 *
	 * @author Waffles
	 * @since 10 May 2024
	 * @version 1.1
	 */
	public static enum Behavior
	{
		/**
		 * Mortality is flipped if within bounds.
		 */
		STAY_EXTREME,
		/**
		 * Mortality is flipped if outside bounds.
		 */
		STAY_MID;
	}
	
		
	/**
	 * Returns a bounding array for the {@code LifeAutoBound}.
	 * This should contain the minimum and maximum live
	 * neighbor count for the bound to take effect.
	 * 
	 * @return  an integer array
	 */
	public abstract int[] Bounds();

	/**
	 * Returns the behavior of the {@code LifeAutoBound}.
	 * 
	 * @return  a behavior type
	 * 
	 * 
	 * @see Behavior
	 */
	public abstract Behavior Behavior();
}