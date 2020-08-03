package zeno.util.intel.graphs;

/**
 * The {@code Heuristic} interface defines a basic cost estimate between objects.
 *
 * @author Zeno
 * @since 28 Feb 2020
 * @version 1.0
 *
 *
 * @param <O>  an object type
 */
public interface Heuristic<O>
{
	/**
	 * Returns an object's neighbours in the {@code Heuristic}.
	 * 
	 * @param obj  a target object
	 * @return  a set of neighbours
	 * 
	 * 
	 * @see Iterable
	 */
	public abstract Iterable<O> neighbours(O obj);
	
	/**
	 * Returns a cost estimate between objects in the {@code Heuristic}.
	 * 
	 * @param src  a source object
	 * @param tgt  a target object
	 * @return  a cost estimate
	 */
	public abstract float estimate(O src, O tgt);
	
	/**
	 * Returns an actual cost between objects in the {@code Heuristic}.
	 * 
	 * @param src  a source object
	 * @param tgt  a target object
	 * @return  a cost estimate
	 */
	public abstract float cost(O src, O tgt);
}