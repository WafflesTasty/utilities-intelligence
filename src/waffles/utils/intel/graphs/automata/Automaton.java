package waffles.utils.intel.graphs.automata;

import waffles.utils.phys.utilities.events.PulseEvent;
import waffles.utils.sets.indexed.IndexedSet;

/**
 * An {@code Automaton} manages a cellular automata algorithm.
 * Its behavior is guided by an {@code AutoLayout}, which contains
 * all the necessary data to steer cellular automaton behavior.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 *
 * 
 * @param <T> a tile type
 * @see IndexedSet
 * @see PulseEvent
 * @see AutoTile
 */
public class Automaton<T extends AutoTile> extends PulseEvent implements IndexedSet<AutoAction>
{
	private AutoLayout<T> layout;
	private IndexedSet<T> target;
	
	private AutoTree action, buffer;
	
	/**
	 * Creates a new {@code Automaton}.
	 * 
	 * @param lyt  an automaton layout
	 * 
	 * 
	 * @see AutoLayout
	 */
	public Automaton(AutoLayout<T> lyt)
	{
		super(lyt.Beat());
		layout = lyt;
	}
	
	/**
	 * Changes the index of the {@code Automaton}.
	 * 
	 * @param tgt  an indexed set
	 * 
	 * 
	 * @see IndexedSet
	 */
	public void setIndex(IndexedSet<T> tgt)
	{
		action = new AutoTree(this, tgt);
		target = tgt;
	}
	
	/**
	 * Enables a coordinate in the {@code Automaton}.
	 * 
	 * @param crd  an index coordinate
	 */
	public void enable(int... crd)
	{
		T tile = target.get(crd);
		action.enable(tile);
	}

	/**
	 * Creates an action for the {@code Automaton}.
	 * 
	 * @param crd  an index coordinate
	 * @return  an automaton action
	 * 
	 * 
	 * @see AutoAction
	 */
	public AutoAction create(int... crd)
	{
		T tile = target.get(crd);
		return layout.create(tile);
	}
	
	/**
	 * Returns the radius of the {@code Automaton}.
	 * 
	 * @return  a tile radius
	 */
	public int AutoRadius()
	{
		return layout.AutoRadius();
	}
	
	/**
	 * Enables the entire {@code Automaton}.
	 */
	public void enableAll()
	{
		action.enableAll();
	}
	
		
	@Override
	public AutoAction get(int... coords)
	{
		return action.get(coords);
	}
	
	@Override
	public void onPulse(long beat)
	{
		for(AutoAction act : action.VisibleObjects())
		{
			act.compute();
		}
		

		buffer = action;
		action = new AutoTree(this, target);
		for(AutoAction act : buffer.VisibleObjects())
		{
			if(act.resolve())
			{
				int[] crd = act.Coordinates();
				T tile = target.get(crd);
				action.enable(tile);
			}
		}
	}
	
	@Override
	public int[] Dimensions()
	{
		return target.Dimensions();
	}
}