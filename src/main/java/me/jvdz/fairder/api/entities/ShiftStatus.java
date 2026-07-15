package me.jvdz.fairder.api.entities;

/**
 * Defines the lifecycle state of a work shift.
 *
 * <p>A shift starts as {@link #OPEN}, becomes {@link #BOOKED} when a worker is
 * matched, and ends as either {@link #COMPLETED} after the work is approved or
 * {@link #CANCELLED} when the shift is withdrawn.</p>
 */
public enum ShiftStatus {
    OPEN,
    BOOKED,
    COMPLETED,
    CANCELLED,
}
