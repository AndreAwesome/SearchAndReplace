/** ****************************************************************************
 *  @ingroup   ?????
 *  @file      SearchAndReplace.java
 *  @date      $Date$
 *  
 *  \internal
 *
 *  MAYER & CIE Rundstrickmaschinen
 *  Abteilung: EE
 *
 *  Projekt 	: Replacement of String(s) in File 
 *  Revision	: $Rev$
 *  Dev. Env.	: Eclipse IDE
 *  \endinternal     
 *
 *  @author André Gluderer
 *  
 *  @brief  Console application for searching and replacing specific value in file.
 *  
 *  Console application with the purpose of finding and replacing values - both values are given as 
 *  starting parameters. Input file can also be overwritten when given as output parameter.
 *  Mandatory values are: 
 *  --input or -i: 
 *  for input file or path 
 *  --search or -s: 
 *  for value to be replaced 
 *  --replace or -r:
 *   for value to be used as replacement 
 *  Optional value: 
 *  --output or -o:
 *  for file or path for output file - with no value given program is making a copy of the original
 *  
 *   
 *******************************************************************************/

package com.mayercie.dev.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.beust.jcommander.JCommander;


public class SearchAndReplace {

	static Args args;
	String[] startingArguments = {};
	JCommander.Builder builder = JCommander.newBuilder();
	
	public SearchAndReplace()
	{
		args = new Args();
	}
	
	public SearchAndReplace(String[] arguments) 
	{
		args = new Args();
		parseArgsWithJCommander(arguments);
	}
	
	public void parseArgsWithJCommander(String[] argsv) {
		//parse arguments
		
		builder
		.addObject(args)
		.build()
		.parse(argsv);
		startingArguments = argsv;
	}
	
	public Args getArgs() 
	{
		return args;
	}
	
	/** ****************************************************************************
	 *  @internal Fkt. Name: print()
	 *  \endinternal
	 *  
	 *  @brief Print method for testing & verbosity reasons
	 *  
	 *  @return    -
	 *     
	 *******************************************************************************/
	public void print() 
	{
		String wSpaceInfo = "\nWhitespace Recognition: -->";
		String wSpace = whiteSpaceRecognizer(getArgs().getReplace());
		if (!wSpace.equals(getArgs().getReplace())) 
		{
			wSpaceInfo += wSpace + "<-- Length: " + wSpace.length();
		}
		else 
		{
			wSpaceInfo = "";
		}
		System.out.println("***** Info ******");
		System.out.println("Search:      " + getArgs().getSearch() 
							+ "\nReplacement: " + getArgs().getReplace()
							+ " Rep.Lenght:   " + getArgs().getReplace().length()
							+ wSpaceInfo 
							+ "\nInput:       " + getArgs().getInputFile() 
							+ "\nOutput:      " + getArgs().getOutputFile() 
							+ "\nHelp:        " + getArgs().isHelp() 
							+ "\nVerb. Level: " + getArgs().getVerbositylevel());
		System.out.println("*****************");
	}
	
	/** ****************************************************************************
	 *  @internal Fkt. Name: checkInputs()
	 *  \endinternal
	 *  
	 *  @brief Checks parameter user inputs
	 *  
	 *  Checks if all mandatory parameter arguments where inputted by the user.
	 *  
	 *  @return    -
	 *     
	 *******************************************************************************/
	public void checkInputs() throws SearchAndReplaceUsageException 
	{
		//print usage info is help is called or no arguments where entered
		if (getArgs().isHelp() || startingArguments.length < 1) 
		{
			//print out usage text
			builder
	        .addObject(getArgs())
	        .build().usage();
			//exit system
			System.exit(1);
		}
		
		String errorm = "Mandatory parameters need to be filled in. Mandatory parameters are: --input, --search, --replace";
		if (getArgs().getInputFile() == null || getArgs().getSearch() == null 
		   || getArgs().getReplace() == null) 
		{
			throw new SearchAndReplaceUsageException(errorm);
		}
	}
	
	
	/** ****************************************************************************
	 *  @internal Fkt. Name: loadFile(String path)
	 *  \endinternal
	 *  
	 *  @brief Load File with given path parameter
	 *  
	 *  Loading file from given path - throws FileNotFoundException when file not found 
	 *  and IOException when not readable.
	 *  
	 *  @attention -
	 *  
	 *  @return    String representation of loaded file
	 *     
	 *******************************************************************************/
	public String loadFile(String path) throws IOException, FileNotFoundException
	{
		Long loadingStart = System.currentTimeMillis(); 
		String compareFileContent = "";
		if(getArgs().getVerbositylevel() > 0) {
			System.out.println("Loading File: " + path);
		}
		File file = new File(path);
		
		if (file.exists()) 
		{
			
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			  
			  String st = ""; 
			  while ((st = br.readLine()) != null) 
			  {
				  //System.out.println(st); 
				  compareFileContent += st;
				  compareFileContent += "\n";
			  }
			br.close();   
		}
		else
		{
			throw new FileNotFoundException("No file found at: " + path);
		}
		Timestamp loadingEnd = new Timestamp(System.currentTimeMillis() - loadingStart);
		if (getArgs().getVerbositylevel() > 2) 
		{
			System.out.println("Loading took: " + new SimpleDateFormat("mm:ss.SSS").format(loadingEnd));	
		}
		return compareFileContent;
	}
	
	/** ****************************************************************************
	 *  @internal Fkt. Name: parseAndReplaceContentFile(String input, String search, String replace)
	 *  \endinternal
	 *  
	 *  @brief Replaces all found search Strings with replacement String 
	 *  
	 *  Replaces all found search Strings in input String with replace String 
	 *  
	 *  @attention Regular expression can be used as search & replace input, therefore special characters need to be escaped by \ if 
	 *  they are not used in a regex context.
	 *  
	 *  @return    String with replaced values.
	 *     
	 *******************************************************************************/
	private String parseAndReplaceContentFile(String input, String search, String replace) 
	{
		String repacementString = whiteSpaceRecognizer(replace);
		if (getArgs().getVerbositylevel() > 0)
			System.out.println("Replacing \"" + search + "\" with \"" + repacementString + "\"");
		
		String out = input.replaceAll(search, repacementString);
		return out;
	}
	
	/** ****************************************************************************
	 *  @internal Fkt. writeOutputFile(String input)
	 *  \endinternal
	 *  
	 *  @brief Writes String given in parameter to either given file path or generates
	 *  new file path by generateCopyFilePath(String path)
	 *  
	 *  @return    -
	 *     
	 *******************************************************************************/
	private void writeOutputFile(String input) 
	{
		Long writingStart = System.currentTimeMillis();
		File woutFile = null;
		//System.out.println(args.getOutputFile()); 
		if (getArgs().getOutputFile() != null) 
		{
			woutFile = new File(getArgs().getOutputFile());
		}
		else 
		{			
			woutFile = new File(generateCopyFilePath(getArgs().getInputFile()));
		}
		
		if (getArgs().getVerbositylevel() > 0) 
		{
			System.out.println("Writing File: " + woutFile.getAbsolutePath());
		}
		
		OutputStream os = null;
        try {
        	//System.out.println(woutFile); 
            os = new FileOutputStream(woutFile);
            os.write(input.getBytes(), 0, input.length());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                os.close();
                
                Timestamp writingEnd = new Timestamp(System.currentTimeMillis() - writingStart);
                if (getArgs().getVerbositylevel() > 2) 
                {
        			System.out.println("Writing took: " + new SimpleDateFormat("mm:ss.SSS").format(writingEnd));	
        		}
                
                if (getArgs().getVerbositylevel() > 0) 
                {
                	 System.out.println(".... done!");
                }
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
		
	}
	
	/** ****************************************************************************
	 *  @internal Fkt. Name: generateCopyFilePath(String path)
	 *  \endinternal
	 *  
	 *  @brief Generates new filename for copy
	 *  
	 *  Generates new file name for copy of old file when no new path or file was given as starting parameter - 
	 *  if generated file name already exists a counting number gets added in front of file name.
	 *  
	 *  @attention -
	 *  
	 *  @return    String with new file name for copy
	 *     
	 *******************************************************************************/
	private String generateCopyFilePath(String path) 
	{
		String newGeneratedFile = path;
	
		Path pathOfFile = Paths.get(path);
	
		String prefix = "Copy_of_";
		boolean copyCreated = false;
		int i = 0;
		String newFileName = "Copy_of_" + pathOfFile.getFileName();
		while (!copyCreated) 
		{
			i ++;
			if (new File(newGeneratedFile.replace(pathOfFile.getFileName().toString(), newFileName)).exists()) 
			{
				newFileName = i + "_" + prefix + pathOfFile.getFileName();
			}
			else 
			{
				copyCreated = true;
			}
		}
		return newGeneratedFile.replace(pathOfFile.getFileName().toString(), newFileName);
	}
	
	public void executeSearchAndReplace() throws IOException, FileNotFoundException 
	{
		//load file, replace search string and save in new file
		writeOutputFile(parseAndReplaceContentFile(loadFile(getArgs().getInputFile()), getArgs().getSearch(), getArgs().getReplace()));
	}
	
	//Resolves the console deleting whitespace characters problem by replacing \\S
	public String whiteSpaceRecognizer(String replacement) 
	{
		String repWithSpaces = "";
		
		if (replacement.contains("\\S")) 
		{
			repWithSpaces = replacement.replace("\\S", " ");
			//System.out.println("-->" + repWithSpaces + "<--");
			//System.out.println(repWithSpaces.length());
			return repWithSpaces;
		}
		else 
		{
			return replacement;
		}
	}
	
	
	
	/** ****************************************************************************
	 *  @internal Fkt. Name: main(String[] argsv)
	 *  \endinternal
	 *  
	 *  @brief Main method parses starting arguments and tries to execute string replacement. 
	 *  
	 * 	Possible outcomes:
	 * 	- with flag --help or -h true or no starting parameters given: 
	 * 	  Usage information is printed and program exits.
	 *  - On Error: Program exits.
	 *  - Values are replaced in given input file and new file is created or old file overwritten.
	 *  
	 *******************************************************************************/
	public static void main(String[] argsv) 
	{
			//create program instance
			SearchAndReplace sar = new SearchAndReplace();
			//parse starting arguments
			sar.parseArgsWithJCommander(argsv);
			
			//Print for testing only
			if(sar.getArgs().getVerbositylevel() > 1)
				sar.print();
			
			try 
			{
				//verify if inputs are present
				sar.checkInputs();
				//execute Search and Replace
				sar.executeSearchAndReplace();
			}
			catch (IOException | SearchAndReplaceUsageException e) 
			{
				e.printStackTrace();
			}
			
		
	}

	

	
}
