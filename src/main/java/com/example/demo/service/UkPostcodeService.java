package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.PostcodesDistanceInfo;
import com.example.demo.model.entity.UkPostcode;
import com.example.demo.repository.UkPostcodeRepository;
import com.example.demo.utils.CoordinateUtils;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author [yun]
 */
@Service
@AllArgsConstructor
public class UkPostcodeService {

    final private UkPostcodeRepository repository;

    final private EntityManager entityManager;

    public Page<UkPostcode> findAll(final Pageable pageable) {
        final var session = entityManager.unwrap(Session.class);
        final var filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", false);
        final var found = repository.findAll(pageable);
        session.disableFilter("deletedFilter");
        return found;
    }

    public PostcodesDistanceInfo calculateDistance(@NonNull final String postcode1, @NonNull final String postcode2) {

        final var fromPostcode = repository.findByPostcode(postcode1.toUpperCase());
        final var toPostcode = repository.findByPostcode(postcode2.toUpperCase());

        if (fromPostcode.isEmpty() || toPostcode.isEmpty()) {
            throw new BadRequestException("UK postcode not found.");
        }

        final var distance = CoordinateUtils.calculateDistance(
                fromPostcode.get().getLatitude(), fromPostcode.get().getLongitude(),
                toPostcode.get().getLatitude(), toPostcode.get().getLongitude()
        );

        final var info = new PostcodesDistanceInfo(fromPostcode.get(), toPostcode.get());
        info.setDistance(distance);

        return info;
    }

    public UkPostcode update(final UkPostcode ukPostcode) {
        final var updated = findAndThrow(ukPostcode.getId())
                .setLatitude(ukPostcode.getLatitude())
                .setLongitude(ukPostcode.getLongitude());

        repository.saveAndFlush(updated);
        return updated;
    }

    public UkPostcode create(final UkPostcode ukPostcode) {
        final var found = repository.findByPostcode(ukPostcode.getPostcode());

        if (found.isEmpty()) {
            final var id = repository.getMaxId() + 1;
            ukPostcode.setId(id);
            final var saved = repository.saveAndFlush(ukPostcode);
            return saved;
        }

        if (!found.get().isDeleted()) {
            throw new BadRequestException("Postcode already existed.");
        } else {
            final var saved = found.get().setLatitude(ukPostcode.getLatitude())
                    .setLongitude(ukPostcode.getLongitude())
                    .setDeleted(false);
            repository.saveAndFlush(saved);
            return saved;
        }
    }

    public void delete(final Long id) {
        final var found = findAndThrow(id);

        repository.delete(found);
        repository.flush();
    }

    private UkPostcode findAndThrow(final Long id) {
        final var found = repository.findByIdAndDeleted(id, false);

        if (found.isEmpty()) {
            throw new BadRequestException("Invalid ID provided.");
        }

        return found.get();
    }
}
