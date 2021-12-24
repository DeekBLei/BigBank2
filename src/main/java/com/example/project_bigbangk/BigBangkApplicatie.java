// Created by Deek
// Creation date 12/11/2021

package com.example.project_bigbangk;

import com.example.project_bigbangk.repository.RootRepository;
import com.example.project_bigbangk.service.ClientFactory;
import com.example.project_bigbangk.service.priceHistoryUpdate.*;
import org.h2.util.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class BigBangkApplicatie implements ApplicationListener<ContextRefreshedEvent> {

    public static final String CURRENT_CURRENCY = "EUR";

    private static final int UPDATE_INTERVAL_PRICEUPDATESERVICE = 300000;//5min
    private static final int NUMBER_OF_CLIENTS_TO_SEED = 3000;
    private static final int DELAY_PRICEHISTORYUPDATE = 3000;
    private static final int DELAY_DATABASES_SEEDING = 6000;

    private final PriceHistoryUpdateService priceHistoryUpdateService;
    private final ClientFactory clientFactory;
    private final Logger logger = LoggerFactory.getLogger(BigBangkApplicatie.class);


    public BigBangkApplicatie(PriceHistoryUpdateService priceHistoryUpdateService,
                              ClientFactory clientFactory) {
        super();
        logger.info("New BigBangkApplicatie");
        this.priceHistoryUpdateService = priceHistoryUpdateService;
        this.clientFactory = clientFactory;
    }

    @Override
    public  void onApplicationEvent(ContextRefreshedEvent event) {
        startPriceHistoryUpdateTimer();
        startDateBaseSeeding();
    }

    private void startPriceHistoryUpdateTimer() {
        Timer priceHistoryUpdateCallBack = new Timer(true);
        logger.info("priceHistoryUpdate in progress");
        priceHistoryUpdateCallBack.schedule(new UpdatePriceHisToryTask(), DELAY_PRICEHISTORYUPDATE, UPDATE_INTERVAL_PRICEUPDATESERVICE);
    }

    class UpdatePriceHisToryTask extends TimerTask {
        @Override
        public void run() {
            priceHistoryUpdateService.updatePriceHistory(CURRENT_CURRENCY);
        }
    }
    private void startDateBaseSeeding(){
        Timer seedDatabaseTimer = new Timer(true);
        seedDatabaseTimer.schedule(new SeedDatabase(), DELAY_DATABASES_SEEDING);
    }

    class SeedDatabase extends TimerTask {

        @Override
         public void run() {
            logger.info("Start database seeding? (Y/N): ");
            Scanner scanner = new Scanner(System.in);
            if(scanner.nextLine().equalsIgnoreCase("Y")) {
                clientFactory.seedDataBase(NUMBER_OF_CLIENTS_TO_SEED);
            }
        }
    }
}
