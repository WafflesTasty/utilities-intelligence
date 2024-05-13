package waffles.utils.intel.graphs.automata.fuel;

import waffles.utils.intel.graphs.automata.AutoAction;
import waffles.utils.intel.utilities.Mortality;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code FuelAutoAction} defines the local behavior of a {@code FuelAutomaton}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 * 
 * 
 * @see AutoAction
 */
public class FuelAutoAction implements AutoAction
{
	private Mortality mrt;
	private float fuel, rate;
	private FuelAutoTile tile;
	
	/**
	 * Creates a new {@code FuelAutoAction}.
	 * 
	 * @param lyt  an automaton layout
	 * @param lat  an automaton tile
	 * 
	 * 
	 * @see FuelAutoLayout
	 * @see FuelAutoTile
	 */
	public FuelAutoAction(FuelAutoLayout<?> lyt, FuelAutoTile lat)
	{
		rate = lyt.FuelRate();
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
		return fuel < 1f;
	}
	
	@Override
	public void compute()
	{
		if(fuel < 0.5f)
		{
			mrt = Mortality.DEAD;
			fuel = 1f;
		}
		else
		{
			fuel += rate;
			fuel = Floats.clamp(fuel, 0f, 1f);
			if(mrt == Mortality.UNDEAD)
			{
				mrt = Mortality.ALIVE;
			}
		}
	}
}