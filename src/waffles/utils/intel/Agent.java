package waffles.utils.intel;

/**
 * An {@code Agent} defines the basic behavior of an artificial intelligence.
 * </br> Each {@code Agent} takes the same basic steps:
 * 
 * <ul>
 * <li> The {@code Agent} observes its surroundings.</li>
 * <li> The {@code Agent} checks its internal state.</li>
 * <li> The {@code Agent} makes its next move.</li>
 * </ul>
 * 
 * @author Waffles
 * @since Oct 16, 2104
 * @version 1.0
 */
public interface Agent
{	
	/**
	 * The {@code Agent} observes its surroundings.
	 */
	public abstract void observe();
	
	/**
	 * The {@code Agent} checks its internal state.
	 */
	public abstract void checkState();
	
	/**
	 * The {@code Agent} makes its next move.
	 */
	public abstract void executeNextMove();
}