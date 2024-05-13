package waffles.utils.intel.graphs.automata.life;

import waffles.utils.geom.spaces.index.tiles.Tiled2D;
import waffles.utils.geom.utilities.constants.Cardinal2D;
import waffles.utils.intel.graphs.automata.AutoTile;
import waffles.utils.intel.utilities.Mortality;

/**
 * A {@code LifeAutoTile} defines a single {@code LifeAutomaton} tile.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 *
 * 
 * @see AutoTile
 * @see Tiled2D
 */
public interface LifeAutoTile extends AutoTile, Tiled2D
{
	/**
	 * Returns the mortality of the {@code LifeAutoTile}.
	 * 
	 * @return  a mortality state
	 * 
	 * 
	 * @see Mortality
	 */
	public abstract Mortality Mortality();
	
	/**
	 * Changes the mortality of the {@code LifeAutoTile}.
	 * 
	 * @param mrt  a mortality state
	 * 
	 * 
	 * @see Mortality
	 */
	public abstract void setMortality(Mortality mrt);
	
	
	@Override
	public default LifeAutoTile Neighbor(Cardinal2D c)
	{
		return (LifeAutoTile) Tiled2D.super.Neighbor(c);
	}
}