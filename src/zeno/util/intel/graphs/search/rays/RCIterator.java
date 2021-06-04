package zeno.util.intel.graphs.search.rays;

import java.util.Iterator;

import zeno.util.algebra.linear.vector.fixed.Vector3;
import zeno.util.coll.Queue;
import zeno.util.coll.queues.FIFOQueue;
import zeno.util.coll.space.planar.TiledSpace2D.Tile2D;
import zeno.util.intel.graphs.search.Raycaster;
import zeno.util.intel.graphs.search.Raycaster.Source;
import zeno.util.tools.Floats;
import zeno.util.tools.Integers;
import zeno.util.tools.helper.Array;

/**
 * The {@code RCIterator} class is the main iterator for a {@code Raycaster}.
 *
 * @author Waffles
 * @since 27 May 2021
 * @version 1.0
 * 
 * 
 * @param <T>  a tile type
 * @see Iterator
 * @see Tile2D
 */
public class RCIterator<T extends Tile2D> implements Iterator<T>
{
	private static final float DELTA = 0.5f;
	
	
	private T next;
	private float rMax;
	private Raycaster<T> cst;
	private Queue<Diamond> queue;
	
	/**
	 * Creates a new {@code RCIterator}.
	 * 
	 * @param c  a target cst
	 * 
	 * 
	 * @see Raycaster
	 */
	public RCIterator(Raycaster<T> c)
	{
		cst = c;
		rMax = MaxRadius();
		next = c.Source().Tile();
		queue = new FIFOQueue<>();
		queue.push(Initial());
	}

	
	private T findNext()
	{
		// If the diamonds are processed...
		if(queue.isEmpty())
		{
			// There are no more tiles.
			return null;
		}
		
		// Otherwise, pull the next tile.
		Diamond dmd = queue.pop();
		Vector3 p = dmd.next();
		
		int c = (int) p.X();
		int r = (int) p.Y();

		
		Source<T> src = cst.Source();
		// Find the closest point on the tile.
		float dx = c - src.Tile().Column();
		float dy = r - src.Tile().Row();

		float cx = dx == 0 ? dx : dx - Floats.sign(dx) / 2;
		float cy = dy == 0 ? dy : dy - Floats.sign(dy) / 2;
		float cr = src.Radius();
		
		// If this point is outside the radius...
		if(cr * cr <= cx * cx + cy * cy)
		{
			// And the diamond has more points...
			if(dmd.hasNext())
				// Add it back to the queue.
				queue.push(dmd);
			else
			{
				// Else, if the maximum radius is not reached...
				int rad = dmd.Radius();
				if(rad < rMax)
				{
					float tMin = dmd.MinAngle();
					float tMax = dmd.MaxAngle();
					
					dmd = new Diamond(cst.Source(), tMin, tMax, rad+1);
					if(dmd.hasNext())
					{
						// Add the next diamond to the queue.
						queue.push(dmd);
					}
				}
			}
			
			// The point is useless. Find the next one.
			return findNext();
		}


		// If the tile is inside the radius...		
		next = cst.Target().get(c, r);
		// And it blocks the raycaster...
		if(cst.Source().blocksRay(next))
		{
			// If the tile is on the negative x-axis...
			if(dx < 0 && dy == 0)
			{
				// Compute the rays toward the tile.
				float[] thetas = new float[]
				{
					Floats.atan2(dx + DELTA, dy - DELTA),
					Floats.atan2(dx + DELTA, dy + DELTA)
				};
				
				float t0 = Floats.max(thetas[0], dmd.MinAngle());
				float t1 = Floats.min(thetas[1], dmd.MaxAngle());

				int rad = dmd.Radius();
				rad = dmd.hasNext() ? rad : rad+1;
				dmd = new Diamond(cst.Source(), t0, t1, rad, p);
				if(dmd.hasNext())
				{
					// Add the next diamond to the queue.
					queue.push(dmd);
				}
				
				// If the tile does not exist...
				if(next == null)
				{
					// It is useless. Find the next one.
					return findNext();
				}
				
				// Otherwise, return it.
				return next;
			}
			
			// Compute the rays toward the tile.
			float[] thetas = new float[]
			{
				Floats.atan2(dx + DELTA, dy + DELTA),
				Floats.atan2(dx - DELTA, dy + DELTA),
				Floats.atan2(dx + DELTA, dy - DELTA),
				Floats.atan2(dx - DELTA, dy - DELTA)
			};
			
			Array.sort.of(thetas);

			float t0 = dmd.MinAngle();
			float t1 = thetas[0];
			float t2 = thetas[3];
			float t3 = dmd.MaxAngle();

			
			int rad = dmd.Radius();
			// If the ray is split from below...
			if(t0 < t1 && rad < rMax)
			{
				dmd = new Diamond(cst.Source(), t0, t1, rad+1);
				if(dmd.hasNext())
				{
					// Add the partial diamond to the queue.
					queue.push(dmd);
				}			
			}
			// If the ray is split from above...
			if(t2 < t3)
			{
				dmd = new Diamond(cst.Source(), t2, t3, rad, p);
				if(dmd.hasNext())
				{
					// Add the partial diamond to the queue.
					queue.push(dmd);
				}
			}
			
			// If the tile does not exist...
			if(next == null)
			{
				// It is useless. Find the next one.
				return findNext();
			}
			
			// Otherwise, return it.
			return next;
		}
		
		// Otherwise, it does not block the raycaster.
		// If the diamond has more points...
		if(dmd.hasNext())
			// Add it back to the queue.
			queue.push(dmd);
		else
		{
			// Otherwise, the diamond is discarded.
			float tMin = dmd.MinAngle();
			float tMax = dmd.MaxAngle();
			
			int rad = dmd.Radius();
			// If max radius is not yet reached...
			if(rad < rMax)
			{
				// Add the diamond of next radius.
				dmd = new Diamond(cst.Source(), tMin, tMax, rad+1);
				if(dmd.hasNext())
				{
					queue.push(dmd);
				}
			}
		}
		
		// If the tile does not exist...
		if(next == null)
		{
			// It is useless. Find the next one.
			return findNext();
		}
		
		// Otherwise, return it.
		return next;
	}

	private Diamond Initial()
	{
		return new Diamond(cst.Source(), -Floats.PI, Floats.PI, 1);
	}
	
	private float MaxRadius()
	{
		return Integers.ceil(Floats.sqrt(2) * cst.Source().Radius());
	}
	
	@Override
	public boolean hasNext()
	{
		return next != null;
	}

	@Override
	public T next()
	{
		T curr = next;
		next = findNext();
		return curr;
	}
}