package zeno.util.intel;

/**
 * The {@code Agent} interface defines the basic behavior of an artificial intelligence.
 * <br> Each AI performs the same basic functions:
 * 
 * <ul>
 * <li> Observe the surroundings of the {@code Agent}.
 * <li> Check the internal state of the {@code Agent}.
 * <li> Execute the next move of the {@code Agent}.
 * </ul>
 * 
 * @author Zeno
 * @since Oct 16, 2104
 */
public interface Agent
{	
	/**
	 * Observe the surroundings of the {@code Agent}.
	 */
	public abstract void observe();
	
	/**
	 * Check the internal state of the {@code Agent}.
	 */
	public abstract void checkState();
	
	/**
	 * Execute the next move of the {@code Agent}.
	 */
	public abstract void executeNextMove();
}