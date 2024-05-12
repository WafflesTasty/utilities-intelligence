package waffles.utils.intel.graphs.automata;

import waffles.utils.tools.patterns.semantics.Coordinated;

/**
 * An {@code AutoAction} defines the local behavior of an {@code Automaton}.
 *
 * @author Waffles
 * @since 12 May 2024
 * @version 1.1
 *
 * 
 * @see Coordinated
 */
public interface AutoAction extends Coordinated
{	
	/**
	 * Resolves the state of the {@code AutoAction}.
	 * 
	 * @return  {@code true} if changes occurred
	 */
	public abstract boolean resolve();
	
	/**
	 * Computes the state of the {@code AutoAction}.
	 */
	public abstract void compute();
}