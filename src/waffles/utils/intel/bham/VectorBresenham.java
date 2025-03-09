package waffles.utils.intel.bham;

import java.util.Iterator;

import waffles.utils.algebra.elements.linear.vector.Vector;
import waffles.utils.algebra.elements.linear.vector.Vectors;
import waffles.utils.tools.patterns.semantics.Idleable;
import waffles.utils.tools.primitives.Floats;
import waffles.utils.tools.primitives.Integers;

/**
 * The {@code VectorBresenham} algorithm performs Bresenham's line algorithm with {@code Vectors}.
 *
 * @author Waffles
 * @since 09 Mar 2025
 * @version 1.0
 *
 *
 * @see Idleable
 * @see Iterator
 */
public class VectorBresenham implements Iterator<Vector>, Idleable
{
	private boolean isIdle;
	private Vector curr, last;
	private Bresenham bham;
	
	/**
	 * Creates a new {@code VectorBresenham}.
	 */
	public VectorBresenham()
	{
		isIdle = true;
	}

	/**
	 * Initializes the {@code TiledBresenham}.
	 * The values are separated into a
	 * source and target vector.
	 * 
	 * @param vals  a value array
	 */
	public void initialize(float... vals)
	{
		int len = vals.length;
		int size = Integers.mod(len, 2);
		size += len / 2;

		Vector p = Vectors.create(size);
		Vector q = Vectors.create(size);
		for(int i = 0; i < len; i++)
		{
			if(size <= i)
				q.set(vals[i], i - size);
			else
				p.set(vals[i], i);
		}
		
		initialize(p, q);
	}
	
	/**
	 * Initializes the {@code TiledBresenham}.
	 * 
	 * @param p  a source vector
	 * @param q  a target vector
	 * 
	 * 
	 * @see Vector
	 */
	public void initialize(Vector p, Vector q)
	{
		Vector dir = q.minus(p);
		bham = new Bresenham(dir);
		// Skip the origin.
		bham.next();
		
		curr = Vectors.create(p.Size());
		for(int i = 0; i < p.Size(); i++)
		{
			float val = p.get(i);
			if(dir.get(i) > 0)
				val = Floats.floor(val);
			else
				val = Floats.ceil(val);
			
			curr.set(val, i);
		}
		
		last = Vectors.create(q.Size());
		for(int i = 0; i < q.Size(); i++)
		{
			float val = q.get(i);
			if(dir.get(i) < 0)
				val = Floats.floor(val);
			else
				val = Floats.ceil(val);
			
			last.set(val, i);
		}
		
		isIdle = false;
	}
	
	
	private Vector findNext()
	{
		Vector v = bham.next();
		
		for(int i = 0; i < v.Size(); i++)
		{
			float c = curr.get(i);
			v.set(v.get(i) + c, i);
		}
		
		return v;
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
	public Vector next()
	{
		Vector next = curr;	
		curr = findNext();
		if(curr.equals(last))
		{
			isIdle = true;
		}
		
		return next;
	}
}
