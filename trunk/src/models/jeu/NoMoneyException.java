package models.jeu;

/**
 * Exception levée si le joueur n'a pas assez d'argent poour effectuer cette
 * action
 * 
 * @author Pierre-Do
 * 
 */
public class NoMoneyException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7348304222504565854L;

	/**
	 * Appel du constructeur simple.
	 * @param cause Un bref message d'erreur sur la cause de l'exception
	 */
	public NoMoneyException(String cause)
	{
		super(cause);
	}
}
