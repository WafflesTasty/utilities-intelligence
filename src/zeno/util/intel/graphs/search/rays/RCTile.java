package zeno.util.intel.graphs.search.rays;

import zeno.util.algebra.linear.vector.fixed.Vector2;
import zeno.util.geom.utilities.cardinal.Cardinal2D;
import zeno.util.tools.Floats;

/**
 * The {@code RCTile} class defines a tile in the {@code Raycaster}.
 *
 * @author Waffles
 * @since 17 Jun 2021
 * @version 1.0
 */
public class RCTile
{
	private Vector2 v;
	private Cardinal2D dir;
	
	/**
	 * Creates a new {@code RCTile}.
	 * 
	 * @param c  a tile column
	 * @param r  a tile row
	 * @param dir  a next direction
	 * 
	 * 
	 * @see Cardinal2D
	 */
	public RCTile(int c, int r, Cardinal2D dir)
	{
		v = new Vector2(c, r);
		this.dir = dir;
	}
	
	/**
	 * Returns the next {@code RCTile}.
	 * 
	 * @return  a next tile
	 */
	public RCTile next()
	{
		int c = (int) (v.X() + dir.X());
		int r = (int) (v.Y() + dir.Y());

		if(c == 0)
		{
			if(dir == Cardinal2D.SOUTHEAST)
			{
				return new RCTile(c, r, Cardinal2D.NORTHEAST);
			}
			
			return new RCTile(c, r, Cardinal2D.SOUTHWEST);
		}
		
		if(r == 0)
		{
			if(dir == Cardinal2D.NORTHEAST)
			{
				return new RCTile(c, r, Cardinal2D.NORTHWEST);
			}
			
			return null;
		}
		
		return new RCTile(c, r, dir);
	}

	/**
	 * Returns the angle of the {@code RCTile}.
	 * 
	 * @return  a tile angle
	 */
	public float Angle()
	{
		if(v.X() < 0 && v.Y() == 0)
		{
			return -Floats.PI;
		}
		
		return Floats.atan2(v.X(), v.Y());
	}
	
	/**
	 * Returns the x-coordinate of the {@code RCTile}.
	 * 
	 * @return  an x-coordinate
	 */
	public int X()
	{
		return (int) v.X();
	}

	/**
	 * Returns the y-coordinate of the {@code RCTile}.
	 * 
	 * @return  an y-coordinate
	 */
	public int Y()
	{
		return (int) v.Y();
	}
}