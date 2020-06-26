package com.speedment.jpastreamer.pipeline.intermediate;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.jpastreamer.pipeline.intermediate.Statement.*;
import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;

public enum IntermediateOperationType {

    FILTER(PRESERVES_ORDER, PRESERVES_TYPE, PRESERVES_STREAM_TYPE),
    DISTINCT(PRESERVES_ORDER, PRESERVES_TYPE, PRESERVES_STREAM_TYPE),
    FLAT_MAP(PRESERVES_ORDER, PRESERVES_STREAM_TYPE),
    FLAT_MAP_TO(PRESERVES_ORDER, MODIFIES_STREAM_TYPE),
    LIMIT(PRESERVES_ORDER, PRESERVES_TYPE, PRESERVES_STREAM_TYPE, MODIFIES_FINITE),
    MAP(PRESERVES_ORDER, PRESERVES_SIZE, PRESERVES_STREAM_TYPE),
    MAP_TO_SAME(PRESERVES_ORDER, PRESERVES_SIZE, PRESERVES_TYPE, PRESERVES_STREAM_TYPE), // E.g. IntStream::map
    MAP_TO(PRESERVES_ORDER, PRESERVES_SIZE, MODIFIES_STREAM_TYPE),
    PEEK(PRESERVES_ORDER, PRESERVES_SIZE, PRESERVES_TYPE, PRESERVES_STREAM_TYPE, MODIFIES_SIDE_EFFECT),
    SKIP(PRESERVES_ORDER, PRESERVES_TYPE, PRESERVES_STREAM_TYPE),
    SORTED(MODIFIES_ORDER, PRESERVES_TYPE, PRESERVES_STREAM_TYPE, MODIFIES_SORTED),
    BOXED(PRESERVES_ORDER, PRESERVES_SIZE, MODIFIES_STREAM_TYPE),
    AS(PRESERVES_ORDER, PRESERVES_SIZE, MODIFIES_STREAM_TYPE),
    TAKE_WHILE(PRESERVES_ORDER, PRESERVES_TYPE, PRESERVES_STREAM_TYPE),
    DROP_WHILE(PRESERVES_ORDER, PRESERVES_TYPE, PRESERVES_STREAM_TYPE);

    private final Set<Statement> orderPreservation;

    IntermediateOperationType(final Statement... statements) {
        if (statements.length == 0) {
            this.orderPreservation = emptySet();
        } else {
            this.orderPreservation = unmodifiableSet(
                    EnumSet.copyOf(
                            Stream.of(statements)
                                    .collect(Collectors.toSet())
                    )
            );
        }
    }

    public Set<Statement> statements() {
        return orderPreservation;
    }
}