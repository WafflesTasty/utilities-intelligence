package waffles.utils.intel.graphs.rays.index;

import waffles.utils.intel.graphs.rays.RayCaster;

/**
 * A {@code RayLayout} provides the parameters for a functional {@link RayCaster}.
 *
 * @author Waffles
 * @since 17 Nov 2024
 * @version 1.1
 */
@FunctionalInterface
public interface RayLayout
{
	/**
	 * Returns the max diagonal of the {@code RayLayout}.
	 * This determines how far the rays will be cast in the grid
	 * and is equivalent to the maximum number of orthogonal
	 * steps you can take to reach a target tile from
	 * the source tile.
	 * 
	 * @return  a max diagonal
	 */
	public abstract int MaxDiagonal();
	
	
	/**
	 * Returns the source radius of the {@code RayLayout}.
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
	 * Returns the target radius of the {@code RayLayout}.
	 * This can be any value in the range of [0.0, 1.0], and
	 * defines the radius of the circle within the target
	 * tile towards which rays will be cast.
	 * 
	 * @return  a target radius
	 */
	public default float TargetRadius()
	{
		return 1f;
	}
}