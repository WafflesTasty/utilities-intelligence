package waffles.utils.intel.graphs.vnoi.beach.events;

import waffles.utils.algebra.elements.linear.vector.fixed.Vector2;
import waffles.utils.intel.graphs.vnoi.Beach;
import waffles.utils.intel.graphs.vnoi.Voronoi;
import waffles.utils.intel.graphs.vnoi.beach.Event;
import waffles.utils.intel.graphs.vnoi.beach.Front;
import waffles.utils.intel.graphs.vnoi.beach.fronts.Head;
import waffles.utils.intel.graphs.vnoi.beach.fronts.Wedge;
import waffles.utils.tools.primitives.Floats;

/**
 * An {@code Insert} event occurs when a new front is added to the {@code Beach}.
 * Two vertices are added, separating the underlying front through a line.
 *
 * @author Waffles
 * @since 24 Sep 2024
 * @version 1.0
 * 
 * 
 * @see Event
 */
public class Insert extends Event
{
	private int eVal;
	private Head hNew;
	
	/**
	 * Creates a new {@code Insert} events.
	 * 
	 * @param h  a beach head
	 * @param e  an error margin
	 * 
	 * 
	 * @see Head
	 */
	public Insert(Head h, int e)
	{
		eVal = e;
		hNew = h;
	}

	
	@Override
	public void modify(Voronoi<?> v)
	{
		System.out.println("Inserting (" + hNew.X() + ", " + hNew.Y() + ").");
		
		Beach<?> b = v.Beach();
		
		Front fOld = b.search(hNew);
		// If no underlying beach
		// front was found...
		if(fOld == null)
		{
			System.out.println("Inserted as root (" + hNew + ").");
			// ...add as a root.
			b.setRoot(hNew);
			return;
		}
		
		// If the front is a wedge...
		if(fOld instanceof Wedge)
		{
			Wedge wOld = (Wedge) fOld;
			System.out.println("Parent is a wedge.");
			
			Head h1 = wOld.LHead();
			Head h2 = wOld.RHead();
			
			// Remove any old event.
			Event e1 = h1.Event();
			if(e1 != null)
			{
				System.out.println("Removing left delete event.");
				h1.setEvent(null);
				b.remove(e1);
			}

			Event e2 = h2.Event();
			if(e2 != null)
			{
				System.out.println("Removing right delete event.");
				h2.setEvent(null);
				b.remove(e2);
			}			
			
			
			// ...create the new beach wedges.
			Wedge lWed = new Wedge(b, h1, hNew);
			Wedge rWed = new Wedge(b, hNew, h2);
			System.out.println("Adding child nodes (" + lWed + ", " + rWed + ").");

			// Replace the old front.
			rWed.setLChild(lWed);
			lWed.setLChild(fOld.LChild());			
			rWed.setRChild(fOld.RChild());
			fOld.replace(rWed);
			
			// Check for delete events.
			b.checkDelete(lWed.LHead());
			b.checkDelete(rWed.RHead());
			
			// Add a new vertex.
			v.addVertex(wOld.Origin());
			return;
		}		
		
		
		float x1 = fOld.X();
		float x2 = hNew.X();
		
		float y1 = fOld.Y();
		float y2 = hNew.Y();
		
		// If the front is a head...
		if(fOld instanceof Head)
		{
			Head hOld = (Head) fOld;
			System.out.println("Parent is a head.");
			// ...create the new beach wedges.
			Wedge lWed = new Wedge(b, hOld, hNew);
			Wedge rWed = new Wedge(b, hNew, hOld);

			// If the head is the root...
			if(fOld == b.Root())
			{
				System.out.println("Replacing the root with wedges (" + lWed + ", " + rWed + ").");
				// ...replace the root.
				rWed.setLChild(lWed);
				b.setRoot(rWed);
				return;
			}
			
			// Remove any old event.
			Event evt = hOld.Event();
			if(evt != null)
			{
				System.out.println("Removing the old delete event.");
				hOld.setEvent(null);
				b.remove(evt);
			}
			
			// If the heads are at the same height...
			if(Floats.isEqual(y1, y2, eVal))
			{
				// ...assign the new beach wedge.
				Wedge mWed = x1 < x2 ? lWed : rWed;
				System.out.println("Replacing the old head with a wedge " + mWed + ".");
				// Replace the old head.
				hOld.replace(mWed);
				return;
			}
						
			System.out.println("Replacing the old head with wedges (" + lWed + ", " + rWed + ").");
			// Replace the old front.
			rWed.setLChild(lWed);
			hOld.replace(rWed);
			
			// Check for delete events.
			b.checkDelete(lWed.LHead());
			b.checkDelete(rWed.RHead());
		}
	}

	@Override
	public Vector2 Origin()
	{
		return hNew.Origin();
	}
}