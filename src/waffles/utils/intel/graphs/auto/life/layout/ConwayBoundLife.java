package waffles.utils.intel.graphs.auto.life.layout;

import waffles.utils.intel.graphs.auto.life.LifeAutoBound;

/**
 * A {@code ConwayBoundLife} defines a {@code LifeAutoBound} for live cells.
 * Live cells stay alive when their number of neighbors is between 2 and 3.
 *
 * @author Waffles
 * @since 12 May 2024
 * @version 1.1
 *
 *
 * @see LifeAutoBound
 */
public class ConwayBoundLife implements LifeAutoBound
{
	@Override
	public LifeAutoBound.Behavior Behavior()
	{
		return Behavior.STAY_MID;
	}

	@Override
	public int[] Bounds()
	{
		return new int[]{2, 3};
	}
}