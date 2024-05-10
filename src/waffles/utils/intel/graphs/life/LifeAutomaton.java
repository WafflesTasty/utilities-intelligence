package waffles.utils.intel.graphs.life;

import waffles.utils.geom.utilities.constants.Cardinal2D;
import waffles.utils.intel.graphs.life.LifeAutoBound.Behavior;
import waffles.utils.intel.graphs.life.layout.ConwayLayout;
import waffles.utils.intel.utilities.Mortality;
import waffles.utils.phys.utilities.events.SteppedEvent;
import waffles.utils.sets.queues.Queue;
import waffles.utils.sets.queues.delegate.JFIFOQueue;

/**
 * A {@code LifeAutomaton} mimicks the behavior of Conway's game of life.
 * Each tile considers the amount of live neighbors. The tile may choose to
 * flip its mortality, or keep it, depending on whether the live neighbor
 * count is within a certain interval. These intervals may be defined
 * withn the supplied {@code LifeAutoLayout}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 *
 *
 * @param <T>  a tile type
 * @see LifeAutoTile
 * @see SteppedEvent
 */
public abstract class LifeAutomaton<T extends LifeAutoTile> extends SteppedEvent
{
	private LifeAutoLayout layout;
	private Queue<LifeAutoAction> action;
	private Queue<LifeAutoAction> buffer;
	
	/**
	 * Creates a new {@code LifeAutomaton}.
	 * 
	 * @param lyt  an automaton layout
	 * 
	 * 
	 * @see LifeAutoLayout
	 */
	public LifeAutomaton(LifeAutoLayout lyt)
	{
		super(lyt.Beat());
		action = new JFIFOQueue<>();
		layout = lyt;
	}
	
	/**
	 * Creates a new {@code LifeAutomaton}.
	 * By default, a {@code ConwayLayout} is used.
	 * 
	 * @param b  a beat time
	 */
	public LifeAutomaton(long b)
	{
		this(new ConwayLayout(b));
	}

	
	/**
	 * Updates a tile in the {@code LifeAutomaton}.
	 * 
	 * @param tile  a target tile
	 * 
	 * 
	 * @see LifeAutoTile
	 */
	public void update(LifeAutoTile tile)
	{
		for(Cardinal2D c : Cardinal2D.All())
		{
			LifeAutoTile n = tile.Neighbor(c);
			if(n != null)
			{
				action.push(new LifeAutoAction(n));
			}
		}
	}
		
	
	private Mortality invert(Mortality m)
	{
		switch(m)
		{
		case ALIVE:
			return Mortality.DEAD;
		case DEAD:
			return Mortality.ALIVE;
		case UNDEAD:
		default:
			return Mortality.UNDEAD;
		}
	}
		
	private Mortality compute(LifeAutoTile tile)
	{
		Mortality mrt = tile.Mortality();
		LifeAutoBound bnd = layout.Bound(mrt);
		
		
		int cnt = liveNeighbors(tile);
		if(bnd.Bounds()[0] <= cnt && cnt <= bnd.Bounds()[1])
		{
			if(bnd.Behavior() == Behavior.STAY_EXTREME)
			{
				mrt = invert(mrt);
			}
			
			return mrt;
		}

		if(bnd.Behavior() == Behavior.STAY_MID)
		{
			mrt = invert(mrt);
		}
		
		return mrt;
	}
	
	private int liveNeighbors(LifeAutoTile tile)
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
	public void onPulse(long beat)
	{
		for(LifeAutoAction act : action)
		{
			LifeAutoTile tile = act.Tile();
			Mortality mrt = tile.Mortality();
			act.setMortality(compute(tile));
		}
		
		buffer = action;
		action = new JFIFOQueue<>();
		for(LifeAutoAction act : buffer)
		{
			LifeAutoTile tile = act.Tile();
			Mortality mrt = act.Mortality();

			if(tile.Mortality() != mrt)
			{
				tile.setMortality(mrt);
				update(tile);
			}
		}
	}
}