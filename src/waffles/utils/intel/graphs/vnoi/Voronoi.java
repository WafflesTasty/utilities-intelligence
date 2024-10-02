package waffles.utils.intel.graphs.vnoi;

import waffles.utils.algebra.elements.linear.vector.fixed.Vector2;
import waffles.utils.geom.spatial.data.unary.Positioned2D;
import waffles.utils.intel.graphs.vnoi.beach.events.Insert;
import waffles.utils.intel.graphs.vnoi.beach.fronts.Head;
import waffles.utils.phys.utilities.events.stepped.SteppableEvent;
import waffles.utils.sets.indexed.delegate.List;
import waffles.utils.sets.trees.Tree;
import waffles.utils.tools.collections.Iterables;

/**
 * The {@code Voronoi} class constructs a Voronoi diagram using Fortune's algorithm.
 *
 * @author Waffles
 * @since 22 Sep 2024
 * @version 1.1
 *
 * 
 * @param <P>  a positioned type
 * @see <a href="https://en.wikipedia.org/wiki/Fortune%27s_algorithm">Fortune&#39s algorithm</a>
 * @see SteppableEvent
 * @see Positioned2D
 * @see Tree
 */
public class Voronoi<P extends Positioned2D> extends Tree implements SteppableEvent
{
	/**
	 * A {@code Voronoi.Layout} defines the parameters of a {@code Voronoi} algorithm.
	 *
	 * @author Waffles
	 * @since 24 Sep 2024
	 * @version 1.1
	 */
	@FunctionalInterface
	public static interface Layout
	{		
		/**
		 * Returns the error margin of the {@code Layout}.
		 * 
		 * @return  an error margin
		 */
		public default int ErrorMargin()
		{
			return 3;
		}
		
		/**
		 * Returns the beat time of the {@code Layout}.
		 * 
		 * @return  a beat time
		 */
		public abstract long BeatTime();
	}
	
	
	private List<Vector2> pts;
	
	private Layout layout;
	private Beach<P> beach;
	private Stride stride;
	
	/**
	 * Creates a new {@code Voronoi}.
	 * 
	 * @param lyt  a voronoi layout
	 * 
	 * 
	 * @see Layout
	 */
	public Voronoi(Layout lyt)
	{
		pts = new List<>();
		stride = new Stride(this);
		beach = new Beach<>(this);
		layout = lyt;
	}
	
	public void add(Vector2 p)
	{
		pts.add(p);
	}
	
	public Iterable<Vector2> Points()
	{
		return pts;
	}
	
	
	/**
	 * Loads the {@code Voronoi} tree with positions.
	 * 
	 * @param set  a set of positioned objects
	 * 
	 * 
	 * @see Positioned2D
	 */
	public void load(P... set)
	{
		load(Iterables.of(set));
	}
	
	/**
	 * Loads the {@code Voronoi} tree with positions.
	 * 
	 * @param set  a set of positioned objects
	 * 
	 * 
	 * @see Iterable
	 */
	public void load(Iterable<P> set)
	{
		clear();
	
		int e = layout.ErrorMargin();
		// Initialize the event queue.
		for(Positioned2D p : set)
		{
			Head h = new Head(beach, p);
			Insert evt = new Insert(h, e);
			beach.add(evt);
		}
	}
	
	/**
	 * Returns the beach of the {@code Voronoi} tree.
	 * 
	 * @return  a voronoi beach
	 * 
	 * 
	 * @see Beach
	 */
	public Beach<?> Beach()
	{
		return beach;
	}
	

	@Override
	public Stride Pulse()
	{
		return stride;
	}

	@Override
	public void onPulse(long beat)
	{
		beach.onUpdate(beat);
	}


	@Override
	public boolean isIdle()
	{
		return Pulse().isIdle()
			|| beach.isIdle();
	}

	@Override
	public long BeatTime()
	{
		return layout.BeatTime();
	}
	
	@Override
	public void clear()
	{
		super.clear();
		beach.clear();
		pts.clear();
	}
}