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
 * AbstractFactory.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */
package com.googlecode.jfilechooserbookmarks;

import java.io.Serializable;

/**
 * Ancestor for factory classes.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractFactory
  implements Serializable {

  /** for serialization. */
  private static final long serialVersionUID = 6161600192296289858L;

  /**
   * Returns the singleton of the bookmarks manager to use.
   * 
   * @return		the singleton instance
   */
  public abstract AbstractBookmarksManager getBookmarksManager();

  /**
   * Returns a new instance of the properties handler to be used.
   * 
   * @return		the handler instance
   */
  public abstract AbstractPropertiesHandler newPropertiesHandler();

  /**
   * Returns a new instance of the icon loader to be used.
   * 
   * @return		the loader instance
   */
  public abstract AbstractIconLoader newIconLoader();
}
