package com.example.project_bigbangk.service.priceHistoryUpdate;

import com.example.project_bigbangk.model.PriceHistory;
import com.example.project_bigbangk.repository.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for updating the current price of an Asset in Euro's
 *
 * @author Pieter Jan -Deek
 * Creation date 12/11/2021
 */
@Service
public class PriceHistoryUpdateService {

    private final Logger logger = LoggerFactory.getLogger(PriceHistoryUpdateService.class);
    private ICryptoApiSwitcher cryptoApiNegotiatorStrategy;
    private ICryptoApiNegotiator cryptoNegotiatorService;
    private RootRepository rootRepository;
    private List<IPriceHistoryListener> priceHistoryListeners;


    public PriceHistoryUpdateService(ICryptoApiSwitcher cryptoApiNegotiatorStrategy, RootRepository rootRepository) {
        super();
        logger.info("New PriceHistoryUpdateService");
        this.cryptoApiNegotiatorStrategy = cryptoApiNegotiatorStrategy;
        this.rootRepository = rootRepository;
        this.priceHistoryListeners = new ArrayList<>();
    }

    public void addPriceHistoryListener(IPriceHistoryListener IPriceHistoryListener) {
        priceHistoryListeners.add(IPriceHistoryListener);
    }

    /**
     * calls the available CryptoAPiNegotiator (determined by the ICryptoApiSwitcher) for current prices
     * and sends it to the rootrepository
     */
    public void updatePriceHistory(String currency) {
        List<PriceHistory> priceHistories = null;
        cryptoNegotiatorService = cryptoApiNegotiatorStrategy.getAvailableNegotiator();
        if (cryptoNegotiatorService != null) {
            priceHistories = cryptoNegotiatorService.getPriceHistory(currency);
        }
        if (priceHistories != null) {
            rootRepository.savePriceHistories(priceHistories);
            logger.info(String.format("Price history updated on %s", LocalDateTime.now()));
            onPriceHistoryUpdated();
        } else {
            logger.error(String.format("Price history update encountered an error on %s", LocalDateTime.now()));
        }
    }

    public void onPriceHistoryUpdated() {
        for (IPriceHistoryListener priceHistoryListener : priceHistoryListeners) {
            priceHistoryListener.onPriceHistoryUpdated();
        }
    }
}
