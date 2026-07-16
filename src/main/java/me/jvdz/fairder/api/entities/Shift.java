package me.jvdz.fairder.api.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "shifts")
public class Shift {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emploer_id", nullable = false)
    private User employer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reserve_pool_id", nullable = false)
    private ReservePool reservePool;

}

//TODO Afmaken Shift Entity...


