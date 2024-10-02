package waffles.utils.intel.graphs.vnoi.beach;

import waffles.utils.algebra.elements.linear.vector.fixed.Vector2;
import waffles.utils.geom.spatial.data.unary.Positioned2D;
import waffles.utils.intel.graphs.vnoi.Beach;
import waffles.utils.intel.graphs.vnoi.beach.Front;
import waffles.utils.intel.graphs.vnoi.beach.fronts.Head;
import waffles.utils.sets.trees.binary.search.IONode;

/**
 * A {@code Front} defines a single node in a {@code Beach}. Each {@code Front}
 * can either be a {@code Head}, representing a segment of a parabola, or a
 * {@code Wedge}, representing an intersection between two parabolas.
 * 
 * @author Waffles
 * @since 29 Sep 2024
 * @version 1.1
 * 
 * 
 * @see Positioned2D
 * @see IONode
 * @see Front
 */
public abstract class Front extends IONode<Front> implements Positioned2D
{	
	/**
	 * Creates a new {@code Front}.
	 * 
	 * @param b  a parent beach
	 * 
	 * 
	 * @see Beach
	 */
	public Front(Beach<?> b)
	{
		super(b, null);
	}
	
	/**
	 * Returns the right head of the {@code Front}.
	 * 
	 * @return  a beach head
	 * 
	 * 
	 * @see Head
	 */
	public abstract Head LHead();
	
	/**
	 * Returns the left head of the {@code Front}.
	 * 
	 * @return  a beach head
	 * 
	 * 
	 * @see Head
	 */
	public abstract Head RHead();

	
	@Override
	public Beach<?> Set()
	{
		return (Beach<?>) super.Set();
	}
	
	@Override
	public String toString()
	{
		return "" + hashCode();
	}

	@Override
	public Vector2 Origin()
	{
		return new Vector2(X(), Y());
	}
	
	@Override
	public Front Delegate()
	{
		return this;
	}

	@Override
	public Front Sibling()
	{
		return (Front) super.Sibling();
	}
	
	@Override
	public Front LChild()
	{
		return (Front) super.LChild();
	}
	
	@Override
	public Front RChild()
	{
		return (Front) super.RChild();
	}

	@Override
	public Front Parent()
	{
		return (Front) super.Parent();
	}
	
	@Override
	public Front Value()
	{
		return this;
	}

	@Override
	public Front LLeaf()
	{
		return (Front) super.LLeaf();
	}
	
	@Override
	public Front RLeaf()
	{
		return (Front) super.RLeaf();
	}
	
	@Override
	public Front Root()
	{
		return (Front) super.Root();
	}
	
	@Override
	public Front Arch()
	{
		return this;
	}
	
	@Override
	public Front prev()
	{
		return (Front) super.prev();
	}
	
	@Override
	public Front next()
	{
		return (Front) super.next();
	}
}