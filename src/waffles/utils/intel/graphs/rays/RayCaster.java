package waffles.utils.intel.graphs.rays;

import java.util.Iterator;

import waffles.utils.geom.spaces.index.tiles.Tiled2D;
import waffles.utils.intel.graphs.rays.iterator.RayIterator;

/**
 * A {@code RayCaster} defines an {{@link #iterator()} which performs a raycasting algorithm on a
 * {@code Tiled2D} grid. The field of view is determined by a queue of {@code RayCones}, which is
 * initialized with two cones spanning the intervals [-\u03c0, 0] and [0, +\u03c0]. The iterator
 * pass through all tiles around the source tile by looping around it in diamond shapes of
 * increasing radius. Whenever a tile passes the {{@link #blocksRay(Tiled2D)} test,
 * the current cone is clipped and split along the edges of the tile.
 *
 * @author Waffles
 * @since 17 Nov 2024
 * @version 1.1
 *
 *
 * @param <T>  a tile type
 * @see Iterable
 * @see Tiled2D
 */
public interface RayCaster<T extends Tiled2D> extends Iterable<T>
{
	/**
	 * Returns the tile of the {@code RayCaster}.
	 * 
	 * @return  a center tile
	 */
	public abstract T Tile();
	
	/**
	 * Returns the diagonal of the {@code RayCaster}.
	 * 
	 * @return  a diagonal radius
	 */
	public abstract int Diagonal();	
	
	/**
	 * Returns the ray index of the {@code RayCaster}.
	 * 
	 * @return  a raycaster index
	 * 
	 * 
	 * @see RayIndex
	 */
	public abstract RayIndex RayIndex();
	
	/**
	 * Checks if a tile blocks the {@code RayCaster}.
	 * 
	 * @param tile  a grid tile
	 * @return  {@code true} if it blocks rays
	 */
	public default boolean blocksRay(T tile)
	{
		return false;
	}

	
	@Override
	public default Iterator<T> iterator()
	{
		return new RayIterator<>(this);
	}
}