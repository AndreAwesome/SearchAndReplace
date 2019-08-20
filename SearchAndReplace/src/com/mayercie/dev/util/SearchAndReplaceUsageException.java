/** ****************************************************************************
 *  @ingroup   ?????
 *  @file      SearchAndReplaceUsageException.java
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
 *  SearchAndReplaceUsageException is a custom exception for SearchAndReplace used for checking user input
 *  
 *   
 *******************************************************************************/

package com.mayercie.dev.util;

public class SearchAndReplaceUsageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1564794665798448691L;

	public SearchAndReplaceUsageException(String errorMessage) {
		super(errorMessage);
	}
}
