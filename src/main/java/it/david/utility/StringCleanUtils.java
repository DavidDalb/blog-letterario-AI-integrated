package it.david.utility;

public class StringCleanUtils {
	
	private StringCleanUtils () {}
	
	public static String cleanEmail(String email) {
		if(email == null) return null;
		return email.toLowerCase().trim();
	}

}
