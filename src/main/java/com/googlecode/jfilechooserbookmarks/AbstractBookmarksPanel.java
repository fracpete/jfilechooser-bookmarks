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
 * AbstractBookmarksPanel.java
 * Copyright (C) 2013-2016 University of Waikato, Hamilton, New Zealand
 */
package com.googlecode.jfilechooserbookmarks;

import com.github.fracpete.jclipboardhelper.ClipboardHelper;
import com.googlecode.jfilechooserbookmarks.gui.BaseList;
import com.googlecode.jfilechooserbookmarks.gui.BasePanel;
import com.googlecode.jfilechooserbookmarks.gui.BaseScrollPane;
import com.googlecode.jfilechooserbookmarks.gui.GUIHelper;
import com.googlecode.jfilechooserbookmarks.gui.MouseUtils;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Panel for bookmarking directories in a {@link JFileChooser}.
 * <p>
 * You can customize the panel by implementing the {@link #newFactory()}
 * method, returning a custom factory class instance.
 * 
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 8361 $
 */
public abstract class AbstractBookmarksPanel
  extends BasePanel {

  /** for serialization. */
  private static final long serialVersionUID = -1969362821325599909L;

  /** the owner. */
  protected JFileChooser m_Owner;
  
  /** the list of bookmarks. */
  protected BaseList m_ListBookmarks;
  
  /** the list model with the bookmarks. */
  protected DefaultListModel m_ModelBookmarks;
  
  /** the panel for the buttons. */
  protected JPanel m_PanelButtons;
  
  /** the button for adding a bookmark. */
  protected JButton m_ButtonAdd;
  
  /** the button for remove a bookmark. */
  protected JButton m_ButtonRemove;
  
  /** the button for moving a bookmark up. */
  protected JButton m_ButtonMoveUp;
  
  /** the button for moving a bookmark down. */
  protected JButton m_ButtonMoveDown;

  /** the button for copying the current directory. */
  protected JButton m_ButtonCopy;

  /** the button for pasting the directory from the clipboard. */
  protected JButton m_ButtonPaste;

  /** the listener for directory changes. */
  protected PropertyChangeListener m_DirChangeListener;
  
  /** whether to skip changes to current directory. */
  protected boolean m_SkipDirectoryChanges;
  
  /** the factor to use. */
  protected AbstractFactory m_Factory;
  
  /** the icon loader to use. */
  protected AbstractIconLoader m_IconLoader;
  
  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();
    
    m_DirChangeListener = new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
	if (evt.getPropertyName().equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
	  if (!m_SkipDirectoryChanges)
	    m_ListBookmarks.clearSelection();
	}
      }
    };
    
    m_Factory    = newFactory();
    m_IconLoader = m_Factory.newIconLoader();
  }
  
  /**
   * Initializes the widgets.
   */
  @Override
  protected void initGUI() {
    super.initGUI();
    
    setLayout(new BorderLayout());
    
    m_ModelBookmarks = loadBookmarks();
    m_ListBookmarks = new BaseList(m_ModelBookmarks);
    m_ListBookmarks.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
	if (MouseUtils.isRightClick(e)) {
	  JPopupMenu menu = getPopupMenu(e);
	  if (menu != null)
	    menu.show(m_ListBookmarks, e.getX(), e.getY());
	}
	else {
	  super.mouseClicked(e);
	}
      }
    });
    m_ListBookmarks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
	updateButtons();
	if (m_Owner == null)
	  return;
	if (m_ListBookmarks.getSelectedIndices().length != 1)
	  return;
	Bookmark bookmark = (Bookmark) m_ListBookmarks.getSelectedValue();
	if (!bookmark.getDirectory().exists())
	  return;
	m_SkipDirectoryChanges = true;
	m_Owner.setCurrentDirectory(bookmark.getDirectory().getAbsoluteFile());
	m_Owner.ensureFileIsVisible(bookmark.getDirectory().getAbsoluteFile());
	m_SkipDirectoryChanges = false;
      }
    });
    add(new BaseScrollPane(m_ListBookmarks), BorderLayout.CENTER);
    
    m_PanelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
    add(m_PanelButtons, BorderLayout.SOUTH);
    
    m_ButtonAdd = new JButton(m_IconLoader.getAdd());
    m_ButtonAdd.setToolTipText("Adds the current directory as bookmark");
    m_ButtonAdd.setContentAreaFilled(false);
    m_ButtonAdd.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    m_ButtonAdd.setBackground(getBackground());
    m_ButtonAdd.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
	addBookmark();
      }
    });
    m_PanelButtons.add(m_ButtonAdd);
    
    m_ButtonRemove = new JButton(m_IconLoader.getRemove());
    m_ButtonRemove.setToolTipText("Removes the selected bookmark(s)");
    m_ButtonRemove.setContentAreaFilled(false);
    m_ButtonRemove.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    m_ButtonRemove.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
	removeBookmark(m_ListBookmarks.getSelectedIndices());
      }
    });
    m_PanelButtons.add(m_ButtonRemove);
    
    m_ButtonMoveUp = new JButton(m_IconLoader.getUp());
    m_ButtonMoveUp.setToolTipText("Moves the selected bookmark(s) up");
    m_ButtonMoveUp.setContentAreaFilled(false);
    m_ButtonMoveUp.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    m_ButtonMoveUp.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
	if (m_ListBookmarks.canMoveUp())
	  m_ListBookmarks.moveUp();
	saveBookmarks(m_ModelBookmarks);
      }
    });
    m_PanelButtons.add(m_ButtonMoveUp);
    
    m_ButtonMoveDown = new JButton(m_IconLoader.getDown());
    m_ButtonMoveDown.setToolTipText("Moves the selected bookmark(s) down");
    m_ButtonMoveDown.setContentAreaFilled(false);
    m_ButtonMoveDown.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    m_ButtonMoveDown.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
	if (m_ListBookmarks.canMoveDown())
	  m_ListBookmarks.moveDown();
	saveBookmarks(m_ModelBookmarks);
      }
    });
    m_PanelButtons.add(m_ButtonMoveDown);
    
    m_ButtonCopy = new JButton(m_IconLoader.getCopy());
    m_ButtonCopy.setToolTipText("Copies the current directory to the clipboard");
    m_ButtonCopy.setContentAreaFilled(false);
    m_ButtonCopy.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    m_ButtonCopy.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        copyCurrentDirToClipboard();
      }
    });
    m_PanelButtons.add(m_ButtonCopy);
    
    m_ButtonPaste = new JButton(m_IconLoader.getPaste());
    m_ButtonPaste.setToolTipText("Pastes the current directory from the clipboard");
    m_ButtonPaste.setContentAreaFilled(false);
    m_ButtonPaste.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    m_ButtonPaste.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        pasteCurrentDirFromClipboard();
      }
    });
    m_PanelButtons.add(m_ButtonPaste);
  }
  
  /**
   * Finishes up the initialization.
   */
  @Override
  protected void finishInit() {
    super.finishInit();
    
    updateButtons();
  }

  /**
   * Sets the owner.
   * 
   * @param value	the new owner
   */
  public void setOwner(JFileChooser value) {
    if (m_Owner != null)
      m_Owner.removePropertyChangeListener(m_DirChangeListener);
    m_Owner = value;
    m_Owner.addPropertyChangeListener(m_DirChangeListener);
  }
  
  /**
   * Returns the owner.
   * 
   * @return		the owner, null if none set
   */
  public JFileChooser getOwner() {
    return m_Owner;
  }
  
  /**
   * Creates a popup menu for the {@link BaseList}.
   * 
   * @param e		the event that triggered the menu
   * @return		the generated menu, null if not available
   */
  protected JPopupMenu getPopupMenu(MouseEvent e) {
    JPopupMenu	result;
    JMenuItem	menuitem;
    final int[]	indices;
    
    if (m_Owner == null)
      return null;
    
    result = new JPopupMenu();
    indices = m_ListBookmarks.getSelectedIndices();
    
    menuitem = new JMenuItem("Remove", m_IconLoader.getRemove());
    menuitem.setEnabled(indices.length > 0);
    menuitem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
	removeBookmark(indices);
      }
    });
    result.add(menuitem);
    
    menuitem = new JMenuItem("Rename", m_IconLoader.getRename());
    menuitem.setEnabled(indices.length == 1);
    menuitem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
	renameBookmark(indices[0]);
      }
    });
    result.add(menuitem);

    return result;
  }
  
  /**
   * Ensures that the bookmark has a unique name in the model.
   * 
   * @param bookmark	the initial bookmark
   * @param model	the model with bookmarks
   * @return		the (potentially) updated bookmark
   */
  protected Bookmark createUniqueBookmark(Bookmark bookmark, DefaultListModel model) {
    Bookmark	result;
    int		count;
    
    result = bookmark;

    count = 1;
    while (model.contains(result)) {
      count++;
      result = new Bookmark(bookmark.getName() + count, bookmark.getDirectory());
    }
    
    return result;
  }
  
  /**
   * Adds a bookmark, if possible.
   */
  protected void addBookmark() {
    File[]	files;
    boolean	added;
    Bookmark	bookmark;
    
    if (m_Owner == null) {
      System.err.println("No owner set, cannot add boookmark!");
      return;
    }
    
    // check whether user selected one or more directories
    files = m_Owner.getSelectedFiles();
    added = false;
    for (File file: files) {
      if (!file.isDirectory())
	continue;
      added    = true;
      bookmark = new Bookmark(file);
      m_ModelBookmarks.addElement(createUniqueBookmark(bookmark, m_ModelBookmarks));
    }

    // nothing added? add current directory
    if (!added) {
      bookmark = new Bookmark(m_Owner.getCurrentDirectory());
      m_ModelBookmarks.addElement(createUniqueBookmark(bookmark, m_ModelBookmarks));
    }
    
    saveBookmarks(m_ModelBookmarks);
  }
  
  /**
   * Removes one or more bookmarks.
   * 
   * @param indices	the indices to remove
   */
  protected void removeBookmark(int[] indices) {
    int		i;
    
    Arrays.sort(indices);
    for (i = indices.length - 1; i >= 0; i--)
      m_ModelBookmarks.remove(indices[i]);
    
    saveBookmarks(m_ModelBookmarks);
  }
  
  /**
   * Renames the bookmark.
   * 
   * @param index	the index of the bookmark to rename
   */
  protected void renameBookmark(int index) {
    Bookmark	bookmark;
    String	name;
    
    bookmark = (Bookmark) m_ModelBookmarks.get(index);
    name     = JOptionPane.showInputDialog(
	GUIHelper.getParentComponent(this), 
	"Please enter a new name:",
	bookmark.getName());
    if ((name == null) || (name.equals(bookmark.getName())))
      return;
    
    m_ModelBookmarks.set(index, new Bookmark(name, bookmark.getDirectory()));
    
    saveBookmarks(m_ModelBookmarks);
  }

  /**
   * Loads all the bookmarks that are currently stored in the properties.
   * 
   * @return		the current bookmarks
   */
  protected DefaultListModel loadBookmarks() {
    DefaultListModel		result;
    List<Bookmark>	list;
    
    result = new DefaultListModel();
    list   = getBookmarksManger().load();
    for (Bookmark item: list)
      result.addElement(item);
    
    return result;
  }
  
  /**
   * Saves all the bookmarks in the properties.
   * 
   * @param model	the current bookmarks
   * @return            true if successfully saved
   */
  protected boolean saveBookmarks(DefaultListModel model) {
    int				i;
    Bookmark		bookmark;
    List<Bookmark>	list;
    
    list = new ArrayList<>();
    for (i = 0; i < model.getSize(); i++) {
      bookmark = (Bookmark) model.get(i);
      list.add(bookmark);
    }
    
    return getBookmarksManger().save(list);
  }

  /**
   * Copies the current directory to the clipboard.
   */
  protected void copyCurrentDirToClipboard() {
    ClipboardHelper.copyToClipboard(m_Owner.getCurrentDirectory().getAbsolutePath());
  }

  /**
   * Checks whether the string represents a valid directory.
   *
   * @param dirStr	the string to test
   * @return		true if valid and existing directory
   */
  protected boolean isValidPath(String dirStr) {
    File	dir;

    if (dirStr == null)
      return false;

    dir = new File(dirStr);
    return (dir.exists() && dir.isDirectory());
  }

  /**
   * Pastes the current directory from the clipboard.
   */
  protected void pasteCurrentDirFromClipboard() {
    String	dirStr;

    dirStr = ClipboardHelper.pasteStringFromClipboard();
    if (isValidPath(dirStr))
      m_Owner.setCurrentDirectory(new File(dirStr));
  }

  /**
   * Returns the preferred size.
   * 
   * @return		the preferred size
   */
  @Override
  public Dimension getPreferredSize() {
    Dimension	result;
    
    result = (Dimension) super.getPreferredSize().clone();
    if (result.getWidth() > m_PanelButtons.getPreferredSize().width)
      result.width = m_PanelButtons.getPreferredSize().width;
    
    return result;
  }

  /**
   * Updates the enabled state of the buttons.
   */
  protected void updateButtons() {
    boolean	hasBookmarks;
    
    hasBookmarks = (m_ModelBookmarks.getSize() > 0);
    
    m_ButtonAdd.setEnabled(true);
    m_ButtonMoveUp.setEnabled(hasBookmarks && m_ListBookmarks.canMoveUp());
    m_ButtonMoveDown.setEnabled(hasBookmarks && m_ListBookmarks.canMoveDown());
    m_ButtonRemove.setEnabled(hasBookmarks && m_ListBookmarks.getSelectedIndices().length > 0);
    m_ButtonPaste.setEnabled(ClipboardHelper.canPasteStringFromClipboard() && isValidPath(ClipboardHelper.pasteStringFromClipboard()));
  }
  
  /**
   * Reloads the bookmarks.
   */
  public void reload() {
    m_ModelBookmarks = loadBookmarks();
    m_ListBookmarks.setModel(m_ModelBookmarks);
  }
  
  /**
   * Creates a new instance of the factory.
   * 
   * @return		the factory
   */
  protected abstract AbstractFactory newFactory();
  
  /**
   * Returns the bookmarks manager to use.
   * 
   * @return		the manager
   */
  public synchronized AbstractBookmarksManager getBookmarksManger() {
    return m_Factory.getBookmarksManager();
  }
}
