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
 * DefaultFactory.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */
package com.googlecode.jfilechooserbookmarks;

/**
 * Default factory for implementations.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class DefaultFactory
  extends AbstractFactory {

  /** for serialization. */
  private static final long serialVersionUID = 6977168963256376965L;
  
  /** the singleton instance of the manager. */
  protected static AbstractBookmarksManager m_Manager;
  
  /**
   * Returns the singleton of the bookmarks manager to use.
   * 
   * @return		the singleton instance
   */
  @Override
  public synchronized AbstractBookmarksManager getBookmarksManager() {
    if (m_Manager == null)
      m_Manager = new DefaultBookmarksManager(newPropertiesHandler());
    return m_Manager;
  }

  /**
   * Returns a new instance of the properties handler to be used.
   * 
   * @return		the handler instance
   */
  @Override
  public AbstractPropertiesHandler newPropertiesHandler() {
    return new DefaultPropertiesHandler();
  }

  /**
   * Returns a new instance of the icon loader to be used.
   * 
   * @return		the loader instance
   */
  @Override
  public AbstractIconLoader newIconLoader() {
    return new DefaultIconLoader();
  }
}
