package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.domain.RequestContext;
import com.moodscapes.backend.moodscapes.backend.dto.request.GuestRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.GuestResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Guest;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.exception.RequestValidationException;
import com.moodscapes.backend.moodscapes.backend.mapper.GuestMapper;
import com.moodscapes.backend.moodscapes.backend.repository.GuestRepo;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEventService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IGuestService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.moodscapes.backend.moodscapes.backend.constant.Constants.*;

@Service
@Slf4j
@AllArgsConstructor
public class GuestsService implements IGuestService {
    private final GuestRepo guestRepo;
    private final UserService userService;
    private final IEventService eventService;
    private final GuestMapper guestMapper;

    @Override
    public List<GuestResponseDTO> getAllGuests() {
        log.info("Fetching all guests");
        return guestRepo
                .findAll()
                .stream()
                .map(guestMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<GuestResponseDTO> getAllGuestsByUserId(String userId) {
        log.info("Fetching all guests by user's id");
        try {
            var guestResponseDTOS = guestRepo
                    .findByUserId(userId)
                    .stream()
                    .toList();
            return guestResponseDTOS;
        }
        catch (Exception ex) {
            throw new ApiException(UNABLE_FETCH);
        }
    }

    @Override
    public GuestResponseDTO getGuestById(String id) {
        var guest = guestRepo
                .findById(id)
                .orElseThrow(() -> new RequestValidationException(REQUEST_VALIDATION_ERROR + id));
        return guestMapper.apply(guest);
    }

    @Override
    public GuestResponseDTO addGuest(GuestRequestDTO guestRequestDTO) {
        try{
            log.info("creating guest: " + guestRequestDTO);
            var userId = userService.getUserById(guestRequestDTO.userId());
            var eventId = eventService.checkIfEventIdExists(guestRequestDTO.eventId());
            if (userId == true && eventId == true) {
            RequestContext.setUserId(guestRequestDTO.userId());
            Guest guest = Guest
                    .builder()
                    .userId(guestRequestDTO.userId())
                    .eventId(guestRequestDTO.eventId())
                    .firstName(guestRequestDTO.firstName())
                    .lastName(guestRequestDTO.lastName())
                    .grouping(guestRequestDTO.grouping())
                    .rsvp(guestRequestDTO.rsvp())
                    .mealPreference(guestRequestDTO.mealPreference())
                    .notes(guestRequestDTO.notes())
                    .build();
            log.info("Saving guest: {}", guest);
            Guest savedGuest = guestRepo.save(guest);

            log.info("Guest saved successfully: {}", savedGuest);
            return guestMapper.apply(savedGuest);
            }
            else throw new ApiException(USER_FETCHING_ERROR);
        }
        catch (DataIntegrityViolationException ex) {
            log.error("Error adding guest due to data integrity violation: {}", ex.getMessage(), ex);
            throw new ApiException("Failed to add guest due to data integrity violation");
        }
        catch (Exception ex) {
            log.error("Error adding guest: {}", ex.getMessage(), ex);
            throw new ApiException(ex.getMessage());
        }
        finally {
            RequestContext.start();
        }
    }

    @Override
    public GuestResponseDTO updateGuest(String id, GuestRequestDTO request) {
        try {
            log.info("updating guest: " + request);
            var guest = guestRepo.findById(id)
                    .orElseThrow(() -> new RequestValidationException(REQUEST_VALIDATION_ERROR + id));
            var userId = userService.getUserById(guest.getUserId());
            RequestContext.setUserId(request.userId());
            if (userId) {
                var updatedGuest = Guest
                        .builder()
                        .userId(guest.getUserId())
                        .eventId(guest.getEventId())
                        .firstName(request.firstName() != null ? request.firstName() : guest.getFirstName())
                        .lastName(request.lastName() != null ? request.lastName() : guest.getLastName())
                        .grouping(request.grouping() != null ? request.grouping() : guest.getGrouping())
                        .rsvp(Objects.equals(request.rsvp(), null) ? guest.isRsvp() : request.rsvp())
                        .mealPreference(request.mealPreference() != null ? request.mealPreference() : guest.getMealPreference())
                        .notes(request.notes() != null ? request.notes() : guest.getNotes())
                        .build();
                var savedGuest = guestRepo.save(updatedGuest);
                return guestMapper.apply(savedGuest);
            }
            else throw new ApiException(USER_FETCHING_ERROR);
        }
        catch (Exception ex){
            throw new ApiException(UPDATE_FETCHING_ERROR + "Guests");
        }
        finally {
            RequestContext.start();
        }
    }

    @Override
    public String deleteGuest(String id) {
        log.info("deleting guest");
        var guest = guestRepo.findById(id).orElse(null);
        if (guest == null) throw new RequestValidationException(REQUEST_VALIDATION_ERROR + id);
        guestRepo.deleteById(id);
        return "success";
    }
}
