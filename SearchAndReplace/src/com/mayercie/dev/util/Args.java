/** ****************************************************************************
 *  @ingroup   ?????
 *  @file      Args.java
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
 *  @brief  Part of console application for searching and replacing specific value in file.
 *  
 *  Args is a container for parsing of starting arguments of the main class SearchAndReplace.java
 *  Args only contains Parameter definitions for JCommander and setters and getter Methods for the variables:
 *  search, replace, inputFile, outputFile and help
 *  
 *   
 *******************************************************************************/

package com.mayercie.dev.util;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;


public class Args {

	  @Parameter
	  private List<String> parameters = new ArrayList<>();

	  @Parameter(names ={"--search", "-s"}, description = "String or REGEX to search for in file (mandatory). Search pattern can be a regular expression therefore some special characters like \"$\" need to be escaped with \"\\$\" backspace before searching when not used in REGEX context.")
	  private String search;

	  @Parameter(names ={"--replace", "-r"}, description = "String or REGEX to replace in file (mandatory). Same rules as on search input apply.")
	  private String replace;
	  
	  @Parameter(names ={"--input", "-i"}, description = "Input file (mandatory)")
	  private String inputFile;
	  
	  @Parameter(names ={"--output", "-o"}, description = "Generated output file name and/or path. Input file can be overwritten.")
	  private String outputFile;

	  @Parameter(names = {"--help", "-h"}, help = true, description = "Usage information and help")
	  private boolean help;
	  
	  @Parameter(names = {"--verbose", "-v"}, description = "Sets verbosity level between 0 and 3 - 0 means zero output, 3 includes loading times.")
	  private int verbose = 1;
	
	public int getVerbositylevel() {
		return verbose;
	}

	public void setVerbositylevel(int verbose) {
		this.verbose = verbose;
	}

	public boolean isHelp() 
	{
		return help;
	}

	public void setHelp(boolean help) 
	{
		this.help = help;
	}

	public List<String> getParameters() 
	{
		return parameters;
	}

	public void setParameters(List<String> parameters) 
	{
		this.parameters = parameters;
	}

	public String getSearch()
	{
		return search;
	}

	public void setSearch(String search)
	{
		this.search = search;
	}

	public String getReplace() 
	{
		return replace;
	}

	public void setReplace(String replace) 
	{
		this.replace = replace;
	}

	public String getInputFile() 
	{
		return inputFile;
	}

	public void setInputFile(String inputFile) 
	{
		this.inputFile = inputFile;
	}

	public String getOutputFile() 
	{
		return outputFile;
	}

	public void setOutputFile(String outputFile) 
	{
		this.outputFile = outputFile;
	}
}
