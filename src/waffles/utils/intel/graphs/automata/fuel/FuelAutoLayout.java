package waffles.utils.intel.graphs.automata.fuel;

import waffles.utils.intel.graphs.automata.AutoLayout;
import waffles.utils.intel.graphs.automata.fire.FireAutoTile;

/**
 * A {@code FuelAutoLayout} defines the parameters to generate a {@code FuelAutomaton}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 * 
 * 
 * @param <T>  a tile type
 * @see FuelAutoAction
 * @see FireAutoTile
 * @see AutoLayout
 */
@FunctionalInterface
public interface FuelAutoLayout<T extends FireAutoTile> extends AutoLayout<FuelAutoAction, T>
{	
	/**
	 * Returns the fuel rate of the {@code FuelAutoLayout}.
	 * 
	 * @return  a fuel rate
	 */
	public default float FuelRate()
	{
		return 0.1f;
	}
	
	@Override
	public default FuelAutoAction create(FireAutoTile tile)
	{
		return new FuelAutoAction(this, tile);
	}
}