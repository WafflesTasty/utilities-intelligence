package waffles.utils.intel.grids.auto.sand;

import waffles.utils.geom.spaces.index.tiles.Tiled2D;
import waffles.utils.geom.utilities.constants.Cardinal2D;
import waffles.utils.intel.grids.auto.AutoTile;
import waffles.utils.intel.utilities.Mortality;
import waffles.utils.intel.utilities.Vacancy;

/**
 * A {@code SandAutoTile} defines a single {@code SandAutomaton} tile.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 *
 * 
 * @see AutoTile
 * @see Tiled2D
 */
public interface SandAutoTile extends AutoTile, Tiled2D
{
	/**
	 * Returns the vacancy of the {@code SandAutoTile}.
	 * 
	 * @return  a vacancy state
	 * 
	 * 
	 * @see Vacancy
	 */
	public abstract Vacancy Vacancy();
	
	/**
	 * Returns the mortality of the {@code SandAutoTile}.
	 * 
	 * @return  a mortality state
	 * 
	 * 
	 * @see Mortality
	 */
	public abstract Mortality Mortality();
	
	/**
	 * Changes the vacancy of the {@code SandAutoTile}.
	 * 
	 * @param vcy  a vacancy state
	 * 
	 * 
	 * @see Vacancy
	 */
	public abstract void setVacancy(Vacancy vcy);

	/**
	 * Changes the mortality of the {@code SandAutoTile}.
	 * 
	 * @param mrt  a mortality state
	 * 
	 * 
	 * @see Mortality
	 */
	public abstract void setMortality(Mortality mrt);
	
	
	@Override
	public default SandAutoTile Neighbor(Cardinal2D c)
	{
		return (SandAutoTile) Tiled2D.super.Neighbor(c);
	}
}