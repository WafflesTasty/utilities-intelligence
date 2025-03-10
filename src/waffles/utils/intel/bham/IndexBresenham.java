package waffles.utils.intel.bham;

import java.util.Iterator;

import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.tools.patterns.semantics.Idleable;
import waffles.utils.tools.primitives.Array;

/**
 * The {@code IndexBresenham} algorithm performs Bresenham's line algorithm with integer indices.
 *
 * @author Waffles
 * @since 09 Mar 2025
 * @version 1.0
 *
 *
 * @see Idleable
 * @see Iterator
 */
public class IndexBresenham implements Iterator<int[]>, Idleable
{
	private boolean isIdle;
	private int[] curr, last;
	private Bresenham bham;
	
	/**
	 * Creates a new {@code IndexBresenham}.
	 * 
	 * @param ord  a dimension order
	 */
	public IndexBresenham(int ord)
	{
		curr = new int[ord];
		last = new int[ord];
		isIdle = true;
	}

	/**
	 * Initializes the {@code IndexBresenham}.
	 * 
	 * @param src  a source coordinate
	 * @param tgt  a target coordinate
	 */
	public void initialize(int[] src, int[] tgt)
	{
		int ord = curr.length;
		float[] dir = new float[ord];
		for(int i = 0; i < ord; i++)
		{
			dir[i] = tgt[i] - src[i];
		}
		
		curr = Array.copy.of(src);
		last = Array.copy.of(tgt);
		bham = new Bresenham(dir);
		isIdle = false;
	}


	@Override
	public boolean hasNext()
	{
		return !isIdle();
	}

	@Override
	public boolean isIdle()
	{
		return isIdle;
	}
	
	@Override
	public int[] next()
	{
		Vector v = bham.next();
		
		for(int i = 0; i < v.Size(); i++)
		{
			curr[i] += (int) v.get(i);
		}
		
		if(Array.equals.of(curr, last))
		{
			isIdle = true;
		}
		
		return curr;
	}
}
