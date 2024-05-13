package waffles.utils.intel.graphs.automata.fuel;

import waffles.utils.intel.graphs.automata.AutoLayout;

/**
 * A {@code FuelAutoLayout} defines the parameters to generate a {@code FuelAutomaton}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 * 
 * 
 * @param <T>  a tile type
 * @see FuelAutoTile
 * @see AutoLayout
 */
@FunctionalInterface
public interface FuelAutoLayout<T extends FuelAutoTile> extends AutoLayout<T>
{	
	/**
	 * Returns the fuel rate of the {@code FuelAutoLayout}.
	 * 
	 * @return  a fuel rate
	 */
	public default float FuelRate()
	{
		return 0.02f;
	}
	
	
	@Override
	public default FuelAutoAction create(FuelAutoTile tile)
	{
		return new FuelAutoAction(this, tile);
	}
	
	@Override
	public default int AutoRadius()
	{
		return 0;
	}
}