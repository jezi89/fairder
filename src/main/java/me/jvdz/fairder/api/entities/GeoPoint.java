package me.jvdz.fairder.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * Reusable latitude/longitude value object embedded in {@link User},
 * {@link ReservePool}, and {@link Shift}.
 *
 * <p>Keeping the coordinates in one embeddable type avoids duplicating the
 * same structure and precision across those entities. The column names are
 * supplied by each owning entity through {@code @AttributeOverride}.</p>
 *
 * <p>A record is appropriate because coordinates are immutable and do not
 * have an identity of their own. Hibernate maps the record through its
 * canonical constructor.</p>
 */
@Embeddable
public record GeoPoint(
    @Column(precision = 9, scale = 6) BigDecimal latitude,
    @Column(precision = 9, scale = 6) BigDecimal longitude
) {
    /**
     * Creates a coordinate value while preserving the existing nullable
     * embedded-object behaviour.
     *
     * <p>When both coordinates are absent, there is no location and this
     * method returns {@code null}. When either coordinate is present, a
     * {@code GeoPoint} is created. This matters for delegating setters that
     * update latitude and longitude separately.</p>
     *
     * @param latitude the latitude, possibly {@code null}
     * @param longitude the longitude, possibly {@code null}
     * @return {@code null} when both values are {@code null}; otherwise a
     *         {@code GeoPoint} containing the supplied values
     */
    public static GeoPoint of(BigDecimal latitude, BigDecimal longitude) {
        if (latitude == null && longitude == null) {
            return null;
        }
        return new GeoPoint(latitude, longitude);
    }
}
