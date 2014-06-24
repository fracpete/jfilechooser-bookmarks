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
 * CustomHandler.java
 * Copyright (C) 2014 University of Waikato, Hamilton, New Zealand
 */
package com.googlecode.jfilechooserbookmarks.example;

import java.io.File;

import javax.swing.JFileChooser;

import com.googlecode.jfilechooserbookmarks.AbstractBookmarksPanel;
import com.googlecode.jfilechooserbookmarks.AbstractFactory;
import com.googlecode.jfilechooserbookmarks.AbstractPropertiesHandler;
import com.googlecode.jfilechooserbookmarks.DefaultFactory;

/**
 * Example class that demonstrates the use of the bookmarks manager
 * with a custom properties handler/panel.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
@SuppressWarnings("serial")
public class CustomHandler {
  
  public static class CustomPropertiesHandler
    extends AbstractPropertiesHandler {

    @Override
    protected String getFilename() {
      return System.getProperty("user.home") + File.separator + ".jfcb.props";
    }
  }

  public static class CustomFactory
    extends DefaultFactory {
    
    @Override
    public AbstractPropertiesHandler newPropertiesHandler() {
      return new CustomPropertiesHandler();
    }
  }
  
  public static class CustomFileChooserBookmarksPanel
    extends AbstractBookmarksPanel {
    
    @Override
    protected AbstractFactory newFactory() {
      return new CustomFactory();
    }
  }
  
  public static void main(String[] args) throws Exception {
    JFileChooser chooser = new JFileChooser();
    chooser.setMultiSelectionEnabled(true);
    chooser.setAcceptAllFileFilterUsed(true);
    CustomFileChooserBookmarksPanel panel = new CustomFileChooserBookmarksPanel();
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
