package com.moodscapes.backend.moodscapes.backend.controller;

import com.moodscapes.backend.moodscapes.backend.dto.request.ClientRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.ClientResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.HttpResponse;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.moodscapes.backend.moodscapes.backend.constant.Constants.*;
import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.getResponse;
import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.handleErrorRequest;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequestMapping("/client")
@AllArgsConstructor
@CrossOrigin
public class ClientController {
    private final IClientService clientService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<HttpResponse> getAllClientsByUser(@PathVariable("userId") String userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<ClientResponseDTO> allClientsByUser = clientService.getAllClientsByUserId(userId);
            if (allClientsByUser.isEmpty())
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                CLIENT, FETCHING_ERROR
                        ), USER_FETCHING_ERROR, OK));
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            CLIENT, allClientsByUser
                    ), DATA_USER_FETCHING, OK));
        }
        catch (AccessDeniedException ade){
            log.error("Error writing response: {}", ade.getMessage());
            handleErrorRequest(request, response, ade);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }
    @GetMapping
    public  ResponseEntity<HttpResponse> getAllClients(HttpServletRequest request, HttpServletResponse response) {
        try{
            var allGuest = clientService.getAllClient();
            if (!allGuest.isEmpty()){
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                CLIENT, allGuest
                        ), FETCH_SUCCESS, OK));
            }
            return ResponseEntity.ok()
                    .body(getResponse(request,Map.of(CLIENT, FETCHING_ERROR),NO_RECORD,OK));
        }
        catch (AccessDeniedException ade){
            log.error("Error writing response: {}", ade.getMessage());
            handleErrorRequest(request, response, ade);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getClientById(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            var guest = clientService.getClientById(id);
            if (guest != null)
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                CLIENT, guest
                        ), FETCH_SUCCESS, OK));
        return ResponseEntity.ok()
                    .body(getResponse(request,Map.of(CLIENT, FETCHING_ERROR),NO_RECORD,OK));

        }
        catch (AccessDeniedException ade){
            log.error("Error writing response: {}", ade.getMessage());
            handleErrorRequest(request, response, ade);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HttpResponse> addClient(@RequestBody ClientRequestDTO requestDTO, HttpServletRequest request, HttpServletResponse response) {

        try {
            var addGuest = clientService.addClient(requestDTO);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "create_"+CLIENT, addGuest
                    ), CREATE_SUCCESS, OK));
        }
        catch (AccessDeniedException ade){
            log.error("Error writing response: {}", ade.getMessage());
            handleErrorRequest(request, response, ade);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse> updateGuest(@PathVariable String id, @RequestBody ClientRequestDTO  updatedClient, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("selecting id to update: "+id);
            var update = clientService.updateClient(id, updatedClient);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "update_"+CLIENT, update
                    ), UPDATE_SUCCESS, OK));
        }
        catch (AccessDeniedException ade){
            log.error("Error writing response: {}", ade.getMessage());
            handleErrorRequest(request, response, ade);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> deleteEvent(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("selecting id to delete: "+id);
            var delete = clientService.deleteClient(id);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "delete_"+CLIENT, delete
                    ), DELETE_SUCCESS, OK));
        }
        catch (AccessDeniedException ade){
            log.error("Error writing response: {}", ade.getMessage());
            handleErrorRequest(request, response, ade);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }
    @DeleteMapping("/bulk")
    public ResponseEntity<HttpResponse> deleteEvents(@RequestBody List<String> ids, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("selecting id to delete: "+ids);
            var result = clientService.deleteListOfClients(ids);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "delete_"+CLIENT, result
                    ), DELETE_SUCCESS, OK));
        }
        catch (AccessDeniedException ade){
            log.error("Error writing response: {}", ade.getMessage());
            handleErrorRequest(request, response, ade);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }
}
