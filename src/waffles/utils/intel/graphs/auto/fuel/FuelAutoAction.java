package waffles.utils.intel.graphs.auto.fuel;

import waffles.utils.intel.graphs.auto.AutoAction;
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
	private Mortality mort;
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
		mort = lat.Mortality();
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
		tile.setMortality(mort);
		return fuel < 1f;
	}
	
	@Override
	public void compute()
	{
		fuel += rate;
		fuel = Floats.clamp(fuel, 0f, 1f);
		if(mort == Mortality.UNDEAD)
		{
			mort = Mortality.DEAD;
		}
	}
}