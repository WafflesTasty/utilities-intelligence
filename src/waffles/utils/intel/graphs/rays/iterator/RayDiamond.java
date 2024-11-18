package waffles.utils.intel.graphs.rays.iterator;

import java.util.Iterator;

import waffles.utils.algebra.Partition;
import waffles.utils.algebra.elements.interval.Cut;
import waffles.utils.algebra.elements.interval.Cuts;
import waffles.utils.algebra.elements.interval.Interval;
import waffles.utils.algebra.elements.interval.Intervals;
import waffles.utils.intel.graphs.rays.RayCaster;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code RayDiamond} iterates over a single diamond in a {@code RayIterator}.
 * Each cone is iterated in a diamond pattern, starting from the tile at the
 * low angle of the cone and moving towards the high angle. Once the high angle
 * is reached, the radius of the diamond is increased and iteration begins again.
 *
 * @author Waffles
 * @since 07 Nov 2024
 * @version 1.1
 *
 * 
 * @see Partition
 * @see Iterator
 */
public class RayDiamond implements Partition, Iterator<int[]>
{
	private int rad;
	private Interval cone;
	private int iNext, iMax;
	private float rSrc, rTgt;
	
	/**
	 * Creates a new {@code RayDiamond}.
	 * 
	 * @param min  a minimum angle
	 * @param max  a maximum angle
	 */
	public RayDiamond(float min, float max)
	{
		this(min, max, 1);
	}

	/**
	 * Creates a new {@code RayCone}.
	 * 
	 * @param min  a minimum angle
	 * @param max  a maximum angle
	 * @param r  a diagonal radius
	 */
	public RayDiamond(float min, float max, int r)
	{
		rad = r;
		setMinimum(min);
		setMaximum(max);
	}

	
	/**
	 * Changes the minimum angle of the {@code RayCone}.
	 * 
	 * @param min  a minimum angle
	 */
	public void setMinimum(float min)
	{
		iNext = index(min, rad);
		cone = Intervals.create
		(
			Cuts.Below(min),
			cone.Maximum()
		);
		
		System.out.println("Minimum: " + iNext + " (" + Floats.tan(min) + ").");
	}

	/**
	 * Changes the maximum angle of the {@code RayCone}.
	 * 
	 * @param max  a maximum angle
	 */
	public void setMaximum(float max)
	{
		iMax = index(max, rad);
		cone = Intervals.create
		(
			cone.Minimum(),
			Cuts.Above(max)
		);
		
		System.out.println("Maximum: " + iMax + " (" + Floats.tan(max) + ").");
	}
	
	/**
	 * Returns the minimum cut of the {@code RayCone}.
	 * 
	 * @return  a minimum cut
	 */
	public float Minimum()
	{
		return aMin;
//		return cone.Minimum();
	}

	/**
	 * Returns the maximum cut of the {@code RayCone}.
	 * 
	 * @return  a maximum cut
	 */
	public float Maximum()
	{
		return aMax;
//		return cone.Maximum();
	}
	
	/**
	 * Returns the diagonal of the {@code RayCone}.
	 * 
	 * @return  a cone diagonal
	 */
	public int Diagonal()
	{
		return rad;
	}

	
	private int mindex()
	{
		float hpi = Floats.PI / 2;
		
		
		int k = 1;
		Cut min = cone.Minimum();
		while(!min.isBelow(-hpi))
		{
			min = min.minus(hpi);
			k++;
		}
		
//		float pi = Floats.PI;
//		int k = Integers.ceil(2 * (pi + a) / pi);
//		System.out.print("a(" + a + "), r(" + r + ") -> ");
		float tan = Floats.tan(min.value());
		float y = k - 1f / (Floats.abs(tan) + 1f);
//		System.out.print("k = " + k + ", tan = " + tan + ", y = " + y + " -> ");
//		System.out.println(Integers.round(y * r) + ".");
		return Integers.ceil(y * rad - rTgt);
	}
	
	private int index(float a, int r)
	{
		int k = 1;
		float ang = a;
		while(ang >= -Floats.PI / 2)
		{
			ang -= Floats.PI / 2;
			k++;
		}
		
//		float pi = Floats.PI;
//		int k = Integers.ceil(2 * (pi + a) / pi);
//		System.out.print("a(" + a + "), r(" + r + ") -> ");
		float tan = Floats.abs(Floats.tan(a));
		float y = k - 1f / (tan + 1f);
//		System.out.print("k = " + k + ", tan = " + tan + ", y = " + y + " -> ");
//		System.out.println(Integers.round(y * r) + ".");
		return Integers.round(y * r);
	}
	
	private int index(float a, int r)
	{
		int k = 1;
		float ang = a;
		while(ang >= -Floats.PI / 2)
		{
			ang -= Floats.PI / 2;
			k++;
		}
		
//		float pi = Floats.PI;
//		int k = Integers.ceil(2 * (pi + a) / pi);
//		System.out.print("a(" + a + "), r(" + r + ") -> ");
		float tan = Floats.abs(Floats.tan(a));
		float y = k - 1f / (tan + 1f);
//		System.out.print("k = " + k + ", tan = " + tan + ", y = " + y + " -> ");
//		System.out.println(Integers.round(y * r) + ".");
		return Integers.round(y * r);
	}
	
	private int X(int i, int r)
	{
		return r - Integers.abs(2 * r - i);
	}
	
	private int Y(int i, int r)
	{
		int j = Integers.mod(i - r, 4 * r);
		return X(j, r);
	}	

	
	@Override
	public int compareTo(float val)
	{
		float ang = Floats.normrad(val);
		return cone.compareTo(ang);
	}
	
	@Override
	public boolean hasNext()
	{
		return true;
	}

	@Override
	public int[] next()
	{
		int[] curr = new int[]
		{
			X(iNext, rad),
			Y(iNext, rad)
		};
		System.out.println("Next: " + iNext + " (" + X(iNext, rad) + ", " + Y(iNext, rad) + ").");
		
		iNext++;
		// If the index is outside bounds...
		if(iNext <= 0 || iMax <= iNext)
		{
			rad++;
			
			// ...increase the radius.			
			iNext = index(aMin, rad);			
			iMax = index(aMax, rad);
		}

		return curr;
	}
}