package waffles.utils.intel.utilities.cost;

import waffles.utils.intel.graphs.Path;
import waffles.utils.intel.utilities.Cost;

/**
 * A {@code Heuristic} defines costs and estimates between objects.
 *
 * @author Waffles
 * @since 28 Feb 2020
 * @version 1.0
 *
 *
 * @param <O>  an object type
 * @see Cost
 */
public interface Heuristic<O> extends Cost<O>
{	
	/**
	 * Iterates over the neighbors of an object.
	 * 
	 * @param tgt  a target object
	 * @return     a neighbour iterable
	 * 
	 * 
	 * @see Iterable
	 */
	public abstract Iterable<O> neighbours(O tgt);
	
	/**
	 * Returns a cost estimate between objects.
	 * 
	 * @param src  a source object
	 * @param tgt  a target object
	 * @return  a cost estimate
	 */
	public abstract float estimate(O src, O tgt);

	
	/**
	 * Returns the cost of a {@code Path}.
	 * 
	 * @param path  a target path
	 * @return  a path cost
	 * 
	 * 
	 * @see Path
	 */
	public default float cost(Path<O> path)
	{
		float cost = 0f;
		for(O node : path)
		{
			cost += cost(node);
		}
		
		return cost;
	}
}