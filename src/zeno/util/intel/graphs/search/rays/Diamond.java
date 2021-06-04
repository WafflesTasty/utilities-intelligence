package zeno.util.intel.graphs.search.rays;

import java.util.Iterator;

import zeno.util.algebra.linear.matrix.Matrices;
import zeno.util.algebra.linear.matrix.fixed.Matrix2x2;
import zeno.util.algebra.linear.vector.fixed.Vector3;
import zeno.util.coll.dictionary.maps.HashedMap;
import zeno.util.geom.utilities.cardinal.Cardinal2D;
import zeno.util.intel.graphs.search.Raycaster.Source;
import zeno.util.tools.Floats;
import zeno.util.tools.Integers;

/**
 * The {@code Diamond} class iterates over a single diamond segment.
 *
 * @author Waffles
 * @since 27 May 2021
 * @version 1.0
 * 
 * 
 * @see Iterator
 * @see Vector3
 */
public class Diamond implements Iterator<Vector3>
{
	static HashedMap<Integer, Matrix2x2> quad = Quadrants();
	
	static HashedMap<Integer, Matrix2x2> Quadrants()
	{
		HashedMap<Integer, Matrix2x2> quads = new HashedMap<>();
		
		Matrix2x2 m0 = Matrices.create(2, 2);
		m0.set(+1f, 0, 0); m0.set(+1f, 1, 1);
		Matrix2x2 m1 = Matrices.create(2, 2);
		m1.set(-1f, 0, 1); m1.set(+1f, 1, 0);
		Matrix2x2 m2 = Matrices.create(2, 2);
		m2.set(-1f, 0, 0); m2.set(-1f, 1, 1);
		Matrix2x2 m3 = Matrices.create(2, 2);
		m3.set(+1f, 0, 1); m3.set(-1f, 1, 0);
		
		quads.put(0, m0);
		quads.put(1, m1);
		quads.put(2, m2);
		quads.put(3, m3);
		
		return quads;
	}
	
	
	private int rad;
	private float tMin, tMax;
	private Vector3 pMin, pMax;
	private Source<?> source;
	private Cardinal2D dir;
	private Vector3 next;
	
	/**
	 * Creates a new {@code Diamond}.
	 * 
	 * @param src  a caster source
	 * @param min  a minimum angle
	 * @param max  a maximum angle
	 * @param r    a diamond radius
	 * 
	 * 
	 * @see Source
	 */
	public Diamond(Source<?> src, float min, float max, int r)
	{
		initialize(src, min, max, r);
		
		next = pMin;
		dir = Direction(next.Z());
	}

	/**
	 * Creates a new {@code Diamond}.
	 * 
	 * @param src  a caster source
	 * @param min  a minimum angle
	 * @param max  a maximum angle
	 * @param r  a diamond radius
	 * @param p  an initial tile
	 * 
	 * 
	 * @see Vector3
	 * @see Source
	 */
	public Diamond(Source<?> src, float min, float max, int r, Vector3 p)
	{
		initialize(src, min, max, r);
		
		next = pMin;
		if(hasNext())
		{
			if(next.equals(p, 3))
			{
				next = findNext();
			}
		}
		dir = Direction(next.Z());
	}
	
	
	private void initialize(Source<?> src, float min, float max, int r)
	{
		source = src; rad  = r;
		tMin = min;	tMax = max;
		
		pMin = PMin(tMin);
		pMax = PMax(tMax);
		
		// Edge case where tMin close to tMax.
		if(pMax.Z() <= tMin && tMax <= pMin.Z())
		{
			dir = Direction(pMax.Z());
			Vector3 v = pMin;
			pMin = pMax;
			pMax = v;
		}
	}

	private Cardinal2D Direction(float tVal)
	{
		if(tVal < -1 * Floats.PI / 2)
			return Cardinal2D.SOUTHEAST;
		if(tVal < +0 * Floats.PI / 2)
			return Cardinal2D.NORTHEAST;
		if(tVal < +1 * Floats.PI / 2)
			return Cardinal2D.NORTHWEST;
		
		return Cardinal2D.SOUTHWEST;
	}
	
	
	private Vector3 PMin(float tVal)
	{
		float abs = Floats.abs(tVal);
		float cos = Floats.cos(tVal);
		float sin = Floats.sin(tVal);
		
		float mod = Floats.abs(cos) + Floats.abs(sin);
		
		int x, y;
		if(2 * abs < Floats.PI)
		{
			y = (int) Floats.ceil(rad * sin / mod);
			x = rad - Integers.abs(y);
		}
		else
		{
			y = (int) Floats.floor(rad * sin / mod);
			x = Integers.abs(y) - rad;
		}
		
		
		int r = source.Tile().Row();
		int c = source.Tile().Column();
		float tMod = Floats.atan2(x, y);
		if(tMin * tMod < 0)
		{
			tMod = -tMod;
		}
		
		return new Vector3(x + c, y + r, tMod);
	}
	
	private Vector3 PMax(float tVal)
	{
		float abs = Floats.abs(tVal);
		float cos = Floats.cos(tVal);
		float sin = Floats.sin(tVal);
		
		float mod = Floats.abs(cos) + Floats.abs(sin);
		
		int x, y;
		if(2 * abs < Floats.PI)
		{
			y = (int) Floats.floor(rad * sin / mod);
			x = rad - Integers.abs(y);
		}
		else
		{
			y = (int) Floats.ceil(rad * sin / mod);
			x = Integers.abs(y) - rad;
		}
		
		
		int r = source.Tile().Row();
		int c = source.Tile().Column();
		float tMod = Floats.atan2(x, y);
		if(tMax * tMod < 0)
		{
			tMod = -tMod;
		}
		
		return new Vector3(x + c, y + r, tMod);
	}


	/**
	 * Returns the min angle of the {@code Diamond}.
	 * 
	 * @return  a minimum angle
	 */
	public float MinAngle()
	{
		return tMin;
	}
	
	/**
	 * Returns the max angle of the {@code Diamond}.
	 * 
	 * @return  a maximum angle
	 */
	public float MaxAngle()
	{
		return tMax;
	}
	
	/**
	 * Returns the radius of the {@code Diamond}.
	 * 
	 * @return  a radius
	 */
	public int Radius()
	{
		return rad;
	}

		
	private Vector3 findNext()
	{
		if(next.equals(pMax, 3))
		{
			dir =  null;
			return next;
		}
		
		
		int r = source.Tile().Row();
		int c = source.Tile().Column();
				
		int x = (int) (next.X() + dir.X());
		int y = (int) (next.Y() + dir.Y());
		
		float tVal = Floats.atan2(x - c, y - r);
		if(tVal == -Floats.PI)
		{
			tVal = -tVal;
		}
		
		if(x - c + y - r > rad)
			throw new RuntimeException("poop");
		next = new Vector3(x, y, tVal);
		dir = Direction(tVal);
		return next;
	}

	@Override
	public boolean hasNext()
	{
		return dir != null;
	}

	@Override
	public Vector3 next()
	{
		Vector3 curr = next;
		next = findNext();
		return curr;
	}
}