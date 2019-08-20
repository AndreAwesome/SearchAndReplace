package com.mayercie.dev.util;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class SearchAndReplaceTest {

	
	static String TEST_RESULT_FILE = "test_files\\test_result_file.txt";
	
	@BeforeAll
	static void initializeExternalResources() {
		System.out.println("Initializing external resources...");
		
	}
 
	/*
	@BeforeEach
	void initializeMockObjects() {
		System.out.println("Initializing mock objects...");
	}
 */
	
	@Test
	@DisplayName("Loading - File Not Found")
	void testFileNotFound() {
		SearchAndReplace noFile = new SearchAndReplace(new String[] {"-i","test_files\\no_file.txt", "-o", "test_files\\test_result_file.txt","-s", "Lorem", "-r", "REPLACED"});
		assertThrows(FileNotFoundException.class,
			           () -> noFile.executeSearchAndReplace(),
			           "Expected doThing() to throw, but it didn't");
	}
	
	
	@Test
	@DisplayName("Start Parameters - Not Enough Input")
	void testNotEnoughInput() {
		SearchAndReplace notEnoughInput = new SearchAndReplace(new String[] {"-input","test_files\\test_file.txt", "-o", "test_files\\test_result_file.txt","-r", "REPLACED"});
		assertThrows(SearchAndReplaceUsageException.class,
			           () -> notEnoughInput.checkInputs(),
			           "Expected doThing() to throw, but it didn't");
	}
	
	@Test
	@DisplayName("Start Parameters - Wrong Input")
	void testWrongInputFlag() {
		SearchAndReplace wrongInputFlag = new SearchAndReplace(new String[] {"-imputs","test_files\\test_file.txt", "-o", "test_files\\test_result_file.txt","-s", "Lorem", "-r", "REPLACED"});
		assertThrows(SearchAndReplaceUsageException.class,
			           () -> wrongInputFlag.checkInputs(),
			           "Expected doThing() to throw, but it didn't");
	}
	
	@Test
	@DisplayName("Start Parameters - Wrong Input Format")
	void testWrongInputFormat() {
		SearchAndReplace wrongInputFormat = new SearchAndReplace(new String[] {"-i","test_files\\test_file.txt", "-o", "test_files\\test_result_file.txt","-s", "$", "-r", "REPLACED"});
		try {
		wrongInputFormat.checkInputs();
		wrongInputFormat.executeSearchAndReplace();
		String compareString = "";
		
			compareString = wrongInputFormat.loadFile(TEST_RESULT_FILE);
			//special character $ should still be there
			assertTrue(compareString.toLowerCase().contains("$".toLowerCase()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SearchAndReplaceUsageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Simple String Replacement")
	void testSearchAndReplaceString() {
		SearchAndReplace sarString = new SearchAndReplace(new String[] {"-i","test_files\\test_file.txt", "-o", "test_files\\test_result_file.txt","-s", "Lorem", "-r", "STRING_TEST"});
		try {
			sarString.checkInputs();
			sarString.executeSearchAndReplace();
			String compareString = "";
		
			compareString = sarString.loadFile(TEST_RESULT_FILE);
			//Lorem should be gone
			assertFalse(compareString.toLowerCase().contains("Lorem".toLowerCase()));
			//STRING_TEST should be visible
			assertTrue(compareString.toLowerCase().contains("STRING_TEST".toLowerCase()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SearchAndReplaceUsageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Special Character Replacement")
	void testSearchAndReplaceSpecialCharacter() {
		SearchAndReplace sarSpecialChar = new SearchAndReplace(new String[] {"-i","test_files\\test_file.txt","-o", "test_files\\test_result_file.txt","-s", "\\$", "-r", "SPECIAL_CHAR_TEST"});
		try {
			sarSpecialChar.checkInputs();
			sarSpecialChar.executeSearchAndReplace();
			String compareString = "";
		
			compareString = sarSpecialChar.loadFile(TEST_RESULT_FILE);
			//$ should be gone
			assertFalse(compareString.toLowerCase().contains("\\$".toLowerCase()));
			//SPECIAL_CHAR_TEST should be visible
			assertTrue(compareString.toLowerCase().contains("SPECIAL_CHAR_TEST".toLowerCase()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SearchAndReplaceUsageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Regular Expression in Search")
	void testSearchRegexAndReplace() {
		SearchAndReplace sarRegex = new SearchAndReplace(new String[] {"-i","test_files\\test_file.txt","-o", "test_files\\test_result_file.txt","-s", "\\[(.*?)\\]", "-r", "REGEX_TEST"});
		try {
			sarRegex.checkInputs();
			sarRegex.executeSearchAndReplace();
			String compareString = "";
		
			compareString = sarRegex.loadFile(TEST_RESULT_FILE);
			//System.out.println(compareString);
			//$ should be gone
			assertFalse(compareString.toLowerCase().contains("[vel]".toLowerCase()));
			//SPECIAL_CHAR_TEST should be visible
			assertTrue(compareString.toLowerCase().contains("REGEX_TEST".toLowerCase()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SearchAndReplaceUsageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Regular Expression in Search & Replacement")
	void testSearchRegexAndReplaceRegex() {
		SearchAndReplace repRegex = new SearchAndReplace(new String[] {"-i","test_files\\test_file.txt","-o", "test_files\\test_result_file.txt","-s", "<b>([^<]*)</b>", "-r", "$1"});
		try {
			repRegex.checkInputs();
			repRegex.executeSearchAndReplace();
			String compareString = "";
		
			compareString = repRegex.loadFile(TEST_RESULT_FILE);
			//System.out.println(compareString);
			//<b></b> bold tag should be gone
			assertFalse(compareString.toLowerCase().contains("<b>".toLowerCase()));
			//Text from bold tag should still be there
			assertTrue(compareString.toLowerCase().contains("Bold Text".toLowerCase()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SearchAndReplaceUsageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Whitespace in front of Replacement")
	void testSearchRegexAndReplaceWhitespace() {
		SearchAndReplace repRegexWhite = new SearchAndReplace(new String[] {"-i","test_files\\test_file.txt","-o", "test_files\\test_result_file.txt","-s", "\\$", "-r", "\\S€"});
		try {
			repRegexWhite.checkInputs();
			repRegexWhite.executeSearchAndReplace();
			String compareString = "";
		
			compareString = repRegexWhite.loadFile(TEST_RESULT_FILE);
			//System.out.println(compareString);
			//<b></b> bold tag should be gone
			assertFalse(compareString.toLowerCase().contains("$".toLowerCase()));
			//Text from bold tag should still be there
			assertTrue(compareString.toLowerCase().contains("9.99 €".toLowerCase()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SearchAndReplaceUsageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	@AfterEach
	void cleanUp() {
		System.out.println("Deleting generated files and resources...");
		//delete generated Test files
		File file = new File(TEST_RESULT_FILE); 
        if (file.exists()) 
        {
        	if(file.delete()) 
            { 
                System.out.println(TEST_RESULT_FILE + " deleted successfully"); 
            } 
            else
            { 
                System.out.println("Failed to delete the file"); 
            } 
        }
        
		
	}

}
