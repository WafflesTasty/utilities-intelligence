package waffles.utils.intel.graphs.automata.life;

import waffles.utils.intel.graphs.automata.AutoLayout;
import waffles.utils.intel.utilities.Mortality;

/**
 * A {@code LifeAutoLayout} defines the parameters to generate a {@code LifeAutomaton}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 * 
 * 
 * @param <T>  a tile type
 * @see LifeAutoTile
 * @see AutoLayout
 */
public interface LifeAutoLayout<T extends LifeAutoTile> extends AutoLayout<T>
{	
	/**
	 * Creates a {@code LifeAutoBound} for a given {@code Mortality}.
	 * 
	 * @param mrt  a mortality state
	 * @return  a boundary check
	 * 
	 * 
	 * @see LifeAutoBound
	 * @see Mortality
	 */
	public abstract LifeAutoBound Bound(Mortality mrt);
	
	
	@Override
	public default LifeAutoAction create(LifeAutoTile tile)
	{
		return new LifeAutoAction(this, tile);
	}
	
	@Override
	public default int AutoRadius()
	{
		return 1;
	}
}