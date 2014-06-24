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
 * Utils.java
 * Copyright (C) 2008-2014 University of Waikato, Hamilton, New Zealand
 */

package com.googlecode.jfilechooserbookmarks.core;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Class implementing some simple utility methods.
 *
 * @author FracPete (fracpete at waikat dot ac dot nz)
 */
public class Utils {

  /**
   * Returns the stacktrace of the throwable as string.
   *
   * @param t		the throwable to get the stacktrace for
   * @return		the stacktrace
   */
  public static String throwableToString(Throwable t) {
    return throwableToString(t, -1);
  }

  /**
   * Returns the stacktrace of the throwable as string.
   *
   * @param t		the throwable to get the stacktrace for
   * @param maxLines	the maximum number of lines to print, <= 0 for all
   * @return		the stacktrace
   */
  public static String throwableToString(Throwable t, int maxLines) {
    StringWriter	writer;
    StringBuilder	result;
    String[]		lines;
    int			i;

    writer = new StringWriter();
    t.printStackTrace(new PrintWriter(writer));

    if (maxLines > 0) {
      result = new StringBuilder();
      lines  = writer.toString().split("\n");
      for (i = 0; i < maxLines; i++) {
	if (i > 0)
	  result.append("\n");
	result.append(lines[i]);
      }
    }
    else {
      result = new StringBuilder(writer.toString());
    }

    return result.toString();
  }

  /**
   * Returns the current stack trace.
   *
   * @param maxDepth	the maximum depth of the stack trace, <= 0 for full trace
   * @return		the stack trace as string (multiple lines)
   */
  public static String getStackTrace(int maxDepth) {
    StringBuilder	result;
    Throwable		th;
    StackTraceElement[]	trace;
    int			i;

    result = new StringBuilder();
    th     = new Throwable();
    th.fillInStackTrace();
    trace  = th.getStackTrace();
    if (maxDepth <= 0)
      maxDepth = trace.length - 1;
    maxDepth++;  // we're starting at 1 not 0
    maxDepth = Math.min(maxDepth, trace.length);
    for (i = 1; i < maxDepth; i++) {
      if (i > 1)
	result.append("\n");
      result.append(trace[i]);
    }

    return result.toString();
  }
}
