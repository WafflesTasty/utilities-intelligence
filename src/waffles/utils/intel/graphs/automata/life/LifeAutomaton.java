package waffles.utils.intel.graphs.automata.life;

import waffles.utils.intel.graphs.automata.Automaton;
import waffles.utils.intel.graphs.automata.life.layout.ConwayLayout;

/**
 * A {@code LifeAutomaton} mimicks the behavior of Conway's game of life.
 * Each tile considers the amount of live neighbors. The tile may choose to
 * flip its mortality, or keep it, depending on whether the live neighbor
 * count is within a certain interval. These intervals may be defined
 * within the supplied {@code LifeAutoLayout}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 *
 * 
 * @param <T>  a tile type
 * @see LifeAutoTile
 * @see Automaton
 */
public class LifeAutomaton<T extends LifeAutoTile> extends Automaton<T>
{
	/**
	 * Creates a new {@code LifeAutomaton}.
	 * 
	 * @param lyt  an automaton layout
	 * 
	 * 
	 * @see LifeAutoLayout
	 */
	public LifeAutomaton(LifeAutoLayout<T> lyt)
	{
		super(lyt);
	}
	
	/**
	 * Creates a new {@code LifeAutomaton}.
	 * By default, a {@code ConwayLayout} is used.
	 * 
	 * @param beat  a beat time
	 */
	public LifeAutomaton(long beat)
	{
		this(new ConwayLayout<>(beat));
	}
}