package waffles.utils.intel.graphs.life;

import waffles.utils.intel.utilities.Mortality;

/**
 * A {@code LifeAutoAction} maintains changes in {@code LifeAutoTile Mortality}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 */
public class LifeAutoAction
{
	private Mortality mrt;
	private LifeAutoTile tile;
	
	/**
	 * Creates a new {@code LifeAutoAction}.
	 * 
	 * @param t  a target tile
	 * 
	 * 
	 * @see LifeAutoTile
	 */
	public LifeAutoAction(LifeAutoTile t)
	{
		tile = t;
	}
	
	/**
	 * Changes the mortality of the {@code LifeAutoAction}.
	 * 
	 * @param m  a mortality
	 * 
	 * 
	 * @see Mortality
	 */
	public void setMortality(Mortality m)
	{
		mrt = m;
	}
	
	/**
	 * Returns the mortality of the {@code LifeAutoAction}.
	 * 
	 * @return  a mortality
	 * 
	 * 
	 * @see Mortality
	 */
	public Mortality Mortality()
	{
		return mrt;
	}
	
	/**
	 * Returns the tile of the {@code LifeAutoAction}.
	 * 
	 * @return  a target tile
	 * 
	 * 
	 * @see LifeAutoTile
	 */
	public LifeAutoTile Tile()
	{
		return tile;
	}
}