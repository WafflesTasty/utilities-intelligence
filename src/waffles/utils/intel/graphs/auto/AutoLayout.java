package waffles.utils.intel.graphs.auto;

/**
 * An {@code AutoLayout} defines the parameters to generate an {@code Automaton}.
 *
 * @author Waffles
 * @since 12 May 2024
 * @version 1.1
 *
 *
 * @param <T>  a tile type
 * @see AutoTile
 */
public interface AutoLayout<T extends AutoTile>
{			
	/**
	 * Creates a new action from the {@code AutoLayout}.
	 * 
	 * @param tile  an automaton tile
	 * @return      an automaton action
	 * 
	 * 
	 * @see AutoAction
	 */
	public abstract AutoAction create(T tile);
	
	/**
	 * Returns the automaton radius of the {@code AutoLayout}.
	 * This determines the square radius of tiles that
	 * get updated whenever a tile updates.
	 * 
	 * @return  a tile radius
	 */
	public abstract int AutoRadius();
	
	/**
	 * Returns the beat time of the {@code AutoLayout}.
	 * 
	 * @return  a beat time
	 */
	public abstract long Beat();
}