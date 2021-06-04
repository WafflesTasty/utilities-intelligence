package zeno.util.intel.graphs.search;

import java.util.Iterator;

import zeno.util.coll.space.planar.TiledSpace2D;
import zeno.util.coll.space.planar.TiledSpace2D.Tile2D;
import zeno.util.intel.graphs.search.rays.RCIterator;

/**
 * The {@code Raycaster} class generates an emission circle in a {@link TiledSpace2D}.
 * </br> The algorithm used is a variant of the {@code precise permissive field of view}.
 * 
 * @author Zeno
 * @since Nov 22, 2014
 * @version 1.0
 * 
 * 
 * @param <T> a tile type
 * @see <a href="http://www.roguebasin.com/index.php?title=Precise_Permissive_Field_of_View">Precise Permissive Field of View</a>
 * @see Iterable
 * @see Tile2D
 */
public class Raycaster<T extends Tile2D> implements Iterable<T>
{		
	/**
	 * The {@code Source} interface defines the source of a {@link Raycaster}.
	 * 
	 * @author Waffles
	 * @since Nov 22, 2014
	 * @version 1.0
	 * 
	 * 
	 * @param <T> a tile type
	 * @see Raycaster
	 * @see Tile2D
	 */
	public static interface Source<T extends Tile2D>
	{		
		/**
		 * Checks if a tile blocks rays from the {@code Source}.
		 * </br> If the tile is null, always return true.
		 * 
		 * @param tile  a target tile
		 * @return {@code true} if a ray is blocked
		 */
		public abstract boolean blocksRay(T tile);
		
		/**
		 * Returns the caster radius of the {@code Source}.
		 * 
		 * @return  a caster radius
		 */
		public abstract int Radius();
		
		/**
		 * Returns the tile of the {@code Source}.
		 * 
		 * @return  a source tile
		 */
		public abstract T Tile();
	}
	
	
	private Source<T> source;
	private TiledSpace2D<T> target;

	/**
	 * Creates a new {@code Raycaster}.
	 * 
	 * @param src  a raycaster source
	 * @param tgt  a raycaster target
	 * 
	 * 
	 * @see TiledSpace2D
	 * @see Source
	 */
	public Raycaster(Source<T> src, TiledSpace2D<T> tgt)
	{	
		source = src;
		target = tgt;
	}

		
	/**
	 * Returns the target of the {@code Raycaster}.
	 * 
	 * @return  a raycaster target
	 * 
	 * 
	 * @see TiledSpace2D
	 */
	public TiledSpace2D<T> Target()
	{
		return target;
	}
	
	/**
	 * Returns the source of the {@code Raycaster}.
	 * 
	 * @return  a raycaster source
	 * 
	 * 
	 * @see Source
	 */
	public Source<T> Source()
	{
		return source;
	}
	
	
	@Override
	public Iterator<T> iterator()
	{
		return new RCIterator<>(this);
	}
}