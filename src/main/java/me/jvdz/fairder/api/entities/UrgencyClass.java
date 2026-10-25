package me.jvdz.fairder.api.entities;

/**
 * Derived urgency classification used for presentation and premium-rate logic.
 *
 * <p>This value is not persisted because a shift can move from
 * {@link #REGULAR} to {@link #SHORT_NOTICE} and then {@link #URGENT} as time passes.</p>
 */
// TODO Usage in het Engels overnemen
public enum UrgencyClass {
    URGENT,
    SHORT_NOTICE,
    REGULAR,
}
