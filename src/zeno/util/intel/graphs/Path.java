package zeno.util.intel.graphs;

import java.util.Iterator;

/**
 * The {@code Path} class defines a path through a set of objects.
 * </br> It is defined recursively, taking another path as a source and appending one object.
 *
 * @author Zeno
 * @since 01 Mar 2020
 * @version 1.0
 * 
 * 
 * @param <O>  an object type
 * @see Iterable
 */
public class Path<O> implements Iterable<O>
{
	/**
	 * The {@code Traversal} class iterates objects in a {@code Path}.
	 *
	 * @author Zeno
	 * @since 01 Mar 2020
	 * @version 1.0
	 * 
	 * 
	 * @param <O>  an object type
	 * @see Iterator
	 */
	public static class Traversal<O> implements Iterator<O>
	{
		private Path<O> path;
		
		/**
		 * Creates a new {@code Traversal}.
		 * 
		 * @param p  a target path
		 * 
		 * 
		 * @see Path
		 */
		public Traversal(Path<O> p)
		{
			path = p;
		}
		
		
		@Override
		public boolean hasNext()
		{
			return path != null;
		}
		
		@Override
		public O next()
		{
			O next = path.Target();
			path = path.Source();
			return next;
		}
	}
	
	
	private O tgt;
	private Path<O> src;
	
	/**
	 * Creates a new {@code Path}.
	 * 
	 * @param src  a source path
	 * @param tgt  a target object
	 */
	public Path(Path<O> src, O tgt)
	{
		this.src = src;
		this.tgt = tgt;
	}
	
	/**
	 * Creates a new {@code Path}.
	 * 
	 * @param tgt  a target object
	 */
	public Path(O tgt)
	{
		this(null, tgt);
	}

	
	/**
	 * Checks an object at the end of the {@code Path}.
	 * 
	 * @param obj  an object to check
	 * @return  {@code true} if it is the path end
	 */
	public boolean endsAt(O obj)
	{
		return tgt.equals(obj);
	}
	
	/**
	 * Checks an object traversed by the {@code Path}.
	 * 
	 * @param obj  an object to check
	 * @return  {@code true} if it is traversed
	 */
	public boolean reaches(O obj)
	{
		if(!tgt.equals(obj))
		{
			if(src != null)
			{
				return src.reaches(obj);
			}
			
			return false;
		}
		
		return true;
	}

	/**
	 * Returns the total cost of the {@code Path}.
	 * 
	 * @param h  a heuristic to use
	 * @return  a path cost
	 * 
	 * 
	 * @see Heuristic
	 */
	public float cost(Heuristic<O> h)
	{
		if(src != null)
		{
			return src.cost(h) + h.cost(src.Target(), tgt);
		}
		
		return 0;
	}
		
	/**
	 * Returns a truncated {@code Path}.
	 * 
	 * @param obj  a target object
	 * @return  a truncated path
	 */
	public Path<O> truncate(O obj)
	{
		if(tgt.equals(obj))
		{
			return this;
		}
		
		if(src != null)
		{
			return src.truncate(obj);
		}
		
		return null;
	}
	
	
	/**
	 * Returns the size of the {@code Path}.
	 * 
	 * @return  a path size
	 */
	public int Size()
	{
		return 1 + (src != null ? src.Size() : 0);
	}
	
	/**
	 * Returns the source of the {@code Path}.
	 * </br> A source is null iff the path has one object.
	 * 
	 * @return  a source path
	 * 
	 * 
	 * @see Path
	 */
	public Path<O> Source()
	{
		return src;
	}
	
	/**
	 * Returns the target of the {@code Path}.
	 * </br> The total path equals its source connecting its target.
	 * 
	 * @return  a target object
	 */
	public O Target()
	{
		return tgt;
	}

	
	@Override
	public Iterator<O> iterator()
	{
		return new Traversal<>(this);
	}

	@Override
	public String toString()
	{
		return "(" + (src != null ? src + " -> " + tgt : tgt) + ")";
	}
}