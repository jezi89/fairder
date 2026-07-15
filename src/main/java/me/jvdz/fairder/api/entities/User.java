package me.jvdz.fairder.api.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Keycloak Subjects are being used as the primary key for users in our system. Keycloak is the identity provider
     * and handles authentication and authorization. The subject is a unique identifier for each user in Keycloak, and
     * it allows us to link our local user records to the corresponding Keycloak user.
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

    /**
     * Contract form for freelancer (ZZP/UITZEND/PAYROLL). Nullable: Not relevant for employer and admin users in V1.
     */

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    /**
     * Chamber of Commerce number and VAT ID are validated live against an external source (Handelsregister/VIES) and
     * only stored as text value — deliberately no document upload, see the GDPR consideration in the scope document.
     */

    private String kvkNumber;
    private String btwId;
    /**
     * V1 contract form harness: with UITZENDPAYROLL we record the agency party as intake data. Matching, bookings,
     * timesheets and disputes deliberately remain blind to these fields.
     */

    private String intermediaryName;
    private String intermediaryKvKNumber;

    @Enumarated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.UNVERIFIED;

    private String verificationComment; //TODO: Add a VerificationComment entity

    /**
     * Public QR Reference for Tips (Could Have). Only filled for flex workers; The tipping endpoint looks up the
     * recipient using this code so guests don't need an account or internal ID.
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

    private String homeAddress;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected User() {}

    public User(String keycloakSubject, String email, string displayName, UserPrimaryRole primaryRole) {
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

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

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

    public String getTipCode() {
        return tipCode;
    }

    public void setTipCode(String tipCode) {
        this.tipCode = tipCode;
    }

    /**
     * The latlon getterssetters remain identical in signature so that services, mappers and tests do not have to
     * change; internally, they delegate to the embedded GeoPoint. Null-safe: a User without a home address has a null
     * embedded object, then the getters simply return null.
     */

    //TODO: Begrijpen hoe dit werkt
    public BigDecimal getHomeLatitude() {
        return homeLocation == null ? null : homeLocation.latitude();
    }

    public BigDecimal setHomeLatitude(BigDecimal homeLatitude) {
        this.homeLocation = GeoPoint.of(homeLatitude, homeLocation == null ? null : homeLocation.longitude());
    }

    public BigDecimal getHomeLongitude() {
        return homeLocation == null ? null : homeLocation.longitude();
    }

    public BigDecimal setHomeLongitude(BigDecimal homeLongitude) {
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
