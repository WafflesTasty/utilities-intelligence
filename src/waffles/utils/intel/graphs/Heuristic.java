package waffles.utils.intel.graphs;

/**
 * A {@code Heuristic} defines costs and estimates between {@code Path} objects.
 *
 * @author Waffles
 * @since 28 Feb 2020
 * @version 1.0
 *
 *
 * @param <O>  an object type
 */
public interface Heuristic<O>
{
	/**
	 * Returns the cost of a full {@code Path}.
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
	
	/**
	 * Iterates over the neighbors of a {@code Path} object.
	 * 
	 * @param tgt  a target object
	 * @return     a neighbour iterable
	 * 
	 * 
	 * @see Iterable
	 */
	public abstract Iterable<O> neighbours(O tgt);
	
	/**
	 * Returns a cost estimate between {@code Path} objects.
	 * 
	 * @param src  a source object
	 * @param tgt  a target object
	 * @return  a cost estimate
	 */
	public abstract float estimate(O src, O tgt);
	
	/**
	 * Returns the cost of a {@code Path} object.
	 * 
	 * @param tgt  a target object
	 * @return     an object cost
	 */
	public abstract float cost(O tgt);
}