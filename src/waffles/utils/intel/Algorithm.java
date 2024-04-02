package waffles.utils.intel;

/**
 * An {@code Algorithm} executes a sequence of steps before going idle.
 *
 * @author Waffles
 * @since 02 Apr 2024
 * @version 1.1
 * 
 * 
 * @see Runnable
 */
public interface Algorithm extends Runnable
{
	/**
	 * Performs a step in the {@code Algorithm}.
	 */
	public abstract void step();
	
	/**
	 * Checks if the {@code Algorithm} is idle.
	 * 
	 * @return  {@code true} if idling
	 */
	public abstract boolean isIdle();
	

	@Override
	public default void run()
	{
		while(!isIdle())
		{
			step();
		}
	}
}