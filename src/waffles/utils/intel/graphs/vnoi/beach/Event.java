package waffles.utils.intel.graphs.vnoi.beach;

import waffles.utils.geom.spatial.data.unary.Positioned2D;
import waffles.utils.intel.graphs.vnoi.Voronoi;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Beach.Event} defines an event for the {@code Voronoi} algorithm.
 * These events are queued according to their y-value, with each event targeting
 * a {@code Beach.Front}. Two subclasses exist, one which inserts a new beach
 * front into the algorithm, and another which deletes an existing front.
 *
 * @author Waffles
 * @since 22 Sep 2024
 * @version 1.0
 *
 * 
 * @see Positioned2D
 * @see Comparable
 */
public abstract class Event implements Positioned2D, Comparable<Event>
{
	/**
	 * Modifies a {@code Voronoi} in the {@code Event}.
	 * 
	 * @param vnoi  a voronoi graph
	 * 
	 * 
	 * @see Voronoi
	 */
	public abstract void modify(Voronoi<?> vnoi);
		
	
	@Override
	public int compareTo(Event e)
	{
		float dy = Y() - e.Y();
		dy = Floats.sign(dy);
		return (int) dy;
	}
}