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
 * AbstractFileChooserBookmarksManger.java
 * Copyright (C) 2013-2014 University of Waikato, Hamilton, New Zealand
 */
package com.googlecode.jfilechooserbookmarks;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Manager for the filechooser bookmarks.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 8361 $
 */
public class FileChooserBookmarksManger
  implements Serializable {

  /** for serialization. */
  private static final long serialVersionUID = 4461451164139626835L;

  /** the properties to store the bookmarks in. */
  public final static String FILENAME = "FileChooserBookmarks.props";

  /** the property for the number of bookmarks stored. */
  public final static String BOOKMARK_COUNT = "BookmarkCount";

  /** the property prefix for a bookmark name. */
  public final static String BOOKMARK_PREFIX_NAME = "BookmarkName.";

  /** the property prefix for a bookmark directory. */
  public final static String BOOKMARK_PREFIX_DIR = "BookmarkDir.";

  /** the singleton. */
  protected static FileChooserBookmarksManger m_Singleton;
  
  /** the properties. */
  protected Properties m_Properties;
  
  /** the props handler to use. */
  protected AbstractPropertiesHandler m_Handler;

  /**
   * Default constructor.
   * 
   * @param handler	the handler to use
   */
  protected FileChooserBookmarksManger(AbstractPropertiesHandler handler) {
    super();
    m_Handler = handler;
  }

  /**
   * Loads all the bookmarks that are currently stored in the properties.
   * 
   * @return		the current bookmarks
   */
  public synchronized List<FileChooserBookmark> load() {
    List<FileChooserBookmark>	result;
    Properties			props;
    int				count;
    int				i;
    FileChooserBookmark		bookmark;
    String			name;
    File	dir;
    
    result = new ArrayList<FileChooserBookmark>();
    props  = getProperties();
    count  = Integer.parseInt(props.getProperty(BOOKMARK_COUNT, "0"));
    
    for (i = 0; i < count; i++) {
      name     = props.getProperty(BOOKMARK_PREFIX_NAME + i);
      dir      = new File(props.getProperty(BOOKMARK_PREFIX_DIR + i));
      bookmark = new FileChooserBookmark(name, dir);
      result.add(bookmark);
    }
    
    return result;
  }
  
  /**
   * Saves all the bookmarks in the properties.
   * 
   * @param bookmarks	the current bookmarks
   */
  public synchronized boolean save(List<FileChooserBookmark> bookmarks) {
    boolean		result;
    Properties		props;
    int			i;
    FileChooserBookmark	bookmark;
    
    props = new Properties();
    props.clear();
    props.setProperty(BOOKMARK_COUNT, "" + bookmarks.size());
    
    for (i = 0; i < bookmarks.size(); i++) {
      bookmark = bookmarks.get(i);
      props.setProperty(BOOKMARK_PREFIX_NAME + i, bookmark.getName());
      props.setProperty(BOOKMARK_PREFIX_DIR  + i, bookmark.getDirectory().getAbsolutePath());
    }
    
    result       = m_Handler.saveProperties(props);
    m_Properties = props;
    
    return result;
  }

  /**
   * Returns the underlying properties.
   *
   * @return		the properties
   */
  public synchronized Properties getProperties() {
    if (m_Properties == null)
      m_Properties = m_Handler.loadProperties();

    return m_Properties;
  }

  /**
   * Returns the singleton of the manager.
   * 
   * @return		the singleton
   */
  public synchronized static FileChooserBookmarksManger getSingleton(AbstractPropertiesHandler handler) {
    if (m_Singleton == null)
      m_Singleton = new FileChooserBookmarksManger(handler);
    return m_Singleton;    
  }
}
