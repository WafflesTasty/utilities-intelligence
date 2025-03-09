package waffles.utils.intel.bham;

import java.util.Iterator;

import waffles.utils.geom.spaces.index.TiledSpace;
import waffles.utils.geom.spaces.index.tiles.Tiled;
import waffles.utils.tools.patterns.semantics.Idleable;

/**
 * The {@code TiledBresenham} algorithm performs Bresenham's line algorithm in an {@code TiledSpace}.
 *
 * @author Waffles
 * @since 09 Mar 2025
 * @version 1.0
 *
 *
 * @param <T>  a tiled type
 * @see Idleable
 * @see Iterator
 * @see Tiled
 */
public class TiledBresenham<T extends Tiled> implements Iterator<T>, Idleable
{
	private IndexBresenham<T> bham;
	
	/**
	 * Creates a new {@code IndexBresenham}.
	 * 
	 * @param s  a tiled space
	 * 
	 * 
	 * @see TiledSpace
	 */
	public TiledBresenham(TiledSpace<T> s)
	{
		bham = new IndexBresenham<>(s);
	}

	/**
	 * Initializes the {@code TiledBresenham}.
	 * 
	 * @param src  a source coordinate
	 * @param tgt  a target coordinate
	 */
	public void initialize(int[] src, int[] tgt)
	{
		bham.initialize(src, tgt);
	}
	
	/**
	 * Initializes the {@code TiledBresenham}.
	 * 
	 * @param src  a source tiled
	 * @param tgt  a target tiled
	 */
	public void initialize(T src, T tgt)
	{		
		initialize(src.Coordinates(), tgt.Coordinates());
	}


	@Override
	public boolean hasNext()
	{
		return bham.hasNext();
	}

	@Override
	public boolean isIdle()
	{
		return bham.isIdle();
	}
	
	@Override
	public T next()
	{
		return bham.next();
	}
}
