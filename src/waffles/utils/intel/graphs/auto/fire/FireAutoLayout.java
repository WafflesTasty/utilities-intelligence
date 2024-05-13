package waffles.utils.intel.graphs.auto.fire;

import waffles.utils.intel.graphs.auto.AutoLayout;
import waffles.utils.tools.Randomizer;

/**
 * A {@code FireAutoLayout} defines the parameters to generate a {@code FireAutomaton}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 * 
 * 
 * @param <T>  a tile type
 * @see FireAutoAction
 * @see FireAutoTile
 * @see AutoLayout
 */
@FunctionalInterface
public interface FireAutoLayout<T extends FireAutoTile> extends AutoLayout<T>
{	
	/**
	 * Returns the rng of the {@code FireAutoLayout}.
	 * 
	 * @return  a random generator
	 * 
	 * 
	 * @see Randomizer
	 */
	public default Randomizer RNG()
	{
		return Randomizer.Global();
	}
	
	/**
	 * Returns the fire rate of the {@code FireAutoLayout}.
	 * 
	 * @return  a fire rate
	 */
	public default float FireRate()
	{
		return 0.10f;
	}
	
	/**
	 * Returns the fire spread of the {@code FireAutoLayout}.
	 * 
	 * @return  a spread chance
	 */
	public default float SpreadChance()
	{
		return 0.33f;
	}
	
	@Override
	public default FireAutoAction create(FireAutoTile tile)
	{
		return new FireAutoAction(this, tile);
	}
	
	@Override
	public default int AutoRadius()
	{
		return 1;
	}
}