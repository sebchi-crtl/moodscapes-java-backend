package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.domain.RequestContext;
import com.moodscapes.backend.moodscapes.backend.dto.request.QuoteRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.QuoteResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Quotes;
import com.moodscapes.backend.moodscapes.backend.entity.QuoteItem;
import com.moodscapes.backend.moodscapes.backend.enumeration.QuoteStatus;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.mapper.QuoteMapper;
import com.moodscapes.backend.moodscapes.backend.repository.QuoteItemRepo;
import com.moodscapes.backend.moodscapes.backend.repository.QuoteRepo;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEventService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IQuoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.moodscapes.backend.moodscapes.backend.constant.Constants.*;

@Service
@Slf4j
public class QuoteService implements IQuoteService {
    @Autowired
    private QuoteRepo quoteRepository;
    @Autowired
    private QuoteItemRepo quoteItemRepository;
    @Autowired
    private QuoteMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private IEventService eventService;

    @Override
    public List<QuoteResponseDTO> getAllQuotes() {
        return quoteRepository.findAll().stream().map(mapper).collect(Collectors.toList());
    }

    @Override
    public List<QuoteResponseDTO> getAllQuotesByUserId(String userId) {
        log.info("Fetching all quotes by user's id");
        try {
            return quoteRepository
                    .findByUserId(userId)
                    .stream()
                    .map(mapper)
                    .collect(Collectors.toList());
        }
        catch (Exception ex) {
            throw new ApiException(UNABLE_FETCH);
        }
    }

    @Override
    public QuoteResponseDTO createQuote(QuoteRequestDTO quoteRequest) {
        try {
            log.info("creating quote: " + quoteRequest);
            var userId = userService.getUserById(quoteRequest.userId());
            var eventId = eventService.checkIfEventIdExists(quoteRequest.eventId());
            if (userId && eventId) {
                RequestContext.setUserId(quoteRequest.userId());
                Quotes quote = Quotes.builder()
                        .userId(quoteRequest.userId())
                        .vendorName(quoteRequest.vendorName())
                        .receiverUserId(quoteRequest.receiverUserId())
                        .eventName(quoteRequest.eventName())
                        .status(QuoteStatus.DRAFT)
                        .eventMessage("This Quote was automatically generated for " + quoteRequest.userId())
                        .build();

                List<QuoteItem> items = quoteRequest.items().stream().map(itemRequest -> QuoteItem.builder()
                        .vendorItemInfo(itemRequest.vendorItemInfo())
                        .quantity(itemRequest.quantity())
                        .unitPrice(itemRequest.unitPrice())
                        .totalCost(itemRequest.quantity() * itemRequest.unitPrice())
                        .quote(quote)
                        .build()
                ).collect(Collectors.toList());

                quote.setItems(items);
                quote.setTotalCost(items.stream().mapToDouble(QuoteItem::getTotalCost).sum());

                var savedQuote = quoteRepository.save(quote);
                quoteItemRepository.saveAll(items); // Ensure items are saved
                return mapper.apply(savedQuote);
            }
            else throw new ApiException(USER_FETCHING_ERROR);
        }catch (DataIntegrityViolationException ex) {
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
    public QuoteResponseDTO updateQuote(String id, QuoteRequestDTO quoteRequest) {
        try{
            log.info("updating quote: " + quoteRequest);
            RequestContext.setUserId(quoteRequest.userId());

            Quotes existingQuote = quoteRepository.findById(id).orElseThrow(() -> new RuntimeException(REQUEST_VALIDATION_ERROR + id));
            existingQuote.setUserId(existingQuote.getUserId());
            existingQuote.setVendorName(quoteRequest.vendorName());
            existingQuote.setReceiverUserId(existingQuote.getReceiverUserId());
            existingQuote.setEventName(existingQuote.getEventName());

            // Update or add new QuoteItems
            List<QuoteItem> updatedItems = quoteRequest.items().stream().map(itemRequest -> {
                QuoteItem quoteItem = findQuoteItemByVendorItemInfo(existingQuote.getItems(), itemRequest.vendorItemInfo());
                if (quoteItem == null) {
                    quoteItem = QuoteItem.builder()
                            .vendorItemInfo(itemRequest.vendorItemInfo())
                            .quantity(itemRequest.quantity())
                            .unitPrice(itemRequest.unitPrice())
                            .totalCost(itemRequest.quantity() * itemRequest.unitPrice())
                            .quote(existingQuote)
                            .build();
                } else {
                    quoteItem.setQuantity(itemRequest.quantity());
                    quoteItem.setUnitPrice(itemRequest.unitPrice());
                    quoteItem.setTotalCost(itemRequest.quantity() * itemRequest.unitPrice());
                }
                return quoteItem;
            }).collect(Collectors.toList());

            // Remove QuoteItems that are no longer in the request
            existingQuote.getItems().removeIf(item ->
                    updatedItems.stream().noneMatch(updatedItem -> updatedItem.getVendorItemInfo().equals(item.getVendorItemInfo()))
            );

            // Update total cost
            existingQuote.setItems(updatedItems);
            existingQuote.setTotalCost(updatedItems.stream().mapToDouble(QuoteItem::getTotalCost).sum());

            Quotes updatedQuote = quoteRepository.save(existingQuote);
            quoteItemRepository.saveAll(updatedItems); // Ensure updated items are saved

//        return mapToQuoteResponse(updatedQuote);
            return  mapper.apply(updatedQuote);
        }
        catch (Exception ex){
            throw new ApiException(UPDATE_FETCHING_ERROR + "Guests");
        }
        finally {
            RequestContext.start();
        }
    }

    private QuoteItem findQuoteItemByVendorItemInfo(List<QuoteItem> items, String vendorItemInfo) {
        return items.stream().filter(item -> item.getVendorItemInfo().equals(vendorItemInfo)).findFirst().orElse(null);
    }

}
