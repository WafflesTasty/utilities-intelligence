package waffles.utils.intel.graphs.life;

import waffles.utils.intel.utilities.Mortality;

/**
 * A {@code LifeAutoLayout} defines the parameters for a {@code LifeAutomaton}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 */
public interface LifeAutoLayout
{	
	/**
	 * Creates a {@code LifeAutoBound} for a given {@code Mortality}.
	 * 
	 * @param mrt  a mortality state
	 * @return  a boundary checker
	 */
	public abstract LifeAutoBound Bound(Mortality mrt);
	
	/**
	 * Returns the beat time of the {@code LifeAutomaton}.
	 * 
	 * @return  a beat time
	 */
	public abstract long Beat();
}