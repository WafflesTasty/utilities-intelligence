package waffles.utils.intel.graphs.auto.fire;

import waffles.utils.geom.utilities.constants.Cardinal2D;
import waffles.utils.intel.graphs.auto.AutoAction;
import waffles.utils.intel.utilities.Mortality;
import waffles.utils.tools.Randomizer;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code FireAutoAction} defines the local behavior of a {@code FireAutomaton}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 * 
 * 
 * @see AutoAction
 */
public class FireAutoAction implements AutoAction
{
	private Mortality mrt;
	private Randomizer rng;
	private FireAutoTile tile;
	private float fuel, rate;
	private float spread;
	
	/**
	 * Creates a new {@code FireAutoAction}.
	 * 
	 * @param lyt  an automaton layout
	 * @param lat  an automaton tile
	 * 
	 * 
	 * @see FireAutoLayout
	 * @see FireAutoTile
	 */
	public FireAutoAction(FireAutoLayout<?> lyt, FireAutoTile lat)
	{
		rng = lyt.RNG();
		rate = lyt.FireRate();
		spread = lyt.SpreadChance();
		mrt = lat.Mortality();
		fuel = lat.Fuel();
		tile = lat;
	}

			
	@Override
	public int[] Coordinates()
	{
		return tile.Coordinates();
	}
	
	@Override
	public boolean resolve()
	{
		tile.setFuel(fuel);
		tile.setMortality(mrt);
		return mrt == Mortality.ALIVE;
	}
	
	@Override
	public void compute()
	{
		switch(mrt)
		{
		case ALIVE:
		{
			fuel = Floats.clamp(fuel - rate, 0f, 1f);
			if(fuel == 0f)
			{
				mrt = Mortality.UNDEAD;
			}
		}
		case DEAD:
		{
			int count = 1;
			float sum = fuel;
			for(Cardinal2D c : Cardinal2D.All())
			{
				if(c == Cardinal2D.CENTER)
					continue;
				
				FireAutoTile n = tile.Neighbor(c);
				if(n != null)
				{
					if(n.Mortality() == Mortality.ALIVE)
					{
						if(rng.randomFloat() < spread / count)
						{
							sum += n.Fuel();
							count++;
						}
					}
				}
			}
			
			if(count > 1)
			{
				mrt = Mortality.ALIVE;
				fuel = sum / count;
			}
			
			break;
		}
		case UNDEAD:
		default:
			break;
		}
	}
}