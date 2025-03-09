package waffles.utils.intel.graphs;

import java.util.Iterator;

import waffles.utils.sets.queues.delegate.JDeque;

/**
 * A {@code Path} defines a chain of objects, internally stored as a {@link JDeque}.
 * {@code Path} objects are used in {@code Path.Finder} algorithms.
 *
 * @author Waffles
 * @since 29 Mar 2024
 * @version 1.0
 *
 *
 * @param <O>  an object type
 * @see Iterable
 */
public class Path<O> implements Iterable<O>
{
	/**
	 * A {@code Path.Finder} attempts to find a {@code Path} between objects.
	 *
	 * @author Waffles
	 * @since 29 Mar 2024
	 * @version 1.1
	 *
	 *
	 * @param <O>  an object type
	 */
	@FunctionalInterface
	public static interface Finder<O>
	{
		/**
		 * Connects a source to a target with the {@code Path.Finder}.
		 * 
		 * @param src  a source object
		 * @param tgt  a target object
		 * @return  a path between objects
		 * 
		 * 
		 * @see Path
		 */
		public abstract Path<O> connect(O src, O tgt);
	}

	
	private JDeque<O> nodes;
	
	/**
	 * Creates a new {@code Path}.
	 * 
	 * @param src  a source path
	 * @param tgt  a target object
	 */
	public Path(Path<O> src, O tgt)
	{
		nodes = new JDeque<>();
		for(O obj : src)
		{
			nodes.add(obj);
		}
		
		nodes.add(tgt);
	}
	
	/**
	 * Creates a new {@code Path}.
	 * 
	 * @param tgt  a target object
	 */
	public Path(O tgt)
	{
		nodes = new JDeque<>();
		nodes.add(tgt);
	}
	
	/**
	 * Creates a new {@code Path}.
	 */
	public Path()
	{
		nodes = new JDeque<>();
	}

	
	/**
	 * Checks an object ends at the {@code Path}.
	 * 
	 * @param obj  an object to check
	 * @return  {@code true} if it is the path end
	 */
	public boolean endsAt(O obj)
	{
		return obj.equals(Tail());
	}
	
	/**
	 * Checks an object along the {@code Path}.
	 * 
	 * @param obj  an object to check
	 * @return  {@code true} if along the path
	 */
	public boolean reaches(O obj)
	{
		for(O tgt : nodes)
		{
			if(obj.equals(tgt))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns the length of the {@code Path}.
	 * 
	 * @return  a path length
	 */
	public int Length()
	{
		return nodes.Count();
	}

	/**
	 * Returns the head of the {@code Path}.
	 * 
	 * @return  a head object
	 */
	public O Head()
	{
		return nodes.peekFirst();
	}
	
	/**
	 * Returns the tail of the {@code Path}.
	 * 
	 * @return  a tail object
	 */
	public O Tail()
	{
		return nodes.peekLast();
	}
	
	
	@Override
	public Iterator<O> iterator()
	{
		return nodes.iterator();
	}
}