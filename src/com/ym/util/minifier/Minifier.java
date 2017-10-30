package com.ym.util.minifier;

import java.util.HashMap;
import java.util.Map;

public class Minifier {
	
	/**
	 * This method takes a String as input, replaces second and subsequent occurrences of the 
	 * identifiers in that String with a dollar sign followed by the index of the first appearance 
	 * of that identifier, and returns the converted String.
	 * <p>
	 * Time complexity: O(n), where n is the length of the input String.
	 * Space complexity: It is linear with respect to input string. But due to the way the logic 
	 * is defined, the length of converted output string might be larger than the input string. But 
	 * it would still be linear. There is also extra space involved due to the Map.
	 * 
	 * <p>
	 * Assumptions/Limitations: 
	 * <ul>
	 * <li>The length of final String can be greater than the length of input String. This code only
	 * handles max chars that can be stored in a String for both - input and output. 

	 * <li>This code will only be able to handle max chars that can be stored in a 
	 * String which is backed by a char array and arrays have int index so, 
	 * max of Integer.MAX_VALUE chars. The case when final string length becomes > Integer.MAX_VALUE,
	 * is not handled. </li>

	 * <li>"You" and "you" are different.</li>
	 * <li>All whitespaces are preserved.</li> 
	 * </ul>
	 * 
	 * <p>
	 * Alternate Approach:
	 * <ul>
	 * <li>Use regular expression to find the identifiers.</li>
	 * <li>Replace all occurrences of the identifier with a '$' + identifier index.</li>
	 * <li>Replace the first occurrence back to the identifier.</li>
	 * </ul>
	 * Pros: The code is elegant and simple. 
	 * Cons: There are multiple scans on the input that would need to be performed. Also, replacing 
	 * would create new String objects as Strings are unmutable. 
	 * 
	 * @param str - String containing source code 
	 * @return - Converted string where the subsequent duplicate identifiers are replaced by logic defined.
	 * 
	 */
	public String replaceIdentifiers(String str) {
		
		//If input is null, return null
		if(str == null) {
			return null;
		}
		
		//Map to store the identifiers and their first occurrence index
		Map<String, Integer> identifierMap = new HashMap<String, Integer>();
		
		//Other temporary variables
		StringBuilder finalOutput = new StringBuilder();
		StringBuilder currIdentifier = new StringBuilder();	 
		int identifierIndex = 0;  			//keeps a count of identifier index
		boolean isIdentifier = false;		//a flag to track if current char is part of an identifier
		
		//Loop on input string
		for(Character ch : str.toCharArray()) {
			
			//If current char is a letter, append char to currIdentifier, else process currIdentifier 
			if(Character.isUpperCase(ch) || Character.isLowerCase(ch)) {
				currIdentifier.append(ch);
				isIdentifier = true;
			} else {				
				if(isIdentifier) {
					//If new identifier, add to map; else get index
					if(!identifierMap.containsKey(currIdentifier.toString())) {						
						identifierMap.put(currIdentifier.toString(), identifierIndex);
						finalOutput.append(currIdentifier);	//first occurrence, so no $ sign					
					} else {
						Integer existingIdentifierId = identifierMap.get(currIdentifier.toString());
						finalOutput.append("$" + existingIdentifierId); //$ sign for subsequent occurrences
					}
					identifierIndex++;
					currIdentifier = new StringBuilder(); //start fresh for next identifier
				} 
				//Append the current char, and set isIdentifier to false
				finalOutput.append(ch);
				isIdentifier = false;
			}			
		}
		
		/* This block of code handles the case, when the input string ends in a character. Alternatively, 
		 * we can add a unique non-alphabetic character at the end of the input string (just before 
		 * the for loop), and remove that char from the finalOutput, just before returning it to 
		 * the calling method. 
		 */
		if(isIdentifier) {
			if(!identifierMap.containsKey(currIdentifier.toString())) {						
				//Adding this last identifier to the map, is not needed as we have already reached the end. 
				//But in case, we want to use this map for future processing, it is needed.
				identifierMap.put(currIdentifier.toString(), identifierIndex);
				finalOutput.append(currIdentifier);						
			} else {
				Integer existingWordId = identifierMap.get(currIdentifier.toString());
				finalOutput.append("$" + existingWordId);
			}			
		}
		return finalOutput.toString();
	}
}
