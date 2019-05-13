package com.epam.test.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProgramEvent {

    @EmbeddedId
    private ProgramEventId programEventId;

    @Enumerated(EnumType.STRING)
    private ProgramEvents programEvent;
    @Column
    @CreationTimestamp
    private LocalDate eventDate;

    public enum ProgramEvents {
        JOIN, SUSPENDED, RESUME, CLOSE
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ProgramEventId implements Serializable {

        private Integer userId;
        private Integer programId;
    }
}
