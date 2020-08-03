package zeno.util.intel.random;

import zeno.util.algebra.linear.vector.Vector;
import zeno.util.algebra.linear.vector.Vectors;
import zeno.util.coll.trees.orthtree.OrtTree;
import zeno.util.geom.collidables.geometry.generic.ICuboid;
import zeno.util.geom.utilities.Geometries;
import zeno.util.tools.helper.Randomizer;

public class BoxFactory
{
	private static float random(float mean, float max)
	{
		return (float) Randomizer.randomGaussian(mean, max);
	}
	
	public static interface Rules
	{		
		public abstract float MaxSize();

		public abstract float MeanSize();
		
		public abstract float Velocity();
		
		public abstract int Iterations();
		
		public abstract int CellCount();
	}
	

	private Rules rules;
	private ICuboid bounds;
	
	public BoxFactory(ICuboid bounds)
	{
		this.bounds = bounds;
	}
	
	public void setRules(Rules rules)
	{
		this.rules = rules;
	}
	
	public OrtTree<ICuboid> generate()
	{
		OrtTree<ICuboid> tree1 = new OrtTree<>(bounds);
		for(int k = 0; k < rules.CellCount(); k++)
		{
			Vector s = Vectors.create(bounds.Dimension());
			for(int i = 0; i < bounds.Dimension(); i++)
			{
				s.set(random(rules.MeanSize(), rules.MaxSize()), i);
			}
			
			Vector m = bounds.Center();
			m = m.plus(bounds.Size().times(1 / 3f));
			m = m.minus(s.times(1 / 2f));
			
			Vector c = Vectors.create(bounds.Dimension());
			for(int i = 0; i < bounds.Dimension(); i++)
			{
				c.set(random(bounds.Center().get(i), m.get(i)), i);
			}
			
			tree1.add(Geometries.cuboid(c, s));
		}
		
		for(int k = 0; k < rules.Iterations(); k++)
		{
			OrtTree<ICuboid> tree2 = new OrtTree<>(bounds);
			
			for(ICuboid c1 : tree1)
			{
				Vector v = Vectors.create(bounds.Dimension());
				for(ICuboid c2 : tree1.query(c1))
				{
					if(c1.equals(c2)) continue;
					if(c2.intersects(c1))
					{
						v = v.plus(c1.Center().minus(c2.Center()));
					}
				}
				
				if(v.normSqr() > 0f)
				{
					Vector c = c1.Center(); Vector s = c1.Size();
					v = v.normalize().times(rules.Velocity() / 2);
					
					ICuboid c2 = Geometries.cuboid(c.plus(v), s);
					if(bounds.contains(c2))
						tree2.add(c2);
					else
						tree2.add(c1);
				}
			}
			
			tree1 = tree2;
		}
		
		return tree1;
	}
}