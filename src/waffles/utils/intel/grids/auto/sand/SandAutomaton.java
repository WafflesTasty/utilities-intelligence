package waffles.utils.intel.grids.auto.sand;

import waffles.utils.intel.grids.Automaton;

/**
 * A {@code SandAutomaton} mimicks the behavior of falling sand particles.
 * Each empty tile attempts to pull sand from its neighbors. This behavior
 * is guided by a direction of gravity, which is the y-axis by default.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 *
 *
 * @param <T>  a tile type
 * @see SandAutoTile
 * @see Automaton
 */
public class SandAutomaton<T extends SandAutoTile> extends Automaton<T>
{
	/**
	 * Creates a new {@code SandAutomaton}.
	 * 
	 * @param lyt  an automaton layout
	 * 
	 * 
	 * @see SandAutoLayout
	 */
	public SandAutomaton(SandAutoLayout<T> lyt)
	{
		super(lyt);
	}
	
	/**
	 * Creates a new {@code SandAutomaton}.
	 * By default, gravity is directed southward.
	 * 
	 * @param beat  a beat time
	 */
	public SandAutomaton(long beat)
	{
		this(() -> beat);
	}
}