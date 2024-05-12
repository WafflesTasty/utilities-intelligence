package waffles.utils.intel.utilities;

/**
 * The {@code Vacancy} enum defines states of vacancy.
 *
 * @author Waffles
 * @since 12 May 2024
 * @version 1.1
 */
public enum Vacancy
{
	/**
	 * A vast emptiness.
	 */
	EMPTY,
	/**
	 * A thick fullness.
	 */
	FULL;
	
	
	/**
	 * Inverts a {@code Vacancy} state.
	 * 
	 * @param mrt  a vacancy state
	 * @return  an inverse vacancy
	 */
	public static Vacancy invert(Vacancy mrt)
	{
		switch(mrt)
		{
		case EMPTY:
			return Vacancy.FULL;
		case FULL:
		default:
			return Vacancy.EMPTY;
		}
	}
}