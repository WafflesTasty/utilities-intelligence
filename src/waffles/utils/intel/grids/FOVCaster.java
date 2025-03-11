package waffles.utils.intel.grids;

import java.util.Iterator;

import waffles.utils.geom.spaces.index.tiles.Tiled2D;
import waffles.utils.intel.grids.rays.FOVIterator;

/**
 * A {@code FOVCaster} defines an {{@link #iterator()} which performs a raycasting algorithm on a
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
public interface FOVCaster<T extends Tiled2D> extends Iterable<T>
{
	/**
	 * Returns the tile of the {@code FOVCaster}.
	 * 
	 * @return  a center tile
	 */
	public abstract T Tile();
	
	/**
	 * Returns the diagonal of the {@code FOVCaster}.
	 * 
	 * @return  a diagonal radius
	 */
	public abstract int Diagonal();	
	
	
	/**
	 * Returns the source radius of the {@code FOVCaster}.
	 * This can be any value in the range of [0.0, 1.0], and
	 * defines the radius of the circle within the source
	 * tile from which rays will be cast.
	 * 
	 * @return  a source radius
	 */
	public default float SourceRadius()
	{
		return 0f;
	}

	/**
	 * Returns the target radius of the {@code FOVCaster}.
	 * This can be any value in the range of [0.0, 1.0], and
	 * defines the radius of the circle within the target
	 * tile towards which rays will be cast.
	 * 
	 * @return  a target radius
	 */
	public default float TargetRadius()
	{
		return 0.5f;
	}
	
	/**
	 * Checks if a tile blocks the {@code FOVCaster}.
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
		return new FOVIterator<>(this);
	}
}