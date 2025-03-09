package waffles.utils.intel.grids.auto.sand;

import waffles.utils.geom.utilities.constants.Cardinal2D;
import waffles.utils.intel.grids.auto.AutoLayout;
import waffles.utils.tools.Randomizer;

/**
 * A {@code SandAutoLayout} defines the parameters for a {@code SandAutomaton}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 * 
 * 
 * @param <T>  a tile type
 * @see SandAutoTile
 * @see AutoLayout
 */
@FunctionalInterface
public interface SandAutoLayout<T extends SandAutoTile> extends AutoLayout<T>
{	
	/**
	 * Returns the direction of the {@code SandAutoLayout}.
	 * 
	 * @return  a cardinal direction
	 * 
	 * 
	 * @see Cardinal2D
	 */
	public default Cardinal2D Direction()
	{
		return Cardinal2D.SOUTH;
	}

	/**
	 * Returns a lazy gravity for the {@code SandAutoLayout}.
	 * This should be overridden and handled by a
	 * singleton to increase performance.
	 * 
	 * @return  a lazy gravity
	 * 
	 * 
	 * @see SandAutoGravity
	 */
	public default SandAutoGravity Gravity()
	{
		return new SandAutoGravity(Randomizer());
	}
	
	/**
	 * Returns the randomizer of the {@code SandAutoLayout}.
	 * 
	 * @return  a randomizer
	 * 
	 * 
	 * @see Randomizer
	 */
	public default Randomizer Randomizer()
	{
		return Randomizer.Global();
	}


	@Override
	public default SandAutoAction create(SandAutoTile tile)
	{
		return new SandAutoAction(this, tile);
	}
	
	@Override
	public default int AutoRadius()
	{
		return 1;
	}
}