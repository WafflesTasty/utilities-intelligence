package waffles.utils.intel.utilities.cost;

import waffles.utils.intel.utilities.Cost;

/**
 * A {@code CostOptimizer} optimizes the cost of objects.
 *
 * @author Waffles
 * @since 04 Apr 2024
 * @version 1.1
 *
 *
 * @param <I>  an input type
 * @param <O>  an output type
 * @see Cost
 */
public interface CostOptimizer<I, O> extends Cost<O>
{	
	/**
	 * Generates output from a given input.
	 * 
	 * @param in  an input
	 * @return    an output
	 */
	public abstract O generate(I in);
}
