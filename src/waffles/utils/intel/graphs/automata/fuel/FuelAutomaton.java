package waffles.utils.intel.graphs.automata.fuel;

import waffles.utils.intel.graphs.automata.Automaton;
import waffles.utils.intel.graphs.automata.fire.FireAutoAction;
import waffles.utils.intel.graphs.automata.fire.FireAutoTile;

/**
 * A {@code FuelAutomaton} gradually refuels tiles at a constant rate.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 *
 * 
 * @param <T>  a tile type
 * @see FireAutoAction
 * @see FireAutoTile
 * @see Automaton
 */
public class FuelAutomaton<T extends FireAutoTile> extends Automaton<FuelAutoAction, T>
{
	/**
	 * Creates a new {@code FuelAutomaton}.
	 * 
	 * @param lyt  an automaton layout
	 * 
	 * 
	 * @see FuelAutoLayout
	 */
	public FuelAutomaton(FuelAutoLayout<T> lyt)
	{
		super(lyt);
	}
	
	/**
	 * Creates a new {@code FuelAutomaton}.
	 * 
	 * @param beat  a beat time
	 */
	public FuelAutomaton(long beat)
	{
		this(() -> beat);
	}
}