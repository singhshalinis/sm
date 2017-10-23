package com.ym.util.minifier;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MinifierTest {

	private Minifier minifier;
	
	@Before
	public void setUp() {
		minifier = new Minifier();
	}
	
	@Test
	public final void testReplaceIdentifiers() {
		
		//Test Case 1
		String input1 = "you say yes, I say no you say stop and I say go go go";
		String output1 = "you say yes, I $1 no $0 $1 stop and $3 $1 go $12 $12";
		assertEquals(output1, minifier.replaceIdentifiers(input1));	
		
		//Test Case 2		
		String input2 ="/*														"
				+ "*Function to chop a string in half. 							" 
				+ "*/                                                           " 
				+ "public static string foo(string input) {                		" 
				+ "	if (input == null || input.isEmpty()) {                    	" 
				+ "        return input;                          				" 
				+ "	}                                                           " 
				+ "    if (input.length() % 2 == 1) {                    		" 
				+ "        return \"cannot chop an odd-length string in half\";	" 
				+ "    }                                                        " 
				+ "	return input.substring(input.length() / 2);                 " 
				+ "}                                                          	";
		String output2 ="/*														"
				+ "*Function to chop a string in half. 							" 
				+ "*/                                                           " 
				+ "public static $4 foo($4 input) {                		" 
				+ "	if ($12 == null || $12.isEmpty()) {                    	" 
				+ "        return $12;                          				" 
				+ "	}                                                           " 
				+ "    $13 ($12.length() % 2 == 1) {                    		" 
				+ "        $18 \"cannot $2 an odd-$22 $4 $5 $6\";	" 
				+ "    }                                                        " 
				+ "	$18 $12.substring($12.$22() / 2);                 " 
				+ "}                                                          	";  
		
		assertEquals(output2, minifier.replaceIdentifiers(input2));
		
		//Test Case 3: Empty String
		String input3 = "";
		String output3 = "";
		assertEquals(output3, minifier.replaceIdentifiers(input3));
		
		//Test Case 4: Null String
		String input4 = null;
		String output4 = null;
		assertEquals(output4, minifier.replaceIdentifiers(input4));
		
		//Test Case 5: No identifier
		String input5 = "{ 2+3+4+5  }";
		String output5 = "{ 2+3+4+5  }";
		assertEquals(output5, minifier.replaceIdentifiers(input5));
		
		//Test Case 6: Input ends in non alphabet char
		String input6 = "{ 2+3+4+5  }";
		String output6 = "{ 2+3+4+5  }";
		assertEquals(output6, minifier.replaceIdentifiers(input6));
				
		//Test Case 7: Input ends in alphabet char
		String input7 = "{ 2+3+4+5  }pp";
		String output7 = "{ 2+3+4+5  }pp";
		assertEquals(output7, minifier.replaceIdentifiers(input7));
		
		//Test Case 8: Single & double char identifiers
		String input8 = "pp{p 2+3+4+5  p}pp ";
		String output8 = "pp{p 2+3+4+5  $1}$0 ";
		assertEquals(output8, minifier.replaceIdentifiers(input8));
//		System.out.println(minifier.replaceIdentifiers(input8));
		
		//Test Case 9: Probable error in decoding - should it be $1 or $14 here?
		String input9 = "pp{p 2+3p+p4+5  p}pp ";
		String output9 = "pp{p 2+3$1+$14+5  $1}$0 ";
		assertEquals(output9, minifier.replaceIdentifiers(input9));
//		System.out.println(minifier.replaceIdentifiers(input9));
		
	}

}
