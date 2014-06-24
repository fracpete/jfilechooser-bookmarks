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
 * DefaultPropertiesHandler.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */
package com.googlecode.jfilechooserbookmarks;

import java.io.File;

/**
 * Default properties handler. Stores the bookmarks in the user's home
 * directory in directory {@link #DIR} as file {@link #FILENAME}.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class DefaultPropertiesHandler
  extends AbstractPropertiesHandler {

  /** for serialization. */
  private static final long serialVersionUID = -5922757670010317114L;

  /** the dir to store the bookmarks in. */
  public final static String DIR = ".jfcb";

  /** the properties to store the bookmarks in. */
  public final static String FILENAME = "FileChooserBookmarks.props";

  /**
   * Returns the filename for the properties.
   * 
   * @return		the filename
   */
  @Override
  protected String getFilename() {
    return System.getProperty("user.home") + File.separator + DIR + File.separator + FILENAME;
  }
}
