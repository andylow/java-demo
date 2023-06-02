package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

/**
 * @author [yun]
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "uk_postcode")
@SQLDelete(sql = "UPDATE uk_postcode SET deleted = true WHERE id=?")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class UkPostcode {

    @Id
    private Long id;
    private String postcode;
    private double latitude;
    private double longitude;

    @JsonIgnore
    private boolean deleted = false;
}
