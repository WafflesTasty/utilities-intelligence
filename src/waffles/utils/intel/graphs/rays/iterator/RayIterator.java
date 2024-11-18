package waffles.utils.intel.graphs.rays.iterator;

import java.util.Iterator;

import waffles.utils.geom.spaces.index.tiles.Tiled2D;
import waffles.utils.intel.graphs.rays.RayCaster;
import waffles.utils.intel.graphs.rays.RayIndex;
import waffles.utils.intel.graphs.rays.index.RayCone;
import waffles.utils.sets.queues.Queue;
import waffles.utils.sets.queues.delegate.JFIFOQueue;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code RayIterator} provides the main iteration loop of a {@code RayCaster}.
 * It iterates the grid around its source tile in expanding diamond shapes. Initially,
 * the iterator loops around a full cone (from -Pi to Pi). Whenever a tile blocks the
 * raycaster, the cone is split into subcones, defining the remaining viewspace.
 *
 * @author Waffles
 * @since 03 Nov 2024
 * @version 1.1
 *
 *
 * @param <T>  a tile type
 * @see Iterator
 * @see Tiled2D
 */
public class RayIterator<T extends Tiled2D> implements Iterator<T>
{
	private T next;
	private RayCaster<T> src;
	private Queue<RayDiamond> cones;
	
	/**
	 * Creates a new {@code RayIterator}.
	 * 
	 * @param c  a ray caster
	 * 
	 * 
	 * @see RayCaster
	 */
	public RayIterator(RayCaster<T> c)
	{
		cones = new JFIFOQueue<>();
		cones.push(new RayDiamond(-Floats.PI, 0));
		cones.push(new RayDiamond(0, +Floats.PI));
		
		next = c.Tile();
		src = c;
	}

	
	private T findNext()
	{
		// If no cones are left...
		if(cones.isEmpty())
		{
			System.out.println("All cones processed.");
			System.out.println();
			// Finish iterating.
			return null;
		}
		
		// Find the next diamond.
		RayDiamond dmd = cones.peek();
		System.out.println("Cone [" + dmd.Minimum() + ", " + dmd.Maximum() + ", " + dmd.Diagonal() + "] :");
		// If it exceeds the max diagonal...
		if(src.Diagonal() < dmd.Diagonal())
		{
			System.out.println("The cone exceeds maximum diagonal.");
			// ...remove it, and
			cones.pop();
			// find the next tile.
			return findNext();
		}
		

		T cen = src.Tile();
		
		int[] crds = dmd.next();
		int r = crds[0] + cen.Row();
		int c = crds[1] + cen.Column();
		
		// Compute the next tile.
		next = (T) cen.Parent().get(r, c);

		// If no tile was found...
		if(next == null)
		{
			System.out.println("The tile was not found.");
			// ...find the next tile.
			return findNext();
		}
		
		// If the tile blocks rays...
		if(src.blocksRay(next))
		{
			System.out.println("The tile blocks rays.");
			
			RayIndex index = src.RayIndex();
			RayCone cone = index.get(crds);
			
			// ...clip the diamond.			
			float aMin = cone.Minimum();
			float aMax = cone.Maximum();
						
			if(crds[0] == 0 && crds[1] < 0)
			{
				if(cone.contains(aMin))
					dmd.setMinimum(aMin);
				if(cone.contains(aMax))
					dmd.setMaximum(aMax);
				
				return next;
			}
			
			if(cone.isAbove(aMin))
			{
				if(cone.isBelow(aMax))
				{
					cones.pop();
					return next;
				}
				
				dmd.setMinimum(aMax);
				return next;
			}
			
			if(cone.isBelow(aMax))
			{
				dmd.setMaximum(aMin);
				return next;
			}

			float cMin = dmd.Minimum();
			dmd.setMinimum(aMax);
			dmd = new RayDiamond(cMin, aMin, r+1);
			cones.push(dmd);
		}
		
		// Return the tile.
		return next;
	}

	@Override
	public boolean hasNext()
	{
		return next != null;
	}

	@Override
	public T next()
	{
		T curr = next;
		next = findNext();
		return curr;
	}
}