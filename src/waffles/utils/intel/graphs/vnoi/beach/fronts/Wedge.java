package waffles.utils.intel.graphs.vnoi.beach.fronts;

import waffles.utils.geom.spatial.data.unary.Positioned2D;
import waffles.utils.intel.graphs.vnoi.Beach;
import waffles.utils.intel.graphs.vnoi.beach.Event;
import waffles.utils.intel.graphs.vnoi.beach.Front;
import waffles.utils.lang.enums.Extreme;
import waffles.utils.tools.primitives.Floats;

/**
 * A {@code Wedge} defines a {@code Front} as an intersection of two {@code Heads}.
 * The intersection of two parabolas moving along the sweep line will always
 * draw out a straight line, which is defined by the {@code Wedge}.
 *
 * @author Waffles
 * @since 29 Sep 2024
 * @version 1.1
 *
 * 
 * @see Front
 */
public class Wedge extends Front
{
	private Event e1, e2;
	private Positioned2D p1, p2;
	
	/**
	 * Creates a new {@code Wedge}.
	 * 
	 * @param b   a parent beach
	 * @param h1  a minimal head
	 * @param h2  a maximum head
	 * 
	 * 
	 * @see Beach
	 * @see Head
	 */
	public Wedge(Beach<?> b, Head h1, Head h2)
	{
		super(b);
		
		p1 = h1.Source();
		p2 = h2.Source();
	}

	
	/**
	 * Changes the left event of the {@code Wedge}.
	 * 
	 * @param e  a beach event
	 * 
	 * 
	 * @see Event
	 */
	public void setLEvent(Event e)
	{
		e1 = e;
	}
	
	/**
	 * Changes the right event of the {@code Wedge}.
	 * 
	 * @param e  a beach event
	 * 
	 * 
	 * @see Event
	 */
	public void setREvent(Event e)
	{
		e2 = e;
	}
	
	
	/**
	 * Returns the left event of the {@code Wedge}.
	 * 
	 * @return  a beach event
	 * 
	 * 
	 * @see Event
	 */
	public Event LEvent()
	{
		return e1;
	}
	
	/**
	 * Returns the right event of the {@code Wedge}.
	 * 
	 * @return  a beach event
	 * 
	 * 
	 * @see Event
	 */
	public Event REvent()
	{
		return e2;
	}

	
	@Override
	public Head LHead()
	{
		Extreme e = Extreme.MIN;
		Head prev = new Head(Set(), p1, e);
		prev.setParent(this);
		return prev;
	}
	
	@Override
	public Head RHead()
	{
		Extreme e = Extreme.MAX;
		Head next = new Head(Set(), p2, e);
		next.setParent(this);
		return next;
	}
	

	@Override
	public float X()
	{
		float x1 = p1.X();
		float y1 = p1.Y();
		float x2 = p2.X();
		float y2 = p2.Y();
				
		if(y1 == y2)
			return (x1 + x2) / 2;
		else
		{
			float ys = Set().YSweep();
			
			float a = y2 - y1;
			float b = 2 * x1 * (ys - y2) + 2 * x2 * (ys - y1);
			float d = Floats.sqrt(4 * (ys - y1) * (ys - y2) * ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));


			float xl = -(b + d) / (2 * a);
			float xr = -(b - d) / (2 * a);

			if(y1 < y2)
			{
				if(x1 < x2)
					return Floats.min(+xl, +xr);
				else
					return Floats.max(-xl, -xr);
			}

			if(x1 < x2)
				return Floats.max(-xl, -xr);
			else
				return Floats.min(+xl, +xr);
		}
	}
	
	@Override
	public float Y()
	{
		float x1 = p1.X();
		float y1 = p1.Y();
		float x2 = p2.X();
		float y2 = p2.Y();
		
		float a = -(x2 - x1) / (y2 - y1);
		float b = (x2 * x2 - x1 * x1 + y2 * y2 - y1 * y1) / (2 * (y2 - y1));

		return a * X() + b;
	}
}