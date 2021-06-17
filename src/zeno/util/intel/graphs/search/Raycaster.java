package zeno.util.intel.graphs.search;

import java.util.Iterator;

import zeno.util.coll.space.planar.TiledSpace2D;
import zeno.util.coll.space.planar.TiledSpace2D.Tile2D;
import zeno.util.intel.graphs.search.rays.RCCIterator;
import zeno.util.intel.graphs.search.rays.RCIterator;
import zeno.util.tools.Floats;
import zeno.util.tools.Integers;

/**
 * The {@code Raycaster} class generates an emission diamond in a {@link TiledSpace2D}.
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
public interface Raycaster<T extends Tile2D> extends Iterable<T>
{	
	/**
	 * The {@code Circular} interface defines a {@code Raycaster} with a circular emission.
	 *
	 * @author Waffles
	 * @since 16 Jun 2021
	 * @version 1.0
	 * 
	 * 
	 * @param <T>  a tile type
	 * @see Raycaster
	 * @see Tile2D
	 */
	public static interface Circular<T extends Tile2D> extends Raycaster<T>
	{
		/**
		 * Returns the radius of the {@code Raycaster}.
		 * 
		 * @return  a raycasting radius
		 */
		public abstract float Radius();
		
		
		@Override
		public default Iterator<T> iterator()
		{
			return new RCCIterator<>(this);
		}
		
		@Override
		public default int Diagonal()
		{
			return Integers.ceil(Floats.SQRT2 * Radius());
		}
	}
	
	
	@Override
	public default Iterator<T> iterator()
	{
		return new RCIterator<>(this);
	}
	
	/**
	 * Returns the column of the {@code Raycaster}.
	 * 
	 * @return  a grid column
	 */
	public default int Column()
	{
		return Tile().Column();
	}
	
	/**
	 * Returns the row of the {@code Raycaster}.
	 * 
	 * @return  a grid row
	 */
	public default int Row()
	{
		return Tile().Row();
	}
	
	
	/**
	 * Returns the target of the {@code Raycaster}.
	 * 
	 * @return  a raycaster target
	 * 
	 * 
	 * @see TiledSpace2D
	 */
	public abstract TiledSpace2D<T> Target();
	
	/**
	 * Checks if a tile blocks the {@code Raycaster}.
	 * </br> If the tile is null, always return true.
	 * 
	 * @param t  a target tile
	 * @return {@code true} if a ray is blocked
	 */
	public abstract boolean blocksRay(T t);
		
	/**
	 * Returns the diagonal of the {@code Raycaster}.
	 * </br> This determines the maximum size of the generated diamonds.
	 * 
	 * @return  a caster radius
	 */
	public abstract int Diagonal();
	
	/**
	 * Returns the tile of the {@code Raycaster}.
	 * 
	 * @return  a source tile
	 */
	public abstract T Tile();
}