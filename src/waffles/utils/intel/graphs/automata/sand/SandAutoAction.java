package waffles.utils.intel.graphs.automata.sand;

import waffles.utils.geom.utilities.constants.Cardinal2D;
import waffles.utils.intel.graphs.automata.AutoAction;
import waffles.utils.intel.utilities.Mortality;
import waffles.utils.intel.utilities.Vacancy;

/**
 * A {@code SandAutoAction} defines the local behavior of a {@code SandAutomaton}.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 * 
 * 
 * @see AutoAction
 */
public class SandAutoAction implements AutoAction
{
	private Cardinal2D[] dir;
	private SandAutoTile src, tgt;

	/**
	 * Creates a new {@code SandAutoAction}.
	 * 
	 * @param lyt  an automaton layout
	 * @param sat  an automaton tile
	 * 
	 * 
	 * @see SandAutoLayout
	 * @see SandAutoTile
	 */
	public SandAutoAction(SandAutoLayout<?> lyt, SandAutoTile sat)
	{
		dir = lyt.Target();
		src = sat;
	}

	
	@Override
	public int[] Coordinates()
	{
		return src.Coordinates();
	}

	@Override
	public boolean resolve()
	{
		if(tgt != null)
		{
			tgt.setVacancy(Vacancy.EMPTY);
			if(src.Mortality() == Mortality.ALIVE
			&& tgt.Mortality() != Mortality.ALIVE)
			{
				src.setMortality(Mortality.DEAD);
				tgt.setMortality(Mortality.ALIVE);
				return true;
			}
		}

		return false;
	}

	@Override
	public void compute()
	{
		if(src.Mortality() != Mortality.DEAD)
		{
			for(Cardinal2D c : dir)
			{
				SandAutoTile n = src.Neighbor(c);
				if(n != null)
				{
					if(n.Mortality() != Mortality.ALIVE
					&& n.Vacancy() == Vacancy.EMPTY)
					{
						n.setVacancy(Vacancy.FULL);
						tgt = n; return;
					}
				}
			}
		}
	}
}