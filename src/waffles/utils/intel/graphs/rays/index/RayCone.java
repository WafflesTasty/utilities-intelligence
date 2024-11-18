package waffles.utils.intel.graphs.rays.index;

import waffles.utils.algebra.Partition;
import waffles.utils.algebra.elements.interval.Cuts;
import waffles.utils.algebra.elements.interval.Interval;
import waffles.utils.algebra.elements.interval.Intervals;
import waffles.utils.sets.utilities.coordinates.Coordinated2D;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code RayCone} iterates over a single cone in a {@code RayIterator}.
 * Each cone is iterated in a diamond pattern, starting from the tile at the
 * low angle of the cone and moving towards the high angle. Once the high angle
 * is reached, the radius of the diamond is increased and iteration begins again.
 *
 * @author Waffles
 * @since 07 Nov 2024
 * @version 1.1
 *
 * 
 * @see Coordinated2D
 * @see Partition
 */
public class RayCone implements Coordinated2D, Partition
{
	private int[] coords;
	private Interval cone;
	
	/**
	 * Creates a new {@code RayCone}.
	 * 
	 * @param lyt  a raycaster layout
	 * @param r  a target tile row
	 * @param c  a target tile column
	 * 
	 * 
	 * @see RayLayout
	 */
	public RayCone(RayLayout lyt, int r, int c)
	{		
		float rSrc = lyt.SourceRadius() / 2;
		float rTgt = lyt.TargetRadius() / 2;
		
		
		float tan = Floats.atan2(c, r);
		float sin = Floats.sqrt(c * c + r * r);
		sin = Floats.asin((rTgt - rSrc) / sin);
		
		float a1 = Floats.normrad(tan - sin);
		float a2 = Floats.normrad(tan + sin);

		coords = new int[]{r, c};
		cone = Intervals.create
		(
			Cuts.Below(Floats.min(a1, a2)),
			Cuts.Above(Floats.max(a1, a2))			
		);
	}

	/**
	 * Returns the minimum cut of the {@code RayCone}.
	 * 
	 * @return  a minimum cut
	 */
	public float Minimum()
	{
		return cone.Minimum().value();
	}

	/**
	 * Returns the maximum cut of the {@code RayCone}.
	 * 
	 * @return  a maximum cut
	 */
	public float Maximum()
	{
		return cone.Maximum().value();
	}
	
	/**
	 * Returns the diagonal of the {@code RayCone}.
	 * 
	 * @return  a cone diagonal
	 */
	public int Diagonal()
	{
		return Integers.abs(Row()) + Integers.abs(Column());
	}
	
	
	@Override
	public int compareTo(float val)
	{
		float ang = Floats.normrad(val);
		return cone.compareTo(ang);
	}
	
	@Override
	public int[] Coordinates()
	{
		return coords;
	}
}