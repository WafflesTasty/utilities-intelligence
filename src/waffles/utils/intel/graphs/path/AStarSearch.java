package waffles.utils.intel.graphs.path;

import waffles.utils.intel.graphs.Path;
import waffles.utils.intel.utilities.cost.Heuristic;
import waffles.utils.phys.utilities.events.stepped.SteppedEvent;

/**
 * The {@code AStarSearch} algorithm computes an optimal path between connected objects.
 * Its runtime is determined by a {@code Heuristic}, which describes neighboring objects,
 * and can assign each of them a cost and estimate. The initial values are set with the
 * {@code #start(Object, Object)} method, after which {@code #step()} iterates the
 * algorithm, while {@code #run()} completes it. The intermediate and
 * resulting paths are returned with {@code #Path()}.
 *  
 * @author Waffles
 * @since 01 Mar 2020
 * @version 1.0
 * 
 * 
 * @see <a href="http://en.wikipedia.org/wiki/A*">A* search algorithm</a>
 * @param <O>  an object type
 * @see SteppedEvent
 */
public class AStarSearch<O> extends SteppedEvent
{	
	private Heuristic<O> heur;
	private AStarHandler<O> data;
	
	private Path<O> path;
	private O target;
	
	/**
	 * Creates a new {@code AStarSearch}.
	 * 
	 * @param b  a beat time
	 * @param h  a path heuristic
	 * 
	 * 
	 * @see Heuristic
	 */
	public AStarSearch(long b, Heuristic<O> h)
	{
		super(b);
		heur = h;
	}
	
	/**
	 * Starts the {@code AStarSearch} algorithm.
	 * 
	 * @param src  a source object
	 * @param tgt  a target object
	 */
	public void start(O src, O tgt)
	{
		data = new AStarHandler<>(heur, src, tgt);
		target = tgt;
		run();
	}
	
	/**
	 * Returns the current optimal path.
	 * 
	 * @return  a result path
	 * 
	 * 
	 * @see Path
	 */
	public Path<O> Path()
	{
		return path;
	}


	@Override
	public void onPulse(long beat)
	{
		// Find the next path.
		path = data.next();
		// If none was found...
		if(path == null)
		{
			// Finish the search.
			pause(); return;
		}
		
		// If it reaches the destination...
		if(path.endsAt(target))
		{
			// Finish the search.
			pause(); return;
		}

		
		// Otherwise, for all neighbour nodes...
		for(O next : data.neighbours(path.Tail()))
		{			
			// Connect the path with the neighbour.
			Path<O> pNew = new Path<>(path, next);
			// Suggest it as an optimal path.
			data.suggest(pNew);
		}
	}
}