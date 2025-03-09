package waffles.utils.intel.graphs.vnoi;

import waffles.utils.geom.spatial.data.unary.Positioned2D;
import waffles.utils.intel.graphs.vnoi.beach.Event;
import waffles.utils.intel.graphs.vnoi.beach.Front;
import waffles.utils.intel.graphs.vnoi.beach.events.Delete;
import waffles.utils.intel.graphs.vnoi.beach.fronts.Head;
import waffles.utils.intel.graphs.vnoi.beach.fronts.Wedge;
import waffles.utils.phys.utilities.events.SynchroEvent;
import waffles.utils.sets.queues.ordered.BSQueue;
import waffles.utils.sets.trees.binary.search.IOTree;
import waffles.utils.tools.patterns.semantics.Idleable;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Beach} defines the state of the beach line in a {@code Voronoi} algorithm.
 * This class is implemented as an {@code IOTree}, with node representing a vertex.
 * By choice, the tree sorts its node according to the vertex x-coordinates.
 * Accordingly, the sweep line should travel in increasing y-coordinate.
 *
 * @author Waffles
 * @since 18 Sep 2024
 * @version 1.0
 * 
 * 
 * @param <P>  a positioned type
 * @see Positioned2D
 * @see SynchroEvent
 * @see Idleable
 * @see IOTree
 * @see Front
 */
public class Beach<P extends Positioned2D> extends IOTree<Front, Front> implements Idleable, SynchroEvent
{	
	private float ySweep;
	private Voronoi<P> vnoi;	
	private BSQueue<Event> events;
	
	/**
	 * Creates a new {@code Beach}.
	 * 
	 * @param v  a voronoi graph
	 * 
	 * 
	 * @see Voronoi
	 */
	public Beach(Voronoi<P> v)
	{
		super((f1, f2) ->
		{
			float dx = f2.X() - f1.X();
			dx = Floats.sign(dx);
			return (int) dx;
		});
		
		ySweep = Floats.MIN_VALUE;
		events = new BSQueue<>();
		vnoi = v;
	}
	
	
	/**
	 * Adds an event to the {@code Beach}.
	 * 
	 * @param e  a beach event
	 * 
	 * 
	 * @see Event
	 */
	public void add(Event e)
	{
		events.push(e);
	}
	
	/**
	 * Removes an event from the {@code Beach}.
	 * 
	 * @param e  a beach event
	 * 
	 * 
	 * @see Event
	 */
	public void remove(Event e)
	{
		events.remove(e);
	}
	
	/**
	 * Checks a delete event in the {@code Beach}.
	 * 
	 * @param h  a beach head
	 * 
	 * 
	 * @see Head
	 */
	public void checkDelete(Head h)
	{		
		System.out.println("Checking delete for " + h + ".");
		Delete evt = new Delete(h);
		if(evt.validate(ySweep))
		{
			events.push(evt);
			h.setEvent(evt);
		}		
	}
			
	/**
	 * Returns the sweepline of the {@code Beach}.
	 * 
	 * @return  a sweep y-coordinate
	 */
	public float YSweep()
	{
		return ySweep;
	}
	
		
	@Override
	public Front Root()
	{
		return super.Root();
	}
	
	@Override
	public Front search(Front f)
	{
		Front s = super.search(f);
		if(s instanceof Wedge)
		{
			int comp = compare(f, s);
			if(comp < 0)
				return s.LHead();
			if(comp > 0)
				return s.RHead();
		}
		
		return s;
	}	
	
	@Override
	public Front createNode(Object... vals)
	{
		Head h1 = (Head) vals[0];
		Head h2 = (Head) vals[1];
		
		return new Wedge(this, h1, h2);
	}
		
	@Override
	public void onUpdate(long time)
	{
		if(!events.isEmpty())
		{
			Event e = events.pop();
			
			ySweep = e.Y();
			e.modify(vnoi);
		}
	}
	
	@Override
	public boolean isIdle()
	{
		return events.isEmpty();
	}
	
	@Override
	public void clear()
	{
		super.clear();
		ySweep = Floats.MIN_VALUE;
		events.clear();
	}
}