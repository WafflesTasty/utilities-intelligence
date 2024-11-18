package waffles.utils.intel.graphs.rays;

import waffles.utils.intel.graphs.rays.index.RayCone;
import waffles.utils.intel.graphs.rays.index.RayLayout;
import waffles.utils.sets.indexed.mutable.ArrayIndex;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code RayIndex} computes an index of cones for a {@link RayCaster}.
 *
 * @author Waffles
 * @since 17 Nov 2024
 * @version 1.1
 * 
 * 
 * @see ArrayIndex
 * @see RayCone
 */
public class RayIndex extends ArrayIndex<RayCone>
{		
	private float rSrc, rTgt;
	
	/**
	 * Creates a new {@code RayIndex}.
	 * 
	 * @param lyt  a raycaster layout
	 * 
	 * 
	 * @see RayLayout
	 */
	public RayIndex(RayLayout lyt)
	{
		super
		(
			2 * lyt.MaxDiagonal() + 1,
			2 * lyt.MaxDiagonal() + 1
		);
		

		int rows = Dimensions()[0];
		int cols = Dimensions()[1];
		
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < cols; c++)
			{
				RayCone cone = new RayCone(lyt, r, c);
				put(cone, r, c);
			}
		}
		
		rSrc = lyt.SourceRadius();
		rTgt = lyt.TargetRadius();
	}

	public int prev(float ang, int rad)
	{
		float tan = Floats.tan(ang);
		float y = k - 1f / (Floats.abs(tan) + 1f);
//		System.out.print("k = " + k + ", tan = " + tan + ", y = " + y + " -> ");
//		System.out.println(Integers.round(y * r) + ".");
		return Integers.ceil(y * rad - rTgt);
	}

	public int next(float ang, int rad)
	{
		
	}
}