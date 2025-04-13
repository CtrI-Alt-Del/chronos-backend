package br.com.chronos.core.hour_bank.domain.records;

public record HourBankReason(String reason) {
    public enum Reason {
        ABSENCE,
        OVERTIME,
        LATETIME,
        MANUAL
    }
}
