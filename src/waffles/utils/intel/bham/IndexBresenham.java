package waffles.utils.intel.bham;

import java.util.Iterator;

import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.geom.spaces.index.IndexSpace;
import waffles.utils.geom.spaces.index.tiles.Tiled;
import waffles.utils.tools.patterns.semantics.Idleable;
import waffles.utils.tools.primitives.Array;

/**
 * The {@code IndexBresenham} algorithm performs Bresenham's line algorithm in an {@code IndexSpace}.
 *
 * @author Waffles
 * @since 09 Mar 2025
 * @version 1.0
 *
 *
 * @param <O>  an object type
 * @see Idleable
 * @see Iterator
 * @see Tiled
 */
public class IndexBresenham<O> implements Iterator<O>, Idleable
{
	private boolean isIdle;
	private int[] curr, last;
	private IndexSpace<O> space;
	private Bresenham bham;
	
	/**
	 * Creates a new {@code IndexBresenham}.
	 * 
	 * @param s  an index space
	 * 
	 * 
	 * @see IndexSpace
	 */
	public IndexBresenham(IndexSpace<O> s)
	{
		isIdle = true;
		space = s;
	}

	/**
	 * Initializes the {@code TiledBresenham}.
	 * 
	 * @param src  a source coordinate
	 * @param tgt  a target coordinate
	 */
	public void initialize(int[] src, int[] tgt)
	{
		int ord = space.Order();

		float[] dir = new float[ord];
		for(int i = 0; i < ord; i++)
		{
			dir[i] = tgt[i] - src[i];
		}
		
		
		bham = new Bresenham(dir);
		isIdle = false;
		curr = src;
		last = tgt;
	}

	
	private int[] findNext()
	{
		Vector v = bham.next();
		
		for(int i = 0; i < v.Size(); i++)
		{
			curr[i] += (int) v.get(i);
		}
		
		return curr;
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
	public O next()
	{
		O next = space.get(curr);
		
		curr = findNext();
		if(Array.equals.of(curr, last))
		{
			isIdle = true;
		}
		
		return next;
	}
}
