package waffles.utils.intel.grids.auto.life;

import waffles.utils.geom.utilities.constants.Cardinal2D;
import waffles.utils.intel.grids.auto.AutoAction;
import waffles.utils.intel.grids.auto.life.LifeAutoBound.Behavior;
import waffles.utils.intel.utilities.Mortality;

/**
 * A {@code LifeAutoAction} defines the local behavior of a {@code LifeAutomaton}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 * 
 * 
 * @see AutoAction
 */
public class LifeAutoAction implements AutoAction
{
	private Mortality mrt;
	private LifeAutoBound bnd;
	private LifeAutoTile tile;
	
	/**
	 * Creates a new {@code LifeAutoAction}.
	 * 
	 * @param lyt  an automaton layout
	 * @param lat  an automaton tile
	 * 
	 * 
	 * @see LifeAutoLayout
	 * @see LifeAutoTile
	 */
	public LifeAutoAction(LifeAutoLayout<?> lyt, LifeAutoTile lat)
	{
		mrt = lat.Mortality();
		bnd = lyt.Bound(mrt);
		tile = lat;
	}

	
	private int liveNeighbors()
	{
		int count = 0;
		for(Cardinal2D c : Cardinal2D.All())
		{
			if(c == Cardinal2D.CENTER)
				continue;
			
			LifeAutoTile n = tile.Neighbor(c);
			if(n != null)
			{
				if(n.Mortality() == Mortality.ALIVE)
				{
					count++;
				}
			}
		}
		
		return count;
	}
			
	@Override
	public int[] Coordinates()
	{
		return tile.Coordinates();
	}
	
	@Override
	public boolean resolve()
	{
		if(tile.Mortality() != mrt)
		{
			tile.setMortality(mrt);
			return true;
		}
		
		return false;
	}
	
	@Override
	public void compute()
	{
		int cnt = liveNeighbors();
		if(bnd.Bounds()[0] <= cnt && cnt <= bnd.Bounds()[1])
		{
			if(bnd.Behavior() == Behavior.STAY_EXTREME)
			{
				mrt = Mortality.invert(mrt);
			}
		}
		else
		{
			if(bnd.Behavior() == Behavior.STAY_MID)
			{
				mrt = Mortality.invert(mrt);
			}
		}
	}
}