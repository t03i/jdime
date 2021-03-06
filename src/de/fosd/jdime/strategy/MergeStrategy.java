/**
 * Copyright (C) 2013-2014 Olaf Lessenich
 * Copyright (C) 2014-2015 University of Passau, Germany
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 *
 * Contributors:
 *     Olaf Lessenich <lessenic@fim.uni-passau.de>
 *     Georg Seibt <seibt@fim.uni-passau.de>
 */
package de.fosd.jdime.strategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.fosd.jdime.artifact.Artifact;
import de.fosd.jdime.artifact.file.FileArtifact;
import de.fosd.jdime.merge.MergeInterface;

/**
 * A <code>MergeStrategy</code> implements an algorithm to merge a certain type of <code>Artifacts</code>.
 * <p>
 * The respective <code>MergeStrategies</code> can be optimized regarding different objectives, e.g., generalization,
 * performance, precision, or also a trade-off of concerns.
 *
 * @param <T>
 *         type of artifact
 * @author Olaf Lessenich
 */
public abstract class MergeStrategy<T extends Artifact<T>> implements MergeInterface<T> {

    private static final Map<String, MergeStrategy<FileArtifact>> strategyMap;

    public static final String LINEBASED = "linebased";
    public static final String UNSTRUCTURED = "unstructured";
    public static final String STRUCTURED = "structured";
    public static final String COMBINED = "combined";
    public static final String AUTOTUNING = "autotuning";
    public static final String NWAY = "nway";
    public static final String VARIANTS = "variants";

    static {
        Map<String, MergeStrategy<FileArtifact>> entries = new HashMap<>();
        LinebasedStrategy lineBased = new LinebasedStrategy();
        StructuredStrategy structured = new StructuredStrategy();
        CombinedStrategy combined = new CombinedStrategy();
        NWayStrategy nway = new NWayStrategy();

        entries.put(LINEBASED, lineBased);
        entries.put(UNSTRUCTURED, lineBased);
        entries.put(STRUCTURED, structured);
        entries.put(COMBINED, combined);
        entries.put(AUTOTUNING, combined);
        entries.put(NWAY, nway);
        entries.put(VARIANTS, nway);

        strategyMap = Collections.unmodifiableMap(entries);
    }

    /**
     * Returns an unmodifiable <code>Set</code> containing the names of available strategies.
     *
     * @return names of available strategies
     */
    public static Set<String> listStrategies() {
        return strategyMap.keySet();
    }

    /**
     * Returns a <code>MergeStrategy</code> for the given <code>name</code>. <code>name</code> (ignoring case and
     * leading/trailing whitespaces) may be one of the strings returned by {@link #listStrategies()}. If no
     * <code>MergeStrategy</code> for the given <code>name</code> is found a <code>StrategyNotFoundException</code> will
     * be thrown.
     *
     * @param name
     *         the name to return a <code>MergeStrategy</code> for; <code>name</code> may not be <code>null</code>
     * @return the <code>MergeStrategy</code>
     * @throws StrategyNotFoundException
     *         if no <code>MergeStrategy</code> for <code>name</code> is found
     * @throws NullPointerException
     *         if <code>name</code> is <code>null</code>
     */
    public static MergeStrategy<FileArtifact> parse(String name) {
        Objects.requireNonNull(name, "name may not be null!");
        name = name.trim().toLowerCase();

        if (!strategyMap.containsKey(name)) {
            throw new StrategyNotFoundException("Strategy not found: " + name);
        }

        return strategyMap.get(name);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
