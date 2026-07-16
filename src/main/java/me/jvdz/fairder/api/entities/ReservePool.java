package me.jvdz.fairder.api.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A group of workers who can be considered for reserve-priority matching.
 *
 * <p>Besides its members, a pool can define a geographic boundary and centroid.
 * This allows Fairder to assign and present pools based on a worker's location
 * while keeping manually managed pools valid when no geographic data exists.</p>
 */
@Entity
@Table(name = "reserve_pools")
public class ReservePool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    //TODO membership als eigen model modelleren en Jdoc schrijven
    @ManyToMany
    @JoinTable(
            name = "pool_members",
            joinColumns = @JoinColumn(name = "pool_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    protected ReservePool() {
        // JPA
    }

    public ReservePool(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getMembers() {
        return members;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    //TODO Poolmembership implementeren
    public void addMember(User user) {
        members.add(user);
    }

    //TODO Deze logica bouwen

    public void removeMember(User user) {
        members.removeIf(member -> Objects.equals(member.getId(), user.getId()));
    }

    /**
     * Checks membership by database identifier instead of object reference.
     *
     * <p>The same database row can be represented by separate Java instances
     * when entities originate from different persistence contexts.</p>
     *
     * @param userId the worker identifier to look up
     * @return {@code true} when the worker belongs to this pool
     */
    public boolean hasMember(Long userId) {
        return members.stream().anyMatch(member -> Objects.equals(member.getId(), userId));

    }

}

