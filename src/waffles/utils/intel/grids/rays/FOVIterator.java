package waffles.utils.intel.grids.rays;

import java.util.Iterator;

import waffles.utils.geom.spaces.index.TiledSpace2D;
import waffles.utils.geom.spaces.index.tiles.Tiled2D;
import waffles.utils.intel.grids.FOVCaster;
import waffles.utils.sets.queues.Queue;
import waffles.utils.sets.queues.delegate.JFIFOQueue;
import waffles.utils.tools.primitives.Floats;

/**
 * The {@code FOVIterator} provides the main iteration loop of a {@code FOVCaster}.
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
public class FOVIterator<T extends Tiled2D> implements Iterator<T>
{
	private T next;
	private FOVDiamond dmd;	
	private Queue<FOVDiamond> cones;
	private FOVCaster<T> src;
	
	/**
	 * Creates a new {@code FOVIterator}.
	 * 
	 * @param c  a ray caster
	 * 
	 * 
	 * @see FOVCaster
	 */
	public FOVIterator(FOVCaster<T> c)
	{
		cones = new JFIFOQueue<>();
		next = c.Tile();
		src = c;
		
		dmd = new FOVDiamond(c);
		dmd.setMaximum(.0f);
		cones.push(dmd);

		dmd = new FOVDiamond(c);
		dmd.setMinimum(.0f);
		cones.push(dmd);
	}

	
	private T findNext()
	{
		// If no cones are left...
		if(cones.isEmpty())
		{
			// Finish iterating.
			return null;
		}
		
		// Find the next diamond.
		dmd = cones.peek();
		
		// If it exceeds the max diagonal...
		if(src.Diagonal() < dmd.Diagonal())
		{
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
			// ...find the next tile.
			return findNext();
		}
		
		// If the tile blocks rays...
		if(src.blocksRay(next))
		{
			float rSrc = src.SourceRadius();
			float rTgt = src.TargetRadius();

			float dMin = dmd.Minimum();
			float dMax = dmd.Maximum();
			int dRad = dmd.Diagonal();
			
			
			float atan = Floats.atan2(crds[0], crds[1]);
			float norm = Floats.sqrt(crds[0] * crds[0] + crds[1] * crds[1]);
			float asin = Floats.asin((rSrc + rTgt) / norm);
			
			// THERE IS AN BUG HERE WHERE ANGLES OVERSHOOT.
			
			float aMin = Floats.normrad(atan - asin);
			float aMax = Floats.normrad(atan + asin);
			if(crds[0] < 0 && crds[1] == 0)
			{
				dmd.setMinimum(aMax);
				dmd.setMaximum(aMin);
				return next;
			}


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
			dmd = new FOVDiamond(src, dRad+1);
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