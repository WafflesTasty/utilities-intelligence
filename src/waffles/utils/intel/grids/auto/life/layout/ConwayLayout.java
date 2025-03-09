package waffles.utils.intel.grids.auto.life.layout;

import waffles.utils.intel.grids.auto.life.LifeAutoBound;
import waffles.utils.intel.grids.auto.life.LifeAutoLayout;
import waffles.utils.intel.grids.auto.life.LifeAutoTile;
import waffles.utils.intel.utilities.Mortality;

/**
 * A {@code ConwayLayout} defines the parameters to generate a {@code LifeAutomaton}.
 * These parameters are specifically set to mimick Conway's Game of Life.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 * 
 * 
 * @param <T>  a tile type
 * @see <a href="https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life">"Conway's Game of Life"</a>
 * @see LifeAutoLayout
 * @see LifeAutoTile
 */
public class ConwayLayout<T extends LifeAutoTile> implements LifeAutoLayout<T>
{
	private long beat;
	
	/**
	 * Creates a new {@code ConwayLayout}.
	 * 
	 * @param b  a beat time
	 */
	public ConwayLayout(long b)
	{
		beat = b;
	}
	
	
	@Override
	public LifeAutoBound Bound(Mortality mrt)
	{
		switch(mrt)
		{
		case ALIVE:
			return new ConwayBoundLife();
		case UNDEAD:
		case DEAD:
			return new ConwayBoundDeath();
		default:
			return null;
		}
	}
	
	@Override
	public long Beat()
	{
		return beat;
	}
}