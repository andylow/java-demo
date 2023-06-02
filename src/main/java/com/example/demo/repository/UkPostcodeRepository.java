package com.example.demo.repository;

import com.example.demo.model.entity.UkPostcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * @author [yun]
 */
public interface UkPostcodeRepository extends JpaRepository<UkPostcode, Long> {

    Optional<UkPostcode> findByPostcode(final String postcode);

    Optional<UkPostcode> findByIdAndDeleted(final Long id, final Boolean deleted);

    @Query("SELECT max(id) from UkPostcode u")
    long getMaxId();
}
