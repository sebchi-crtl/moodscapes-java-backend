package com.moodscapes.backend.moodscapes.backend.service.interfaces;

import com.moodscapes.backend.moodscapes.backend.dto.request.EventRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.request.QuoteRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.QuoteResponseDTO;

import java.util.List;

public interface IQuoteService {

    List<QuoteResponseDTO> getAllQuotes();

    List<QuoteResponseDTO> getAllQuotesByUserId(String userId);

    //    QuoteResponseDTO getEventById(Long id);
    QuoteResponseDTO createQuote(QuoteRequestDTO book);

    QuoteResponseDTO updateQuote(String quoteId, QuoteRequestDTO quoteRequest);
//    void deleteEvent(Long id);
}
