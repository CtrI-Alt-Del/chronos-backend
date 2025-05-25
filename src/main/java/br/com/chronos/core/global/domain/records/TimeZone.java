package br.com.chronos.core.global.domain.records;

import br.com.chronos.core.global.domain.exceptions.ValidationException;
import java.time.ZoneId;

public record TimeZone(TimeZoneType value) {
    public enum TimeZoneType {
    AMERICA_SAO_PAULO("America/Sao_Paulo", "Brasil (Horário de Brasília)"),
    AMERICA_NEW_YORK("America/New_York", "EUA (Leste)"),
    AMERICA_LOS_ANGELES("America/Los_Angeles", "EUA (Oeste/Pacífico)"),
    AMERICA_CHICAGO("America/Chicago", "EUA (Central)"),
    AMERICA_TORONTO("America/Toronto", "Canadá (Ontário/Quebec)"),
    AMERICA_MEXICO_CITY("America/Mexico_City", "México"),
    AMERICA_BUENOS_AIRES("America/Buenos_Aires", "Argentina"),
    AMERICA_ST_JOHNS("America/St_Johns", "Canadá (Terra Nova)"),
    EUROPE_LONDON("Europe/London", "Reino Unido"),
    EUROPE_PARIS("Europe/Paris", "França/Europa Central"),
    EUROPE_BERLIN("Europe/Berlin", "Alemanha"),
    EUROPE_MADRID("Europe/Madrid", "Espanha"),
    EUROPE_MOSCOW("Europe/Moscow", "Rússia"),
    EUROPE_ISTANBUL("Europe/Istanbul", "Turquia"),
    ASIA_TOKYO("Asia/Tokyo", "Japão"),
    ASIA_SHANGHAI("Asia/Shanghai", "China"),
    ASIA_DUBAI("Asia/Dubai", "Emirados Árabes"),
    ASIA_KOLKATA("Asia/Kolkata", "Índia"),
    ASIA_SINGAPORE("Asia/Singapore", "Singapura"),
    ASIA_HONG_KONG("Asia/Hong_Kong", "Hong Kong"),
    ASIA_BANGKOK("Asia/Bangkok", "Tailândia"),
    ASIA_SEOUL("Asia/Seoul", "Coreia do Sul"),
    ASIA_TEHRAN("Asia/Tehran", "Irã"),
    ASIA_KABUL("Asia/Kabul", "Afeganistão"),
    AFRICA_JOHANNESBURG("Africa/Johannesburg", "África do Sul"),
    AFRICA_CAIRO("Africa/Cairo", "Egito"),
    AUSTRALIA_SYDNEY("Australia/Sydney", "Austrália (Leste)"),
    PACIFIC_AUCKLAND("Pacific/Auckland", "Nova Zelândia"),
    PACIFIC_CHATHAM("Pacific/Chatham", "Ilhas Chatham");

        private final String zoneId;
        private final String description;

        TimeZoneType(String zoneId, String description) {
            this.zoneId = zoneId;
            this.description = description;
        }

        public String getZoneId() {
            return zoneId;
        }

        public String getDescription() {
            return description;
        }
    }

    public static TimeZone create(String timezone) {
        if (timezone == null) {
            return new TimeZone(TimeZoneType.AMERICA_SAO_PAULO);
        }
        
        var text = Text.create(timezone.toUpperCase(), "fuso horário");
        try {
            return new TimeZone(TimeZoneType.valueOf(text.value()));
        } catch (Exception e) {
            throw new ValidationException(text.key(), "deve ser um fuso horário válido");
        }
    }

    public String toString() {
        return value.getZoneId();
    }

    public ZoneId toZoneId() {
        return ZoneId.of(value.getZoneId());
    }

    public String getDescription() {
        return value.getDescription();
    }
}