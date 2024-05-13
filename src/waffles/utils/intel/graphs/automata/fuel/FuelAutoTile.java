package waffles.utils.intel.graphs.automata.fuel;

import waffles.utils.geom.spaces.index.tiles.Tiled2D;
import waffles.utils.geom.utilities.constants.Cardinal2D;
import waffles.utils.intel.graphs.automata.AutoTile;
import waffles.utils.intel.utilities.Mortality;

/**
 * A {@code FuelAutoTile} defines a single {@code FuelAutomaton} tile.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 *
 * 
 * @see AutoTile
 * @see Tiled2D
 */
public interface FuelAutoTile extends AutoTile, Tiled2D
{
	/**
	 * Returns the fuel of the {@code FuelAutoTile}.
	 * 
	 * @return  a tile fuel
	 */
	public abstract float Fuel();
	
	/**
	 * Returns the mortality of the {@code FuelAutoTile}.
	 * 
	 * @return  a mortality state
	 * 
	 * 
	 * @see Mortality
	 */
	public abstract Mortality Mortality();
	
	/**
	 * Changes the mortality of the {@code FuelAutoTile}.
	 * 
	 * @param mrt  a mortality state
	 * 
	 * 
	 * @see Mortality
	 */
	public abstract void setMortality(Mortality mrt);
	
	/**
	 * Changes the fuel of the {@code FuelAutoTile}.
	 * 
	 * @param fuel  a tile fuel
	 */
	public abstract void setFuel(float fuel);
	
	
	@Override
	public default FuelAutoTile Neighbor(Cardinal2D c)
	{
		return (FuelAutoTile) Tiled2D.super.Neighbor(c);
	}
}