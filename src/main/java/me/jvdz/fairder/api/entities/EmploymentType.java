package me.jvdz.fairder.api.entities;

/**
 * Describes the contractual arrangement under which a worker performs work.
 *
 * <p>Fairder remains contract-type agnostic: the platform does not assume a
 * self-employed relationship. Storing this value supports future
 * employment-type-specific compliance flows without changing the data model.</p>
 */
public enum EmploymentType {
    SELF_EMPLOYED,
    TEMPORARY_AGENCY,
    PAYROLL
}
