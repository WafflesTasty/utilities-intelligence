package waffles.utils.intel.utilities;

/***
 * The {@code Cost} interface can compute the cost of a type of objects.
 *
 * @author Waffles
 * @since 04 Apr 2024
 * @version 1.1
 *
 *
 * @param <O>  an object type
 */
public interface Cost<O>
{
	/**
	 * Returns the cost of an object.
	 * 
	 * @param tgt  a target object
	 * @return     an object cost
	 */
	public abstract float cost(O tgt);
}