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
 * AbstractIconLoader.java
 * Copyright (C) 2014-2016 University of Waikato, Hamilton, New Zealand
 */
package com.googlecode.jfilechooserbookmarks;

import javax.swing.ImageIcon;
import java.io.Serializable;

/**
 * Loads the icons for the buttons.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractIconLoader
  implements Serializable {

  /** for serialization. */
  private static final long serialVersionUID = 8151622241295547615L;

  /**
   * Returns the "down" icon.
   * 
   * @return		the icon
   */
  public abstract ImageIcon getDown();

  /**
   * Returns the "up" icon.
   * 
   * @return		the icon
   */
  public abstract ImageIcon getUp();

  /**
   * Returns the "add" icon.
   * 
   * @return		the icon
   */
  public abstract ImageIcon getAdd();

  /**
   * Returns the "remove" icon.
   * 
   * @return		the icon
   */
  public abstract ImageIcon getRemove();

  /**
   * Returns the "rename" icon.
   * 
   * @return		the icon
   */
  public abstract ImageIcon getRename();

  /**
   * Returns the "copy" icon.
   *
   * @return		the icon
   */
  public abstract ImageIcon getCopy();

  /**
   * Returns the "paste" icon.
   *
   * @return		the icon
   */
  public abstract ImageIcon getPaste();
}
