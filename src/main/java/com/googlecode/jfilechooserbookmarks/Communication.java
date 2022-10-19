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

/*
 * Communication.java
 * Copyright (C) 2022 University of Waikato, Hamilton, New Zealand
 */

package com.googlecode.jfilechooserbookmarks;

import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * Interface for communication implementations between widget and bookmarks.
 *
 * @author fracpete (fracpete at waikato dot ac dot nz)
 */
public interface Communication {

  /**
   * Sets the current directory.
   *
   * @param dir		the directory to use
   */
  public void setCurrentDirectory(File dir);

  /**
   * Returns the current directory.
   *
   * @return		the directory in use
   */
  public File getCurrentDirectory();

  /**
   * Returns all the selected files.
   *
   * @return		the currently selected files
   */
  public File[] getSelectedFiles();

  /**
   * Scrolls the specified file into view
   *
   * @param f		the file to scroll into view
   */
  public void ensureFileIsVisible(File f);

  /**
   * Adds the property change listener.
   *
   * @param l		the listener to add
   */
  public void addPropertyChangeListener(PropertyChangeListener l);

  /**
   * Removes the property change listener.
   *
   * @param l		the listener to remove
   */
  public void removePropertyChangeListener(PropertyChangeListener l);
}
