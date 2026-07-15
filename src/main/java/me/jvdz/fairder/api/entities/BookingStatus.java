package me.jvdz.fairder.api.entities;

/**
 * Defines the lifecycle state of a booking.
 *
 * <p>Only {@link #CONFIRMED} bookings count towards the global open-booking
 * limit. A booking no longer counts after it has been {@link #COMPLETED} or
 * {@link #CANCELLED}.</p>
 */
public enum BookingStatus {
    CONFIRMED,
    CANCELLED,
    COMPLETED,
}
