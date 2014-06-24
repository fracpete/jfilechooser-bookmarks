/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * AbstractPropertiesHandler.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */
package com.googlecode.jfilechooserbookmarks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.Properties;

/**
 * Ancestor for handlers that load/save the properties with the bookmarks.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractPropertiesHandler
  implements Serializable {
  
  /** for serialization. */
  private static final long serialVersionUID = -7969703434827836907L;

  /**
   * Returns the filename for the properties.
   * 
   * @return		the filename
   */
  protected abstract String getFilename();

  /**
   * Saves the properties.
   * 
   * @param props	the properties to save
   */
  public boolean saveProperties(Properties props) {
    boolean		result;
    String		filename;
    File		file;
    BufferedWriter	writer;
    
    result   = false;
    filename = getFilename();
    writer   = null;
    
    try {
      file   = new File(getFilename());
      if (!file.getParentFile().exists())
	file.getParentFile().mkdirs();
      writer = new BufferedWriter(new FileWriter(file));
      props.store(writer, null);
      writer.flush();
      result = true;
    }
    catch (Exception e) {
      System.err.println("Failed to save bookmarks to " + filename);
      e.printStackTrace();
    }
    finally {
      try {
	if (writer != null)
	  writer.close();
      }
      catch (Exception e) {
	// ignored
      }
    }
    
    return result;
  }
  
  /**
   * Loads the properties.
   * 
   * @return		the properties loaded from disk
   */
  public Properties loadProperties() {
    Properties		result;
    String		filename;
    BufferedReader	reader;
    File		file;
    
    result   = new Properties();
    filename = getFilename();
    reader   = null;
    
    try {
      file = new File(filename);
      if (file.exists() && !file.isDirectory()) {
	reader = new BufferedReader(new FileReader(filename));
	result.load(reader);
      }
    }
    catch (Exception e) {
      System.err.println("Failed to load bookmarks from " + filename);
      e.printStackTrace();
    }
    finally {
      try {
	if (reader != null)
	  reader.close();
      }
      catch (Exception e) {
	// ignored
      }
    }
    
    return result;
  }
}
