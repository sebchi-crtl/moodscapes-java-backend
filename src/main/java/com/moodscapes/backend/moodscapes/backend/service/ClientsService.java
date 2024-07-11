package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.domain.RequestContext;
import com.moodscapes.backend.moodscapes.backend.dto.request.ClientRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.ClientResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Client;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.exception.RequestValidationException;
import com.moodscapes.backend.moodscapes.backend.mapper.ClientMapper;
import com.moodscapes.backend.moodscapes.backend.repository.ClientRepo;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IClientService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.moodscapes.backend.moodscapes.backend.constant.Constants.*;

@Service
@Slf4j
@AllArgsConstructor
public class ClientsService implements IClientService {
    private final ClientRepo repo;
    private final UserService userService;
    private final IEventService eventService;
    private final ClientMapper mapper;

    @Override
    public List<ClientResponseDTO> getAllClient() {
        log.info("Fetching all clients");
        return repo
                .findAll()
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientResponseDTO> getAllClientsByUserId(String userId) {
        log.info("Fetching all clients by user's id");
        try {
            var responseDTOS = repo
                    .findByUserId(userId)
                    .stream()
                    .toList();
            return responseDTOS;
        }
        catch (Exception ex) {
            throw new ApiException(UNABLE_FETCH);
        }
    }

    @Override
    public ClientResponseDTO getClientById(String id) {
        var guest = repo
                .findById(id)
                .orElseThrow(() -> new RequestValidationException(REQUEST_VALIDATION_ERROR + id));
        return mapper.apply(guest);
    }

    @Override
    public ClientResponseDTO addClient(ClientRequestDTO requestDto) {
        try{
            log.info("creating guest: " + requestDto);
            var userId = userService.getUserById(requestDto.userId());
            var eventId = eventService.checkIfEventIdExists(requestDto.eventId());
            if (userId && eventId) {
            RequestContext.setUserId(requestDto.userId());
            var client = Client
                    .builder()
                    .eventId(requestDto.eventId())
                    .userId(requestDto.userId())
                    .firstName(requestDto.firstName())
                    .lastName(requestDto.lastName())
                    .country(requestDto.country())
                    .phoneNumber(requestDto.phoneNumber())
                    .email(requestDto.email())
                    .budget(requestDto.budget())
                    .notes(requestDto.notes())
                    .active(true)
                    .build();
            log.info("Saving client: {}", client);
            var savedClient = repo.save(client);

            log.info("Client saved successfully: {}", savedClient);
            return mapper.apply(savedClient);
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
    public ClientResponseDTO updateClient(String id, ClientRequestDTO request) {
        try {
            log.info("updating guest: " + request);
            var client = repo.findById(id)
                    .orElseThrow(() -> new RequestValidationException(REQUEST_VALIDATION_ERROR + id));
            var userId = userService.getUserById(client.getUserId());
            RequestContext.setUserId(request.userId());
            if (userId) {
                Float requestBudget = request.budget() == 0.0f ? null : request.budget();
                var updatedGuest = Client
                        .builder()
                        .eventId(client.getEventId())
                        .userId(client.getUserId())
                        .firstName(request.firstName() != null ? request.firstName() : client.getFirstName())
                        .lastName(request.lastName() != null ? request.lastName() : client.getLastName())
                        .country(request.country() != null ? request.country() : client.getCountry())
                        .phoneNumber(request.phoneNumber() != null ? request.phoneNumber() : client.getPhoneNumber())
                        .email(request.email() != null ? request.email() : client.getEmail())
                        .budget(requestBudget != null ? requestBudget : client.getBudget())
                        .notes(request.notes() != null ? request.notes() : client.getNotes())
                        .active(Objects.equals(request.active(), null) ? client.isActive() : request.active())
                        .build();
                var savedGuest = repo.save(updatedGuest);
                return mapper.apply(savedGuest);
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
    public String deleteClient(String id) {
        log.info("deleting guest");
        var guest = repo.findById(id).orElse(null);
        if (guest == null) throw new RequestValidationException(REQUEST_VALIDATION_ERROR + id);
        repo.deleteById(id);
        return "success";
    }
    @Override
    public String deleteListOfClients(List<String> ids) {
        log.info("Find all the IDs that are present in the database");
        List<String> presentIds = repo.findAllByIdIn(ids);

        log.info("Determine the IDs that are not present in the database");
        List<String> notPresentIds = ids.stream()
                .filter(id -> !presentIds.contains(id))
                .collect(Collectors.toList());

        if (!notPresentIds.isEmpty()) {
            log.info("If there are IDs that are not present, throw an exception");
            throw new RequestValidationException("REQUEST_VALIDATION_ERROR: " + notPresentIds);
        }

        if (!presentIds.isEmpty()) {
            log.info("Delete the entities corresponding to the present IDs");
            repo.deleteByIdIn(presentIds);
        }

        return "Events successfully deleted";
    }
//    public String deleteEventsByIdsAndReturnNotPresent(List<String> ids) {
//        log.info("deleting clients");
//        var guest = repo.findById(ids).orElse(null);
//        if (guest == null) throw new RequestValidationException(REQUEST_VALIDATION_ERROR + id);
//        repo.deleteById(id);
//        return "success";
//    }
}
