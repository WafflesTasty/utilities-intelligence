package waffles.utils.intel.bham;

import java.util.Iterator;

import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.elements.linear.vector.Vectors;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code Bresenham} class implements a basic version of Bresenham's line algorithm.
 * It is implemented as an {@code Iterator} which starts at the origin and iterates
 * differential vectors along the direction vector at every step.
 *
 * @author Waffles
 * @since 09 Mar 2025
 * @version 1.0
 * 
 * 
 * @see Iterator
 * @see Vector
 */
public class Bresenham implements Iterator<Vector>
{
	private Vector curr;
	private Vector dir, err;
	private float dMax;
	
	/**
	 * Creates a new {@code Bresenham}.
	 * 
	 * @param d  a direction vector
	 * 
	 * 
	 * @see Vector
	 */
	public Bresenham(Vector d)
	{
		dir = d;

		int size = dir.Size();
		err = Vectors.create(size);
		curr = Vectors.create(size);
		for(int i = 0; i < size; i++)
		{
			float dVal = Floats.abs(dir.get(i));
			if(dMax < dVal)
			{
				dMax = dVal;
			}
		}
	}
	
	/**
	 * Creates a new {@code Bresenham}.
	 * 
	 * @param dVals  a direction vector
	 */
	public Bresenham(float... dVals)
	{
		this(Vectors.create(dVals));
	}
	
	
	private Vector findNext()
	{
		int size = dir.Size();
		
		Vector next = Vectors.create(size);
		for(int i = 0; i < dir.Size(); i++)
		{
			float e = err.get(i) + dir.get(i);
			if(dMax <= Floats.abs(e))
			{
				float sign = Floats.sign(e);
				
				e -= sign * dMax;
				next.set(sign, i);
			}

			err.set(e, i);
		}
		
		return next;
	}
	
	@Override
	public boolean hasNext()
	{
		return true;
	}

	@Override
	public Vector next()
	{
		Vector next = curr;
		curr = findNext();
		return next;
	}
}