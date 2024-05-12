package waffles.utils.intel.utilities;

/**
 * The {@code Mortality} enum defines states of mortality.
 *
 * @author Waffles
 * @since 10 May 2024
 * @version 1.1
 */
public enum Mortality
{
	/**
	 * Life.
	 */
	ALIVE,
	/**
	 * Undeath.
	 */
	UNDEAD,
	/**
	 * Death.
	 */
	DEAD;
	
	
	/**
	 * Inverts a {@code Mortality} state.
	 * 
	 * @param mrt  a mortality state
	 * @return  an inverse mortality
	 */
	public static Mortality invert(Mortality mrt)
	{
		switch(mrt)
		{
		case ALIVE:
			return Mortality.DEAD;
		case DEAD:
			return Mortality.ALIVE;
		case UNDEAD:
			return Mortality.ALIVE;
		default:
			return Mortality.UNDEAD;
		}
	}
}