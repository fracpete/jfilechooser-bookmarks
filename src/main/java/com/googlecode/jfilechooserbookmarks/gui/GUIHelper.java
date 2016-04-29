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
 * GUIHelper.java
 * Copyright (C) 2008-2016 University of Waikato, Hamilton, New Zealand
 */

package com.googlecode.jfilechooserbookmarks.gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;

/**
 * A little helper class for GUI related stuff.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 8793 $
 */
public class GUIHelper {

  /** the empty icon name. */
  public final static String EMPTY_ICON = "empty.gif";

  /**
   * Checks whether the image is available.
   *
   * @param name	the name of the image (filename without path but with
   * 			extension)
   * @return		true if image exists
   */
  public static boolean hasImageFile(String name) {
    return (getImageFilename(name) != null);
  }

  /**
   * Adds the path of the images directory to the name of the image.
   *
   * @param name	the name of the image to add the path to
   * @return		the full path of the image
   */
  public static String getImageFilename(String name) {
    String	result;
    String	dir;
    URL		url;

    result = null;

    dir = "com/googlecode/jfilechooserbookmarks/images/";
    try {
      url = ClassLoader.getSystemClassLoader().getResource(dir + name);
      if (url != null)
	result = dir + name;
    }
    catch (Exception e) {
      // ignored
    }

    return result;
  }

  /**
   * Returns an ImageIcon for the given class.
   *
   * @param cls		the class to get the icon for (gif, png or jpg)
   * @return		the ImageIcon or null if none found
   */
  public static ImageIcon getIcon(Class cls) {
    if (hasImageFile(cls.getName() + ".gif"))
      return getIcon(cls.getName() + ".gif");
    else if (hasImageFile(cls.getName() + ".png"))
      return getIcon(cls.getName() + ".png");
    else if (hasImageFile(cls.getName() + ".jpg"))
      return getIcon(cls.getName() + ".jpg");
    else
      return null;
  }

  /**
   * Returns an ImageIcon from the given name.
   *
   * @param name	the filename without path
   * @return		the ImageIcon or null if not available
   */
  public static ImageIcon getIcon(String name) {
    String	filename;

    filename = getImageFilename(name);
    if (filename != null)
      return new ImageIcon(ClassLoader.getSystemClassLoader().getResource(filename));
    else
      return null;
  }

  /**
   * Returns an ImageIcon from the given name.
   *
   * @param filename	the filename
   * @return		the ImageIcon or null if not available
   */
  public static ImageIcon getExternalIcon(String filename) {
    ImageIcon	result;

    try {
      result = new ImageIcon(ClassLoader.getSystemClassLoader().getResource(filename));
    }
    catch (Exception e) {
      result = null;
    }

    return result;
  }

  /**
   * Returns the ImageIcon for the empty icon.
   *
   * @return		the ImageIcon
   */
  public static ImageIcon getEmptyIcon() {
    return getIcon(EMPTY_ICON);
  }

  /**
   * Tries to determine the parent this panel is part of.
   *
   * @param cont	the container to get the parent for
   * @param parentClass	the class of the parent to obtain
   * @return		the parent if one exists or null if not
   */
  public static Object getParent(Container cont, Class parentClass) {
    Container	result;
    Container	parent;

    result = null;

    parent = cont;
    while (parent != null) {
      if (parentClass.isInstance(parent)) {
	result = parent;
	break;
      }
      else {
	parent = parent.getParent();
      }
    }

    return result;
  }

  /**
   * Tries to determine the frame the container is part of.
   *
   * @param cont	the container to get the frame for
   * @return		the parent frame if one exists or null if not
   */
  public static Frame getParentFrame(Container cont) {
    return (Frame) getParent(cont, Frame.class);
  }

  /**
   * Tries to determine the frame the component is part of.
   *
   * @param comp	the component to get the frame for
   * @return		the parent frame if one exists or null if not
   */
  public static Frame getParentFrame(Component comp) {
    if (comp instanceof Container)
      return (Frame) getParent((Container) comp, Frame.class);
    else
      return null;
  }

  /**
   * Tries to determine the dialog this container is part of.
   *
   * @param cont	the container to get the dialog for
   * @return		the parent dialog if one exists or null if not
   */
  public static Dialog getParentDialog(Container cont) {
    return (Dialog) getParent(cont, Dialog.class);
  }

  /**
   * Tries to determine the dialog this component is part of.
   *
   * @param comp	the component to get the dialog for
   * @return		the parent dialog if one exists or null if not
   */
  public static Dialog getParentDialog(Component comp) {
    if (comp instanceof Container)
      return (Dialog) getParent((Container) comp, Dialog.class);
    else
      return null;
  }

  /**
   * Tries to determine the internal frame this container is part of.
   *
   * @param cont	the container to start with
   * @return		the parent internal frame if one exists or null if not
   */
  public static JInternalFrame getParentInternalFrame(Container cont) {
    return (JInternalFrame) getParent(cont, JInternalFrame.class);
  }

  /**
   * Tries to determine the internal frame this component is part of.
   *
   * @param comp	the component to start with
   * @return		the parent internal frame if one exists or null if not
   */
  public static JInternalFrame getParentInternalFrame(Component comp) {
    if (comp instanceof Container)
      return (JInternalFrame) getParent((Container) comp, JInternalFrame.class);
    else
      return null;
  }

  /**
   * Tries to determine the component this panel is part of in this order:
   * 1. dialog, 2. child, 3. frame.
   *
   * @param comp	the component to get the parent component for, must
   * 			be a container actually
   * @return		the parent component if one exists or null if not
   * @see		#getParentDialog(Container)
   * @see		#getParentFrame(Container)
   */
  public static Component getParentComponent(Component comp) {
    Component	result;
    Container	cont;

    if (comp == null)
      return null;
    
    if (comp instanceof Container)
      cont = (Container) comp;
    else
      return null;

    result = getParentDialog(cont);
    if (result == null)
      result = getParentFrame(cont);

    return result;
  }

  /**
   * Closes the parent dialog/frame of this container.
   * 
   * @param cont	the container to close the parent for
   */
  public static void closeParent(Container cont) {
    Dialog		dialog;
    Frame		frame;
    JFrame		jframe;
    JInternalFrame	jintframe;
    int		i;
    WindowListener[] 	listeners;
    WindowEvent	event;

    if (getParentDialog(cont) != null) {
      dialog = getParentDialog(cont);
      dialog.setVisible(false);
    }
    else if (getParentFrame(cont) != null) {
      jintframe = getParentInternalFrame(cont);
      if (jintframe != null) {
	jintframe.doDefaultCloseAction();
      }
      else {
	frame = getParentFrame(cont);
	if (frame instanceof JFrame) {
	  jframe = (JFrame) frame;
	  if (jframe.getDefaultCloseOperation() == JFrame.HIDE_ON_CLOSE)
	    jframe.setVisible(false);
	  else if (jframe.getDefaultCloseOperation() == JFrame.DISPOSE_ON_CLOSE)
	    jframe.dispose();
	  else if (jframe.getDefaultCloseOperation() == JFrame.EXIT_ON_CLOSE)
	    System.exit(0);

	  // notify listeners
	  listeners = jframe.getWindowListeners();
	  event     = new WindowEvent(jframe, WindowEvent.WINDOW_CLOSED);
	  for (i = 0; i < listeners.length; i++)
	    listeners[i].windowClosed(event);
	}
	else {
	  frame.dispose();
	}
      }
    }
  }
}
