package waffles.utils.intel.grids.auto.fire;

import waffles.utils.geom.spaces.index.tiles.Tiled2D;
import waffles.utils.geom.utilities.constants.Cardinal2D;
import waffles.utils.intel.grids.auto.AutoTile;
import waffles.utils.intel.utilities.Mortality;

/**
 * A {@code FireAutoTile} defines a single {@code FireAutomaton} tile.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 *
 * 
 * @see AutoTile
 * @see Tiled2D
 */
public interface FireAutoTile extends AutoTile, Tiled2D
{
	/**
	 * Returns the fuel of the {@code FireAutoTile}.
	 * 
	 * @return  a tile fuel
	 */
	public abstract float Fuel();
	
	/**
	 * Returns the mortality of the {@code FireAutoTile}.
	 * 
	 * @return  a mortality state
	 * 
	 * 
	 * @see Mortality
	 */
	public abstract Mortality Mortality();
	
	/**
	 * Changes the mortality of the {@code FireAutoTile}.
	 * 
	 * @param mrt  a mortality state
	 * 
	 * 
	 * @see Mortality
	 */
	public abstract void setMortality(Mortality mrt);
	
	/**
	 * Changes the fuel of the {@code FireAutoTile}.
	 * 
	 * @param fuel  a tile fuel
	 */
	public abstract void setFuel(float fuel);
	
	
	@Override
	public default FireAutoTile Neighbor(Cardinal2D c)
	{
		return (FireAutoTile) Tiled2D.super.Neighbor(c);
	}
}