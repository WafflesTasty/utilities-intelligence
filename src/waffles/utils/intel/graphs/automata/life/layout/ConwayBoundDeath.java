package waffles.utils.intel.graphs.automata.life.layout;

import waffles.utils.intel.graphs.automata.life.LifeAutoBound;

/**
 * A {@code ConwayBoundDeath} defines a {@code LifeAutoBound} for dead cells.
 * Dead cells stay dead when their number of neighbours is more or less than 3.
 *
 * @author Waffles
 * @since 12 May 2024
 * @version 1.1
 *
 *
 * @see LifeAutoBound
 */
public class ConwayBoundDeath implements LifeAutoBound
{
	@Override
	public LifeAutoBound.Behavior Behavior()
	{
		return Behavior.STAY_EXTREME;
	}

	@Override
	public int[] Bounds()
	{
		return new int[]{3, 3};
	}
}