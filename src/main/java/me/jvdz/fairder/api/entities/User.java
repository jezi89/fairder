package me.jvdz.fairder.api.entities;

import jakarta.persistence.*;
import me.jvdz.fairder.api.dtos.EmploymentType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Local user entity linked to a Keycloak identity.
 *
 * <p>Keycloak handles authentication and authorization. This entity stores
 * additional application data such as the user's role, verification status
 * and contract details.</p>
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * The Keycloak subject uniquely links the local user to the corresponding
     * identity in Keycloak.
     */

    @Column(nullable = false)
    private String keycloakSubject;

    @Column(unique = true)
    private String email;

    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserPrimaryRole primaryRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    /*
     * Only relevant for freelancers. This field remains empty for employers
     * and administrators in V1.
     */

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    /*
     * The Chamber of Commerce number and VAT ID are stored as text.
     * Validation is performed against external sources such as the Dutch
     * Business Register and VIES.
     */

    private String kvkNumber;
    private String btwId;
    /**
     * V1 contract form harness: with UITZENDPAYROLL we record the agency party as intake data. Matching, bookings,
     * timesheets and disputes deliberately remain blind to these fields.
     */

    private String intermediaryName;
    private String intermediaryKvKNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.UNVERIFIED;

    // TODO: Consider introducing a separate VerificationComment entity.
    private String verificationComment;

    /*
     * Unique public code used to identify a freelancer when processing a tip.
     * Guests do not need an account or an internal user ID.
     */

    @Column(unique = true)
    private String tipCode;

    //TODO: Uitzoeken hoe dit werkt en of we google distance matrix kunnen gebruiken. Momenteel wordt de home location opgeslagen als lat/long.
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "home_latitude", precision = 9, scale = 6)),
        @AttributeOverride(name = "longitude", column = @Column(name = "home_longitude", precision = 9, scale = 6)),
    })
    private GeoPoint homeLocation;

    private String homeAddressText;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * JPA requires a protected no-argument constructor to reconstruct entities
     * from database records.
     */
    protected User() {}

    public User(String keycloakSubject, String email, String displayName, UserPrimaryRole primaryRole) {
        this.keycloakSubject = keycloakSubject;
        this.email = email;
        this.displayName = displayName;
        this.primaryRole = primaryRole;
        this.status = UserStatus.ACTIVE;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntermediaryKvkNumber() {
        return intermediaryKvKNumber;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserPrimaryRole getPrimaryRole() {
        return primaryRole;
    }

    public String getDisplayName() {
        return displayName;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    /*
     * The user's home location is stored as geographic coordinates.
     * Distance calculations may be added at a later stage.
     */

    public GeoPoint getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(GeoPoint homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getIntermediaryKvKNumber() {
        return intermediaryKvKNumber;
    }

    public void setIntermediaryKvKNumber(String intermediaryKvKNumber) {
        this.intermediaryKvKNumber = intermediaryKvKNumber;
    }

    public String getIntermediaryName() {
        return intermediaryName;
    }

    public void setIntermediaryName(String intermediaryName) {
        this.intermediaryName = intermediaryName;
    }

    public String getKvkNumber() {
        return kvkNumber;
    }

    public void setKvkNumber(String kvkNumber) {
        this.kvkNumber = kvkNumber;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getTipCode() {
        return tipCode;
    }

    public void setTipCode(String tipCode) {
        this.tipCode = tipCode;
    }

    /**
     * Returns the latitude of the user's home location.
     *
     * @return the latitude, or {@code null} if no home location is set
     */
    public BigDecimal getHomeLatitude() {
        return homeLocation == null ? null : homeLocation.latitude();
    }

    public void setHomeLatitude(BigDecimal homeLatitude) {
        this.homeLocation = GeoPoint.of(homeLatitude, homeLocation == null ? null : homeLocation.longitude());
    }

    public BigDecimal getHomeLongitude() {
        return homeLocation == null ? null : homeLocation.longitude();
    }

    public void setHomeLongitude(BigDecimal homeLongitude) {
        this.homeLocation = GeoPoint.of(homeLocation == null ? null : homeLocation.latitude(), homeLongitude);
    }

    public String getHomeAddressText() {
        return homeAddressText;
    }

    public void setHomeAddressText(String homeAddressText) {
        this.homeAddressText = homeAddressText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeycloakSubject() {
        return keycloakSubject;
    }
}
