package me.jvdz.fairder.api.dtos;

/**
 * Contractvorm-agnostisch na het Temper-arrest: het platform doet geen aanname
 * over de arbeidsrelatie. Dit veld maakt latere compliance-flows per vorm
 * mogelijk zonder het datamodel te breken.
 */

public enum EmploymentType {
    ZZP,
    UITZEND,
    PAYROLL,
}
