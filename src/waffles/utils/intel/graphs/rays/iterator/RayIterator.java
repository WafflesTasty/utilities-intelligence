package waffles.utils.intel.graphs.rays.iterator;

import java.util.Iterator;

import waffles.utils.geom.spaces.index.TiledSpace2D;
import waffles.utils.geom.spaces.index.tiles.Tiled2D;
import waffles.utils.intel.graphs.rays.RayCaster;
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
	private RayDiamond dmd;	
	private Queue<RayDiamond> cones;
	private RayCaster<T> src;
	
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
		next = c.Tile();
		src = c;
		
		dmd = new RayDiamond(c);
		dmd.setMaximum(0);
		cones.push(dmd);

		dmd = new RayDiamond(c);
		dmd.setMinimum(0);
		cones.push(dmd);
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
		dmd = cones.peek();
		System.out.println(dmd + ":");
		
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
		TiledSpace2D<?> space = cen.Parent();
		next = (T) space.get(r, c);

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

			float rSrc = src.SourceRadius();
			float rTgt = src.TargetRadius();
			
			System.out.println("Source Radius: " + rSrc);
			System.out.println("Target Radius: " + rTgt);
			
			float norm = Floats.sqrt(crds[0] * crds[0] + crds[1] * crds[1]);
			float asin = Floats.asin((rSrc + rTgt) / norm);
			float atan = Floats.atan2(crds[0], crds[1]);

			System.out.println("Tile Norm: " + norm);
			
			float dMin = dmd.Minimum();
			float dMax = dmd.Maximum();
			int dRad = dmd.Diagonal();
			
			float aMin = atan - asin;
			float aMax = atan + asin;
			
			System.out.println("Minimum Angle: " + (aMin / Floats.PI) + " Pi");
			System.out.println("Maximum Angle: " + (aMax / Floats.PI) + " Pi");
			
			// If the diamond is above the minimum...
			if(dmd.isAbove(aMin))
			{
				// ...and below the maximum...
				if(dmd.isBelow(aMax))
				{
					// ...drop the diamond.
					cones.pop();
					// Return the tile.
					return next;
				}
				
				// ...update the diamond minimum.
				dmd.setMinimum(aMax);
				// Return the tile.
				return next;
			}

			// If the diamond is below the maximum...
			if(dmd.isBelow(aMax))
			{
				// ...update the diamond maximum.
				dmd.setMaximum(aMin);
				// Return the tile.
				return next;
			}

			// Update the diamond minimum.
			dmd.setMinimum(aMax);
			// Create and add a second diamond.
			dmd = new RayDiamond(src, dRad+1);
			dmd.setMinimum(dMin);
			dmd.setMaximum(aMin);
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