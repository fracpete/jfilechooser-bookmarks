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
 * DefaultIconLoader.java
 * Copyright (C) 2014-2016 University of Waikato, Hamilton, New Zealand
 */
package com.googlecode.jfilechooserbookmarks;

import com.googlecode.jfilechooserbookmarks.gui.GUIHelper;

import javax.swing.ImageIcon;

/**
 * Default icon loader.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class DefaultIconLoader
  extends AbstractIconLoader {

  /** for serialization. */
  private static final long serialVersionUID = 4246132624550617978L;

  /**
   * Returns the "down" icon.
   * 
   * @return		the icon
   */
  @Override
  public ImageIcon getDown() {
    return GUIHelper.getIcon("arrow_down.gif");
  }

  /**
   * Returns the "up" icon.
   * 
   * @return		the icon
   */
  @Override
  public ImageIcon getUp() {
    return GUIHelper.getIcon("arrow_up.gif");
  }

  /**
   * Returns the "add" icon.
   * 
   * @return		the icon
   */
  @Override
  public ImageIcon getAdd() {
    return GUIHelper.getIcon("add.gif");
  }

  /**
   * Returns the "remove" icon.
   * 
   * @return		the icon
   */
  @Override
  public ImageIcon getRemove() {
    return GUIHelper.getIcon("remove.gif");
  }

  /**
   * Returns the "rename" icon.
   * 
   * @return		the icon
   */
  @Override
  public ImageIcon getRename() {
    return GUIHelper.getEmptyIcon();
  }

  /**
   * Returns the "copy" icon.
   *
   * @return		the icon
   */
  public ImageIcon getCopy() {
    return GUIHelper.getIcon("copy.gif");
  }

  /**
   * Returns the "paste" icon.
   *
   * @return		the icon
   */
  public ImageIcon getPaste() {
    return GUIHelper.getIcon("paste.gif");
  }
}
