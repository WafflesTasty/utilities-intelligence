package waffles.utils.intel.graphs.life.layout;

import waffles.utils.intel.graphs.life.LifeAutoBound;
import waffles.utils.intel.graphs.life.LifeAutoLayout;
import waffles.utils.intel.utilities.Mortality;

/**
 * A {@code ConwayLayout} defines the parameters for Conway's Game of Life.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 * 
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life">"Conway's Game of Life"</a>
 * @see LifeAutoLayout
 */
public class ConwayLayout implements LifeAutoLayout
{
	class DeathBound implements LifeAutoBound
	{
		@Override
		public LifeAutoBound.Behavior Behavior()
		{
			return Behavior.STAY_EXTREME;
		}

		@Override
		public int[] Bounds()
		{
			return new int[]{3, 3};
		}
	}
	
	class LifeBound implements LifeAutoBound
	{
		@Override
		public LifeAutoBound.Behavior Behavior()
		{
			return Behavior.STAY_MID;
		}

		@Override
		public int[] Bounds()
		{
			return new int[]{2, 3};
		}
	}
	
	
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
			return new LifeBound();
		case DEAD:
			return new DeathBound();
		case UNDEAD:
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