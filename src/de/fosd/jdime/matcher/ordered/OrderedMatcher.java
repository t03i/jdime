/*******************************************************************************
 * Copyright (c) 2013 Olaf Lessenich.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Olaf Lessenich - initial API and implementation
 ******************************************************************************/
/**
 * 
 */
package de.fosd.jdime.matcher.ordered;

import de.fosd.jdime.common.Artifact;
import de.fosd.jdime.matcher.Matcher;
import de.fosd.jdime.matcher.Matching;
import de.fosd.jdime.matcher.MatchingInterface;

/**
 * @author Olaf Lessenich
 * 
 * @param <T>
 *            type of artifact
 */
public abstract class OrderedMatcher<T extends Artifact<T>> implements
		MatchingInterface<T> {

	/**
	 * The matcher is used for recursive matching calls. It can determine
	 * whether the order of artifacts is essential.
	 */
	protected Matcher<T> matcher;

	/**
	 * Creates a new instance of OrderedMatcher.
	 * 
	 * @param matcher
	 *            matcher
	 */
	public OrderedMatcher(final Matcher<T> matcher) {
		this.matcher = matcher;
	}

	/**
	 * Compares two nodes.
	 * 
	 * @param left
	 *            left node
	 * @param right
	 *            right node
	 * @return matching
	 */
	public abstract Matching<T> match(final T left, final T right);

}
