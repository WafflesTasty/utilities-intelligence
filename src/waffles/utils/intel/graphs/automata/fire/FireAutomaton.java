package waffles.utils.intel.graphs.automata.fire;

import waffles.utils.intel.graphs.automata.Automaton;

/**
 * A {@code FireAutomaton} mimicks the behavior of burning flames.
 * Each tile which is dead or burning updates its burning state according
 * to its burning neighbours. A burnt out tile becomes undead,
 * and cannot burn again.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 *
 * 
 * @param <T>  a tile type
 * @see FireAutoTile
 * @see Automaton
 */
public class FireAutomaton<T extends FireAutoTile> extends Automaton<T>
{
	/**
	 * Creates a new {@code FireAutomaton}.
	 * 
	 * @param lyt  an automaton layout
	 * 
	 * 
	 * @see FireAutoLayout
	 */
	public FireAutomaton(FireAutoLayout<T> lyt)
	{
		super(lyt);
	}
	
	/**
	 * Creates a new {@code FireAutomaton}.
	 * 
	 * @param beat  a beat time
	 */
	public FireAutomaton(long beat)
	{
		this(() -> beat);
	}
}