package waffles.utils.intel.graphs.automata.fuel;

import waffles.utils.intel.graphs.automata.AutoAction;
import waffles.utils.intel.graphs.automata.fire.FireAutoTile;
import waffles.utils.intel.graphs.automata.life.LifeAutoLayout;
import waffles.utils.intel.graphs.automata.life.LifeAutoTile;
import waffles.utils.intel.utilities.Mortality;
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
public class FuelAutoAction implements AutoAction
{
	private Mortality mrt;
	private float fuel, rate;
	private FireAutoTile tile;
	
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
	public FuelAutoAction(FuelAutoLayout<?> lyt, FireAutoTile lat)
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
		if(fuel < 1f)
		{
			fuel += rate;
			fuel = Floats.clamp(fuel, 0f, 1f);
			tile.setFuel(fuel);
			return true;
		}
		
		if(mrt != null)
		{
			tile.setMortality(mrt);
		}
		
		return false;
	}
	
	@Override
	public void compute()
	{
		if(mrt == Mortality.UNDEAD)
			mrt = Mortality.DEAD;
		else
			mrt = null;
	}
}