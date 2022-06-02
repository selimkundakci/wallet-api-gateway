package com.selimkundakcioglu.walletapi.model.entity;

import com.selimkundakcioglu.walletapi.model.enumtype.Status;
import com.selimkundakcioglu.walletapi.model.enumtype.StatusConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @CreationTimestamp
    @Column(name = "create_date_time", updatable = false, nullable = false)
    private Timestamp createDateTime;

    @UpdateTimestamp
    @Column(name = "update_date_time", insertable = false)
    private Timestamp updateDateTime;

    @Convert(converter = StatusConverter.class)
    @Column(name = "status", nullable = false, columnDefinition = "INTEGER CHECK (status IN (0, 1))")
    private Status status;
}
