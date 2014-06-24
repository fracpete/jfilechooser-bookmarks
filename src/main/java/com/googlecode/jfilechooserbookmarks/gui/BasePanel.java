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
 * BasePanel.java
 * Copyright (C) 2008-2014 University of Waikato, Hamilton, New Zealand
 */

package com.googlecode.jfilechooserbookmarks.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import com.googlecode.jfilechooserbookmarks.core.Utils;

/**
 * A JPanel extended by a few useful methods.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 8552 $
 */
public class BasePanel
  extends JPanel {

  /** for serialization. */
  private static final long serialVersionUID = -6780889707840400801L;

  /**
   * Initializes the panel.
   */
  public BasePanel() {
    this(new BorderLayout());
  }

  /**
   * Initializes the panel with the given layout.
   *
   * @param manager	the layout manager to use
   */
  public BasePanel(LayoutManager manager) {
    super(manager);
    initialize();
    initGUI();
    finishInit();
  }

  /**
   * For initializing members.
   */
  protected void initialize() {
  }

  /**
   * For initializing the GUI.
   */
  protected void initGUI() {
  }

  /**
   * finishes the initialization.
   */
  protected void finishInit() {
  }

  /**
   * Tries to determine the frame this panel is part of.
   *
   * @return		the parent frame if one exists or null if not
   */
  public Frame getParentFrame() {
    return GUIHelper.getParentFrame(this);
  }

  /**
   * Tries to determine the dialog this panel is part of.
   *
   * @return		the parent dialog if one exists or null if not
   */
  public Dialog getParentDialog() {
    return GUIHelper.getParentDialog(this);
  }

  /**
   * Tries to determine the internal frame this panel is part of.
   *
   * @return		the parent internal frame if one exists or null if not
   */
  public JInternalFrame getParentInternalFrame() {
    return GUIHelper.getParentInternalFrame(this);
  }

  /**
   * Closes the parent dialog/frame.
   */
  public void closeParent() {
    GUIHelper.closeParent(this);
  }

  /**
   * Hook method just before the panel is made visible.
   */
  protected void beforeShow() {
  }

  /**
   * Hook method just after the panel was made visible.
   */
  protected void afterShow() {
  }

  /**
   * Hook method just before the panel is hidden.
   */
  protected void beforeHide() {
  }

  /**
   * Hook method just after the panel was hidden.
   */
  protected void afterHide() {
  }

  /**
   * Displays/hides the panel.
   *
   * @param value	if true then panel is displayed otherwise hidden
   */
  @Override
  public void setVisible(boolean value) {
    if (value)
      beforeShow();
    else
      beforeHide();

    super.setVisible(value);

    if (value)
      afterShow();
    else
      afterHide();
  }

  /**
   * Returns the current title of the parent.
   *
   * @return		the parent's title, null if not available
   */
  public String getParentTitle() {
    if (getParentDialog() != null)
      return getParentDialog().getTitle();
    else if (getParentFrame() != null)
      return getParentFrame().getTitle();
    else
      return null;
  }

  /**
   * Sets the new title for the parent.
   *
   * @param value	the title to use
   */
  public void setParentTitle(String value) {
    if (getParentDialog() != null)
      getParentDialog().setTitle(value);
    else if (getParentFrame() != null)
      getParentFrame().setTitle(value);
  }

  /**
   * Outputs the error message in the console window and the console.
   *
   * @param msg		the message to output
   */
  public void printErrorMessage(String msg) {
    System.err.println(msg);
  }

  /**
   * Outputs the exception in the console window and the console.
   *
   * @param msg		the message to output with the exception
   * @param e		the exception to output
   */
  public void printException(String msg, Exception e) {
    msg = getClass().getName() + ": " + msg + "\n" + Utils.throwableToString(e);
    System.err.println(msg);
  }

  /**
   * Displays the BasePanel descendant, it's classname is the first parameter,
   * in its own frame.
   *
   * @param args	the first parameter is the classname of the BasePanel
   * 			descendant to display
   * @throws Exception	if something goes wrong
   */
  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.out.println(
	  "\nUsage: " + BasePanel.class.getName()
	  + " <classname of BasePanel descendant>" + "\n");
      System.exit(1);
    }

    BasePanel panel = (BasePanel) Class.forName(args[0]).newInstance();
    JFrame jf = new JFrame(panel.getClass().getName());
    jf.getContentPane().setLayout(new BorderLayout());
    jf.getContentPane().add(panel, BorderLayout.CENTER);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.pack();
    jf.setSize(800, 600);
    jf.setLocationRelativeTo(null);
    jf.setVisible(true);
  }
}
