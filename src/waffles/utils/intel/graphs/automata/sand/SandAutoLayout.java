package waffles.utils.intel.graphs.automata.sand;

import waffles.utils.geom.utilities.constants.Cardinal2D;
import waffles.utils.geom.utilities.constants.Dial;
import waffles.utils.intel.graphs.automata.AutoLayout;
import waffles.utils.tools.Randomizer;
import waffles.utils.tools.primitives.Array;

/**
 * A {@code SandAutoLayout} defines the parameters for a {@code SandAutomaton}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 * 
 * 
 * @param <T>  a tile type
 * @see SandAutoAction
 * @see SandAutoTile
 * @see AutoLayout
 */
public class SandAutoLayout<T extends SandAutoTile> implements AutoLayout<SandAutoAction, T>
{	
	private long beat;
	private Cardinal2D[] src, tgt;
	
	/**
	 * Creates a new {@code SandAutoLayout}.
	 * 
	 * @param b  a beat time
	 */
	public SandAutoLayout(long b)

	{
		beat = b;
	}
	
	/**
	 * Returns cardinal sources of the {@code SandAutoLayout}.
	 * 
	 * @return  a cardinal source set
	 * 
	 * 
	 * @see Cardinal2D
	 */
	public final Cardinal2D[] Source()
	{
		if(src == null)
		{
			src = new Cardinal2D[3];
			
			src[0] = Gravity().flip();
			src[1] = src[0].spin(Dial.CNT_CLOCKWISE);
			src[2] = src[0].spin(Dial.CLOCKWISE);
		}
		
		if(RNG().randomBoolean())
		{
			Cardinal2D tmp = src[1];
			src[1] = src[2];
			src[2] = tmp;
		}
		
		return Array.copy.of(src);
	}
	
	/**
	 * Returns cardinal targets of the {@code SandAutoLayout}.
	 * 
	 * @return  a cardinal target set
	 * 
	 * 
	 * @see Cardinal2D
	 */
	public final Cardinal2D[] Target()
	{
		if(tgt == null)
		{
			tgt = new Cardinal2D[3];
			
			tgt[0] = Gravity();
			tgt[1] = tgt[0].spin(Dial.CNT_CLOCKWISE);
			tgt[2] = tgt[0].spin(Dial.CLOCKWISE);
		}
		
		if(RNG().randomBoolean())
		{
			Cardinal2D tmp = tgt[1];
			tgt[1] = tgt[2];
			tgt[2] = tmp;
		}
		
		return Array.copy.of(tgt);
	}

	/**
	 * Returns the gravity of the {@code SandAutoLayout}.
	 * 
	 * @return  a gravity vector
	 * 
	 * 
	 * @see Cardinal2D
	 */
	public Cardinal2D Gravity()
	{
		return Cardinal2D.SOUTH;
	}
	
	/**
	 * Returns the rng of the {@code SandAutoLayout}.
	 * 
	 * @return  a random generator
	 * 
	 * 
	 * @see Randomizer
	 */
	public Randomizer RNG()
	{
		return Randomizer.Global();
	}


	@Override
	public SandAutoAction create(SandAutoTile tile)
	{
		return new SandAutoAction(this, tile);
	}
	
	@Override
	public int AutoRadius()
	{
		return 1;
	}
	
	@Override
	public long Beat()
	{
		return beat;
	}
}