package zeno.util.intel.graphs.search.rays;

import java.util.Iterator;

import zeno.util.geom.utilities.cardinal.Cardinal2D;
import zeno.util.tools.Floats;
import zeno.util.tools.helper.Array;

/**
 * The {@code RCDiamond} class iterates over a single diamond segment.
 *
 * @author Waffles
 * @since 27 May 2021
 * @version 1.0
 * 
 * 
 * @see Iterator
 * @see RCTile
 */
public class RCDiamond implements Iterator<RCTile>
{
	private static final float MARGIN = 0.5f;
	private static final RCTile T_INITIAL = new RCTile(-1, 0, Cardinal2D.SOUTHEAST);
	
	private RCDiamond(RCTile first, float min, float max)
	{
		tFirst = first;
		tMin = min; tMax = max;
		initialize();
	}
	
	
	private float tMin, tMax;
	private RCTile tFirst, tNext;
		
	/**
	 * Creates a new {@code Diamond}.
	 */
	public RCDiamond()
	{
		this(T_INITIAL, -Floats.PI, +Floats.PI);
	}
	
	/**
	 * Grows the {@code Diamond} to the next diagonal.
	 * 
	 * @param dMax  a diagonal maximum
	 * @return  {@code true} if the diamond grew
	 */
	public boolean grow(int dMax)
	{
		if(Diagonal() == dMax)
		{
			return false;
		}
		
		
		int x = tFirst.X();
		int y = tFirst.Y();

		if(x <= 0 && y <= 0)
			tFirst = new RCTile(x-1, y, Cardinal2D.SOUTHEAST);
		else if(x > 0 && y <= 0)
			tFirst = new RCTile(x, y-1, Cardinal2D.NORTHEAST);
		else if(x >= 0 && y > 0)
			tFirst = new RCTile(x+1, y, Cardinal2D.NORTHWEST);
		else
			tFirst = new RCTile(x, y+1, Cardinal2D.SOUTHWEST);
		
		initialize();
		return true;
	}
	
	/**
	 * Splits the {@code Diamond} along a tile.
	 * 
	 * @param tile  a target tile
	 * @return  a list of valid diamonds
	 * 
	 * 
	 * @see RCDiamond
	 */
	public RCDiamond[] split(RCTile tile)
	{
		RCDiamond[] dmds = new RCDiamond[0];
		
		float[] thetas = new float[]
		{
			Floats.atan2(tile.X() + 0.5f, tile.Y() + 0.5f),
			Floats.atan2(tile.X() - 0.5f, tile.Y() + 0.5f),
			Floats.atan2(tile.X() + 0.5f, tile.Y() - 0.5f),
			Floats.atan2(tile.X() - 0.5f, tile.Y() - 0.5f)
		};
		
		float min = Floats.min(thetas);
		float max = Floats.max(thetas);
		
		if(max <= tMax)
		{
			dmds = Array.add.to(dmds, new RCDiamond(tile, max, tMax));
		}

		if(tMin <= min)
		{
			dmds = Array.add.to(dmds, this);
			tMax = min; tNext = null;
		}
		
		return dmds;
	}
	
	/**
	 * Clips the {@code Diamond} along a tile.
	 * 
	 * @param tile  a target tile
	 * 
	 * 
	 * @see RCTile
	 */
	public void clip(RCTile tile)
	{
		float min = Floats.atan2(tile.X() + 0.5f, tile.Y() - 0.5f);		
		if(tMin < min)
		{
			tMin = min;
		}
		
		float max = Floats.atan2(tile.X() + 0.5f, tile.Y() + 0.5f);
		if(tMax > max)
		{
			tMax = max;
			check();
		}
	}
		
		
	private boolean isAbove(RCTile tile)
	{
		if(tile.Angle() < tMin)
		{
			float cos = Floats.cos(tMin);
			float sin = Floats.sin(tMin);
			
			float x = tile.X();
			float y = tile.Y();

			return Floats.abs(y * cos - x * sin) > MARGIN;
		}
		
		return false;
	}

	private boolean isBelow(RCTile tile)
	{
		if(tMax < tile.Angle())
		{
			float cos = Floats.cos(tMax);
			float sin = Floats.sin(tMax);
			
			float x = tile.X();
			float y = tile.Y();

			return Floats.abs(y * cos - x * sin) > MARGIN;
		}
		
		return false;
	}
	
	private void initialize()
	{
		tNext = tFirst;
		while(isAbove(tNext))
		{
			tFirst = tNext;
			tNext = tNext.next();
			if(tNext == null)
			{
				break;
			}
		}
		
		// This check is necessary to avoid overshoots.
		// But using it leads to nullpointers.
		check();
	}
	
	private int Diagonal()
	{
		return (int) (Floats.abs(tFirst.X()) + Floats.abs(tFirst.Y()));
	}
	
	private void check()
	{
		if(tNext != null)
		{
			if(isBelow(tNext))
			{
				tNext = null;
			}
		}
	}


	@Override
	public String toString()
	{
		return "Diamond(" + tMin + ", " + tMax + ", " + Diagonal() + ").";
	}
	
	@Override
	public boolean hasNext()
	{
		return tNext != null;
	}

	@Override
	public RCTile next()
	{
		RCTile curr = tNext;
		tNext = tNext.next();
		check();
		
		return curr;
	}
}