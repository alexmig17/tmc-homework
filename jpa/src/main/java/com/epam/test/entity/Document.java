package com.epam.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.epam.test.enums.DocumentType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Document extends AbstractEntity {

    @Column(columnDefinition = "BLOB")
    private byte[] data;
    @Column
    private DocumentType type;
}
