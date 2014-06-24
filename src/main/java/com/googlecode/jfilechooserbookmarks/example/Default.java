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
 * Default.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */
package com.googlecode.jfilechooserbookmarks.example;

import java.io.File;

import javax.swing.JFileChooser;

import com.googlecode.jfilechooserbookmarks.DefaultBookmarksPanel;

/**
 * Example class that demonstrates the use of the bookmarks manager.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class Default {
  public static void main(String[] args) throws Exception {
    JFileChooser chooser = new JFileChooser();
    chooser.setMultiSelectionEnabled(true);
    chooser.setAcceptAllFileFilterUsed(true);
    DefaultBookmarksPanel panel = new DefaultBookmarksPanel();
    // the panel needs to know which JFileChooser to notify when the user
    // selects a bookmark and the current directory needs to change
    panel.setOwner(chooser);
    chooser.setAccessory(panel);
    int retVal = chooser.showOpenDialog(null);
    if (retVal == JFileChooser.APPROVE_OPTION) {
      for (File file: chooser.getSelectedFiles())
	System.out.println(file);
    }
  }
}
