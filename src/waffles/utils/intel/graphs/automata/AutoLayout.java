package waffles.utils.intel.graphs.automata;

/**
 * An {@code AutoLayout} defines the parameters to generate an {@code Automaton}.
 *
 * @author Waffles
 * @since 12 May 2024
 * @version 1.1
 *
 *
 * @param <A>  an action type
 * @param <T>  a tile type
 * @see AutoAction
 * @see AutoTile
 */
public interface AutoLayout<A extends AutoAction, T extends AutoTile>
{			
	/**
	 * Creates a new action from the {@code AutoLayout}.
	 * 
	 * @param tile  an automaton tile
	 * @return      an automaton action
	 */
	public abstract A create(T tile);
	
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