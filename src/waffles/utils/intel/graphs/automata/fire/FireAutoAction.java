package waffles.utils.intel.graphs.automata.fire;

import waffles.utils.geom.utilities.constants.Cardinal2D;
import waffles.utils.intel.graphs.automata.AutoAction;
import waffles.utils.intel.graphs.automata.life.LifeAutoLayout;
import waffles.utils.intel.graphs.automata.life.LifeAutoTile;
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
	private FireAutoTile tile;
	private float fuel, spread;
	private Randomizer rng;
	
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
	public FireAutoAction(FireAutoLayout<?> lyt, FireAutoTile lat)
	{
		rng = lyt.RNG();
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
		if(mrt == Mortality.UNDEAD)
		{
			fuel = 0f;
			return;
		}
		
		int count = 1;
		for(Cardinal2D c : Cardinal2D.All())
		{
			if(c == Cardinal2D.CENTER)
				continue;
			
			FireAutoTile n = tile.Neighbor(c);
			if(n != null)
			{
				if(n.Mortality() == Mortality.ALIVE)
				{
					if(rng.randomFloat() < spread)
					{
						mrt = Mortality.ALIVE;
						fuel += n.Fuel();
						count++;
					}
				}
			}
		}
		
		if(mrt == Mortality.ALIVE)
		{
			fuel = fuel / count - 0.1f;
			fuel = Floats.max(0f, fuel);
			if(fuel <= 0f)
			{
				mrt = Mortality.UNDEAD;
			}
		}
	}
}