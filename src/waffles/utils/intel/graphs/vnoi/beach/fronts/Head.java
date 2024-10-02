package waffles.utils.intel.graphs.vnoi.beach.fronts;

import waffles.utils.algebra.elements.linear.vector.fixed.Vector2;
import waffles.utils.geom.spatial.data.unary.Positioned2D;
import waffles.utils.intel.graphs.vnoi.Beach;
import waffles.utils.intel.graphs.vnoi.beach.Event;
import waffles.utils.intel.graphs.vnoi.beach.Front;
import waffles.utils.lang.enums.Extreme;

/**
 * A {@code Head} defines leaf {@code Front} of a {@code Beach}.
 * Each {@code Head} represents a section of the parabola, defined by
 * all points at equal distance from its source, to the sweep line.
 *
 * @author Waffles
 * @since 29 Sep 2024
 * @version 1.1
 * 
 * 
 * @see Front
 */
public class Head extends Front
{	
	private Extreme ext;
	private Positioned2D src;
	
	/**
	 * Creates a new {@code Head}.
	 * 
	 * @param b  a parent beach
	 * @param s  a positioned object
	 * 
	 * 
	 * @see Positioned2D
	 * @see Beach
	 */
	public Head(Beach<?> b, Positioned2D s)
	{
		super(b);
		src = s;
	}

	/**
	 * Creates a new {@code Head}.
	 * 
	 * @param b  a parent beach
	 * @param s  a positioned object
	 * @param e  a parent extremum
	 * 
	 * 
	 * @see Positioned2D
	 * @see Extreme
	 * @see Beach
	 */
	public Head(Beach<?> b, Positioned2D s, Extreme e)
	{
		super(b);
		ext = e;
		src = s;
	}

	
	/**
	 * Changes the {@code Event} of the {@code Head}.
	 * 
	 * @param evt  a beach event
	 * 
	 * 
	 * @see Event
	 */
	public void setEvent(Event evt)
	{
		if(ext == Extreme.MIN)
			Parent().setLEvent(evt);
		else
			Parent().setREvent(evt);
	}

	/**
	 * Replaces the {@code Head} with a {@code Wedge}.
	 * 
	 * @param wed  a beach wedge
	 * 
	 * 
	 * @see Wedge
	 */
	public void replace(Wedge wed)
	{
		if(ext == Extreme.MIN)
			Parent().setLChild(wed);
		else
			Parent().setRChild(wed);			
	}
	
	/**
	 * Returns the position source of the {@code Head}.
	 * 
	 * @return  a positioned object
	 * 
	 * 
	 * @see Positioned2D
	 */
	public Positioned2D Source()
	{
		return src;
	}
	
	/**
	 * Returns the {@code Event} of the {@code Head}.
	 * 
	 * @return  a beach event
	 * 
	 * 
	 * @see Event
	 */
	public Event Event()
	{
		if(ext == Extreme.MIN)
			return Parent().LEvent();
		else
			return Parent().REvent();
	}

			
	@Override
	public Wedge Parent()
	{
		return (Wedge) super.Parent();
	}
	
	@Override
	public Vector2 Origin()
	{
		return src.Origin();
	}
	
	@Override
	public Head LHead()
	{
		Wedge prev = prev();
		if(prev != null)
		{
			return prev.LHead();
		}
		
		return null;
	}
	
	@Override
	public Head RHead()
	{
		Wedge next = next();
		if(next != null)
		{
			return next.RHead();
		}
		
		return null;
	}
	
	@Override
	public Wedge prev()
	{
		if(ext == Extreme.MIN)
			return (Wedge) Parent().prev();
		else
			return Parent();
	}
	
	@Override
	public Wedge next()
	{
		if(ext == Extreme.MAX)
			return (Wedge) Parent().next();
		else
			return Parent();
	}
}