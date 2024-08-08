package com.moodscapes.backend.moodscapes.backend.controller;

import com.moodscapes.backend.moodscapes.backend.dto.request.QuoteRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.GuestResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.HttpResponse;
import com.moodscapes.backend.moodscapes.backend.dto.response.QuoteResponseDTO;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IQuoteService;
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
import static com.moodscapes.backend.moodscapes.backend.constant.Constants.NO_RECORD;
import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.getResponse;
import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.handleErrorRequest;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequestMapping("/quote")
@AllArgsConstructor
@CrossOrigin
public class QuoteController {

    private final IQuoteService quoteService;


    @GetMapping
    public ResponseEntity<HttpResponse> getAllQuotes(HttpServletRequest request, HttpServletResponse response) {
        try {
            var quotes = quoteService.getAllQuotes();
            if (!quotes.isEmpty()){
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                QUOTES, quotes
                        ), FETCH_SUCCESS, OK));
            }
            return ResponseEntity.ok()
                    .body(getResponse(request,Map.of(QUOTES, FETCHING_ERROR),NO_RECORD,OK));
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<HttpResponse> getQuotesByUserId(@PathVariable String userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<QuoteResponseDTO> quotesByUserId = quoteService.getAllQuotesByUserId(userId);
            if (quotesByUserId.isEmpty())
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                QUOTES, FETCHING_ERROR
                        ), USER_FETCHING_ERROR, OK));
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            QUOTES, quotesByUserId
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HttpResponse> createQuote(@RequestBody QuoteRequestDTO quoteRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            var quoteResponse = quoteService.createQuote(quoteRequest);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "create_"+QUOTES, quoteResponse
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
    public ResponseEntity<HttpResponse> updateQuote(@PathVariable String id, @RequestBody QuoteRequestDTO quoteRequest, HttpServletRequest request, HttpServletResponse response) {

        try {
            log.info("selecting id to update: "+id);
            var update = quoteService.updateQuote(id, quoteRequest);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "update_"+QUOTES, update
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

}
