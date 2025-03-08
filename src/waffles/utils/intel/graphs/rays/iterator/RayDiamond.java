package waffles.utils.intel.graphs.rays.iterator;

import java.util.Iterator;

import waffles.utils.algebra.Partition;
import waffles.utils.algebra.elements.interval.Cuts;
import waffles.utils.algebra.elements.interval.Interval;
import waffles.utils.algebra.elements.interval.Intervals;
import waffles.utils.intel.graphs.rays.RayCaster;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * A {@code RayDiamond} iterates over a single diamond in a {@code RayIterator}.
 * Each cone is iterated in a diamond pattern, starting from the tile at the low
 * angle of the cone and moving towards the high angle. Once the high angle is
 * reached, the radius is increased and iteration begins again.
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
	private int iNext, iLast;
	private float rSrc, rTgt;
	private boolean isActive;
	private Interval cone;
	
	/**
	 * Creates a new {@code RayDiamond}.
	 * 
	 * @param c  a raycaster
	 * 
	 * 
	 * @see RayCaster
	 */
	public RayDiamond(RayCaster<?> c)
	{
		this(c, 1);
	}

	/**
	 * Creates a new {@code RayCone}.
	 * 
	 * @param c  a raycaster
	 * @param r  a radius
	 * 
	 * 
	 * @see RayCaster
	 */
	public RayDiamond(RayCaster<?> c, int r)
	{		
		rSrc = c.SourceRadius();
		rTgt = c.TargetRadius();
		
		iNext = 0; iLast = 4 * r;
		cone = Intervals.RADIANS;
		isActive = false;
		rad = r;
	}

	
	/**
	 * Changes the minimum angle of the {@code RayDiamond}.
	 * 
	 * @param min  a minimum angle
	 */
	public void setMinimum(float min)
	{
		if(min < Minimum())
			return;
		
		cone = Intervals.create
		(
			Cuts.Below(min),
			cone.Maximum()
		);
		
		if(!isActive)
		{
			computeNext();
		}
	}

	/**
	 * Changes the maximum angle of the {@code RayDiamond}.
	 * 
	 * @param max  a maximum angle
	 */
	public void setMaximum(float max)
	{
		if(max > Maximum())
			return;
		
		cone = Intervals.create
		(
			cone.Minimum(),
			Cuts.Below(max)
		);
		
		computeLast();
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
		return rad;
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


		iNext++;
		// If the index is outside bounds...
		if(iNext <= 0 || iLast < iNext)
		{
			// ...increase the radius.
			
			rad++;

			computeNext();
			computeLast();
		}

		isActive = true;
		return curr;
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
	
	private void computeNext()
	{
		int k = 0;
		float aMin = Minimum();
		while(aMin >= -Floats.PI / 2)
		{
			aMin -= Floats.PI / 2;
			k++;
		}
		
		
		float sin = Floats.sin(aMin);
		float cos = Floats.cos(aMin);
		
		float xMin = (-rSrc - rad * cos) / (sin + cos);
		float yMin = (+rSrc - rad * sin) / (sin + cos);
		
		int xTop = (int) Floats.floor(xMin);
		int yTop = (int) Floats.ceil (yMin);
		
		
		float xDst = xMin - xTop;
		float yDst = yMin - yTop;
		
		if(xDst * xDst + yDst * yDst > rTgt * rTgt)
			iNext = k * rad - yTop + 1;
		else
			iNext = k * rad - yTop;
	}

	private void computeLast()
	{
		int k = 0;
		float aMax = Maximum();
		while(aMax >= -Floats.PI / 2)
		{
			aMax -= Floats.PI / 2;
			k++;
		}
		
		
		float sin = Floats.sin(aMax);
		float cos = Floats.cos(aMax);
		
		float xMin = (+rSrc - rad * cos) / (sin + cos);
		float yMin = (-rSrc - rad * sin) / (sin + cos);
		
		int xBot = (int) Floats.ceil (xMin);
		int yBot = (int) Floats.floor(yMin);
		
		
		float xDst = xMin - xBot;
		float yDst = yMin - yBot;
		
		if(xDst * xDst + yDst * yDst > rTgt * rTgt)
			iLast = k * rad - yBot - 1;
		else
			iLast = k * rad - yBot;
	}
}