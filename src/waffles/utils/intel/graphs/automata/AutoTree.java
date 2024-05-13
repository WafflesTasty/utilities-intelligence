package waffles.utils.intel.graphs.automata;

import waffles.utils.sets.indexed.IndexedSet;
import waffles.utils.sets.trees.indexed.enums.ChristmasTree;
import waffles.utils.sets.utilities.iterators.IndexKeys;

/**
 * An {@code AutoTree} maintains a view of the active tiles in an {@code Automaton}.
 * Each active tile corresponds to an {@code AutoAction} in a {@code ChristmasTree},
 * which allows the {@code Automaton} to quickly iterate active tiles.
 *
 * @author Waffles
 * @since 12 May 2024
 * @version 1.1
 * 
 * 
 * @see ChristmasTree
 * @see AutoAction
 */
public class AutoTree extends ChristmasTree<AutoAction>
{
	private Automaton<?> parent;
	
	/**
	 * Creates a new {@code AutoTree}.
	 * 
	 * @param atm  a parent automaton
	 * @param tgt  a target space
	 * 
	 * 
	 * @see IndexedSet
	 * @see Automaton
	 */
	public AutoTree(Automaton<?> atm, IndexedSet<?> tgt)
	{
		super(tgt.Dimensions());
		parent = atm;
	}

	
	private Iterable<int[]> Keys(int[] min, int[] max)
	{
		return () -> new IndexKeys(this, min, max);
	}
	
	/**
	 * Enables a square section in the {@code AutoTree}.
	 * This generates {@code AutoActions} for the
	 * area between the given min and max.
	 * 
	 * @param min  an index minimum
	 * @param max  an index maximum
	 */
	public void enable(int[] min, int[] max)
	{
		show(min, max);
		for(int[] keys : Keys(min, max))
		{
			if(contains(keys))
			{
				put(parent.create(keys), keys);
			}
		}
	}
	
	/**
	 * Enables a single tile in the {@code AutoTree}.
	 * This generates {@code AutoActions} for the
	 * given rectangular radius around the tile,
	 * and makes this region visible.
	 * 
	 * @param tile  an automaton tile
	 * 
	 * 
	 * @see AutoTile
	 */
	public void enable(AutoTile tile)
	{
		int rad = parent.AutoRadius();
		int[] crd = tile.Coordinates();
		
		int[] min = new int[crd.length];
		int[] max = new int[crd.length];
		
		for(int i = 0; i < crd.length; i++)
		{
			min[i] = crd[i] - rad;
			max[i] = crd[i] + rad;
		}
		
		
		enable(min, max);
	}

	/**
	 * Enables all tiles in the {@code AutoTree}.
	 */
	public void enableAll()
	{
		enable(Minimum(), Maximum());
	}
}