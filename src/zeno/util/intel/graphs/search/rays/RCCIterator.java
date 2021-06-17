package zeno.util.intel.graphs.search.rays;

import java.util.Iterator;

import zeno.util.coll.space.planar.TiledSpace2D.Tile2D;
import zeno.util.intel.graphs.search.Raycaster;

/**
 * The {@code RCCIterator} class defines an iterator for a {@code Circular Raycaster}.
 *
 * @author Waffles
 * @since 16 Jun 2021
 * @version 1.0
 *
 *
 * @param <T>  a tile type
 * @see Iterator
 * @see Tile2D
 */
public class RCCIterator<T extends Tile2D> implements Iterator<T>
{
	private float rMax;
	private T tFirst, tNext;
	private RCIterator<T> tiles;
	
	/**
	 * Creates a new {@code RCCIterator}.
	 * 
	 * @param c  a target cst
	 * 
	 * 
	 * @see Raycaster
	 */
	public RCCIterator(Raycaster.Circular<T> c)
	{
		tFirst = c.Tile();
		tiles = new RCIterator<>(c);
		rMax = c.Radius();
		tNext = tFirst;
	}

		
	@Override
	public boolean hasNext()
	{
		return tNext != null;
	}

	private T findNext()
	{
		tNext = tiles.next();
		if(tNext == null)
		{
			return tNext;
		}
		
		
		int x = tNext.Column() - tFirst.Column();
		int y = tNext.Row()    - tFirst.Row();
		
		if(x * x + y * y >= rMax * rMax)
			return findNext();		
		return tNext;
	}
	
	@Override
	public T next()
	{
		T curr = tNext;
		tNext = findNext();
		return curr;
	}
}