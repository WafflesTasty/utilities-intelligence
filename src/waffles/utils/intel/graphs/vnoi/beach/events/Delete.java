package waffles.utils.intel.graphs.vnoi.beach.events;

import waffles.utils.algebra.elements.linear.vector.fixed.Vector2;
import waffles.utils.geom.collidable.axial.spheroid.Circle;
import waffles.utils.geom.utilities.constants.Dial;
import waffles.utils.intel.graphs.vnoi.Beach;
import waffles.utils.intel.graphs.vnoi.Voronoi;
import waffles.utils.intel.graphs.vnoi.beach.Event;
import waffles.utils.intel.graphs.vnoi.beach.Front;
import waffles.utils.intel.graphs.vnoi.beach.fronts.Head;
import waffles.utils.intel.graphs.vnoi.beach.fronts.Wedge;

/**
 * A {@code Delete} event occurs when three beach heads intersect in a point.
 * The middle head is deleted, creating a vertex in the {@code Voronoi} tree.
 * 
 * @author Waffles
 * @since 24 Sep 2024
 * @version 1.1
 * 
 * 
 * @see Event
 */
public class Delete extends Event
{
	private Head hOld;
	private Circle circ;

	/**
	 * Creates a new {@code Delete} event.
	 * 
	 * @param h  a beach head
	 * 
	 * 
	 * @see Head
	 */
	public Delete(Head h)
	{
		hOld = h;
	}
		
	/**
	 * Validates the {@code Delete} event.
	 * 
	 * @param yCurr  a beach line
	 * @return  {@code true} if the event is valid
	 */
	public boolean validate(float yCurr)
	{
		// If no head was provided...
		if(hOld == null)
		{
			System.out.println("No head was provided.");
			// ...do nothing.
			return false;
		}
		
		Head prev = hOld.LHead();
		Head next = hOld.RHead();
		// If the prev or next head is missing...
		if(prev == null || next == null)
		{
			System.out.println("No surrounding heads found.");
			// ...do nothing.
			return false;
		}
		
		
		Vector2 p1 = prev.Origin();
		Vector2 p2 = hOld.Origin();
		Vector2 p3 = next.Origin();
		// If the middle head is the highest...
		if(p1.Y() <= p2.Y() && p3.Y() <= p2.Y())
		{
			System.out.println("The middle head is the highest.");
			// ...do nothing.
			return false;
		}
		
		// If the points do not turn clockwise...
		if(Dial.of(p1, p2, p3) != Dial.CLOCKWISE)
		{
			System.out.println("The points turn counterclockwise.");
			// ...do nothing.
			return false;
		}
		
		System.out.print("(" + p1.X() + " : " + p1.Y() + ")");
		System.out.print("(" + p2.X() + " : " + p2.Y() + ")");
		System.out.print("(" + p3.X() + " : " + p3.Y() + ")");
		System.out.println();
		
		circ = Circle.through(p1, p2, p3);
		// If no circ exists through the points...
		if(circ == null)
		{
			System.out.println("No circle exists through the points.");
			// ...do nothing.
			return false;
		}

		// If the y-coordinate is in the past...
		if(Y() < yCurr)
		{
			System.out.println("The delete event is in the past.");
			// ...do nothing.
			return false;
		}
		
//		Not necessary, should never happen.
//		Event e = hOld.Event();
//		if(e != null)
//		{
//			// If an earlier delete already exists...
//			if(e.Y() <= Y())
//			{
//				System.out.println("An earlier delete already exists.");
//				// ...do nothing.
//				return false;			
//			}			
//		}
		
		System.out.println("Validation successfull!");
		// Otherwise, deleting is valid.
		return true;
	}

		
	@Override
	public void modify(Voronoi<?> v)
	{
		System.out.println("Deleting (" + hOld.X() + ", " + hOld.Y() + ").");

		Beach<?> b = v.Beach();
		
		Wedge lWed = hOld.prev();
		Wedge rWed = hOld.next();
		
		Head h1 = lWed.LHead();
		Head h2 = rWed.RHead();

		// Remove any old event.
		Event e1 = h1.Event();
		if(e1 != null)
		{
			h1.setEvent(null);
			b.remove(e1);
		}

		Event e2 = h2.Event();
		if(e2 != null)
		{
			h2.setEvent(null);
			b.remove(e2);
		}			
		
		// Create the new beach wedge.
		Wedge mWed = new Wedge(b, h1, h2);

		// Replace the old wedges.
		if(hOld.Parent() == lWed)
		{
			Front lchild = lWed.LChild();
			mWed.setLChild(lchild);
			lWed.replace(mWed);
			rWed.delete();
			
			// Check for delete events.
			b.checkDelete(mWed.LHead());
			b.checkDelete(mWed.RHead());

			// Add a new vertex.
			v.add(mWed.Origin());
			return;
		}

		Front rchild = rWed.RChild();
		mWed.setRChild(rchild);
		rWed.replace(mWed);
		lWed.delete();
		
		b.checkDelete(mWed.LHead());
		b.checkDelete(mWed.RHead());
		
		// Add a new vertex.
		v.add(mWed.Origin());
	}

	@Override
	public Vector2 Origin()
	{
		return new Vector2(X(), Y());
	}

	
	@Override
	public float X()
	{
		return circ.X();
	}
	
	@Override
	public float Y()
	{
		return circ.Y() + circ.Radius();
	}
}