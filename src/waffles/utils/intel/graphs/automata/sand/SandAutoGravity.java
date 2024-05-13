package waffles.utils.intel.graphs.automata.sand;

import waffles.utils.geom.utilities.constants.Cardinal2D;
import waffles.utils.geom.utilities.constants.Dial;
import waffles.utils.tools.Randomizer;
import waffles.utils.tools.patterns.properties.LazyValue;

/**
 * The {@code SandAutoGravity} determines the direction of falling {@code SandAutoTiles}.
 * This class is implemented as a lazy value, so the direction array is only computed
 * once, using the gravity of the {@code SandAutoLayout}.
 *
 * @author Waffles
 * @since 13 May 2024
 * @version 1.1
 * 
 * 
 * @see Cardinal2D
 * @see LazyValue
 */
public class SandAutoGravity extends LazyValue<Cardinal2D, Cardinal2D[]>
{
	private Randomizer rng;
	
	/**
	 * Creates a new {@code SandAutoGravity}.
	 * 
	 * @param rng  a randomizer
	 * 
	 * 
	 * @see Randomizer
	 */
	public SandAutoGravity(Randomizer rng)
	{
		this.rng = rng;
	}
	
	
	@Override
	public Cardinal2D[] compute(Cardinal2D grv)
	{
		Cardinal2D[] dir = new Cardinal2D[3];
		
		dir[0] = grv;
		dir[1] = grv.spin(Dial.CLOCKWISE);
		dir[2] = grv.spin(Dial.CNT_CLOCKWISE);
		
		return dir;
	}
	
	@Override
	public Cardinal2D[] Value(Cardinal2D grv)
	{
		Cardinal2D[] dir = super.Value(grv);
		
		if(rng.randomBoolean())
		{
			Cardinal2D tmp = dir[1];
			dir[1] = dir[2];
			dir[2] = tmp;
		}
		
		return dir;
	}
}