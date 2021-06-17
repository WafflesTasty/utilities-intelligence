package zeno.util.intel.graphs.search.rays;

import java.util.Iterator;

import zeno.util.coll.Queue;
import zeno.util.coll.queues.FIFOQueue;
import zeno.util.coll.space.planar.TiledSpace2D;
import zeno.util.coll.space.planar.TiledSpace2D.Tile2D;
import zeno.util.intel.graphs.search.Raycaster;

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
	private T next;
	private int dMax;
	private Raycaster<T> cst;
	private Queue<RCDiamond> queue;
	
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
		next = c.Tile();
		dMax = cst.Diagonal();
		queue = new FIFOQueue<>();
		queue.push(new RCDiamond());
	}

	
	@Override
	public boolean hasNext()
	{
		return next != null;
	}

	private T findNext()
	{
		// If the diamond queue is empty...
		if(queue.isEmpty())
		{
			// The iterator is done.
			return null;
		}
		
		// Otherwise, fetch the next diamond.		
		RCDiamond diam = queue.peek();
		// If the diamond is finished...
		if(!diam.hasNext())
		{
			// And it is at max diagonal...
			if(!diam.grow(dMax))
			{
				// Remove it from the queue.
				queue.pop();
			}
			
			// And start from the top.
			return findNext();
		}
		
		
		RCTile tile = diam.next();
		int r = tile.Y() + cst.Row();
		int c = tile.X() + cst.Column();
		TiledSpace2D<T> tgt = cst.Target();
		
		next = null;
		// Fetch the next tile.
		if(tgt.contains(c, r))
		{
			next = tgt.get(c, r);
		}
		
		
		// If the tile blocks the raycaster...
		if(cst.blocksRay(next))
		{
			// Along the negative x-axis...
			if(tile.X() < 0 && tile.Y() == 0)
				// Clip the diamond's bounds.
				diam.clip(tile);
			else
			{
				queue.pop();
				// Otherwise, split the diamond.
				for(RCDiamond dmd : diam.split(tile))
				{
					queue.push(dmd);
				}
			}
		}
		
		
		// If the tile does not exist...
		if(next == null)
			// Start from the top.
			return findNext();
		// Otherwise, return it.
		return next;
	}
	
	@Override
	public T next()
	{
		T curr = next;
		next = findNext();
		return curr;
	}
}