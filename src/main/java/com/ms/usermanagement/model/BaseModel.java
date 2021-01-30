package com.ms.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "deletedAt"},
        allowGetters = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseModel implements Serializable {

    @Id
    @Column(columnDefinition = "uuid NOT NULL")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "System generated UUID", example = "dd2b8d50-d954-4c9a-b946-0a452a200eba", readOnly = true)
    private UUID id;

    @ApiModelProperty(notes = "User name can be any string")
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    protected boolean deleted;

    @ApiModelProperty(notes = "System generated timestamp on entity delete")
    @ReadOnlyProperty
    protected Date deletedAt;

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = null;
    }

    public void delete() {
        this.deletedAt = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
        this.deleted = true;
    }

    @ApiModelProperty(notes = "System generated timestamp on entity create")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @CreatedDate
    protected Date createdAt;

    @ApiModelProperty(notes = "System generated timestamp on entity update")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @LastModifiedDate
    protected Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
    }

}



