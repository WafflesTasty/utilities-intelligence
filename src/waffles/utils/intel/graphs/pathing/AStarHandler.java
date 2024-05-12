package waffles.utils.intel.graphs.pathing;

import waffles.utils.intel.graphs.Path;
import waffles.utils.intel.utilities.cost.Heuristic;
import waffles.utils.sets.keymaps.delegate.JHashMap;
import waffles.utils.sets.queues.ordered.BSQueue;
import waffles.utils.tools.primitives.Floats;

/**
 * An {@code AStarHandler} holds all the intermediate data for an {@code AStarSearch}.
 *
 * @author Waffles
 * @since 02 Apr 2024
 * @version 1.1
 *
 *
 * @param <O>  an object type
 * @see Heuristic
 */
public class AStarHandler<O> implements Heuristic<O>
{
	private Heuristic<O> heur;
	private JHashMap<O, Float> cost;
	private BSQueue<Path<O>> queue;

	/**
	 * Creates a new {@code AStarHandler}.
	 * 
	 * @param h  a path heuristic
	 * @param src  a source object
	 * @param tgt  a target object
	 * 
	 * 
	 * @see Heuristic
	 */
	public AStarHandler(Heuristic<O> h, O src, O tgt)
	{
		queue = new BSQueue<>((p1, p2) ->
		{
			float e1 = estimate(p1.Tail(), tgt);
			float e2 = estimate(p2.Tail(), tgt);

			float c1 = cost(p1) + e1;
			float c2 = cost(p2) + e2;

			return (int) Floats.sign(c1 - c2);
		});
		
		queue.push(new Path<>(src));
		cost = new JHashMap<>();
		heur = h;
	}
	
	/**
	 * Suggest a {@code Path} to the {@code AStarHandler}.
	 * 
	 * @param p  a path
	 * 
	 * 
	 * @see Path
	 */
	public void suggest(Path<O> p)
	{
		O tail = p.Tail();
		// Compute the old cost of the tail.
		float cOld = oldCost(tail);
		// Compute the new cost of the path.
		float cNew = cost(p);
		
		// If the new cost is better...
		if(cNew < cOld)
		{
			// Add the path to the queue.
			cost.put(tail, cNew);
			queue.push(p);
		}
	}
	
	/**
	 * Returns the next {@code Path} in the iteration.
	 * This path has the lowest cost + estimate
	 * value in the queue.
	 * 
	 * @return  a path
	 * 
	 * 
	 * @see Path
	 */
	public Path<O> next()
	{
		return queue.pop();
	}

	
	@Override
	public Iterable<O> neighbours(O tgt)
	{
		return heur.neighbours(tgt);
	}

	@Override
	public float estimate(O src, O tgt)
	{
		return heur.estimate(src, tgt);
	}

	@Override
	public float cost(O tgt)
	{
		return heur.cost(tgt);
	}

	float oldCost(O tgt)
	{
		Float c = cost.get(tgt);
		if(c == null)
		{
			return Floats.MAX_VALUE;
		}
		
		return c;
	}
}