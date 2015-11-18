package de.waschnick.happy.stars;

import de.waschnick.happy.stars.control.TestDataCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class StartUp {

    private static final long FIVE_SECONS_IN_MS = 5000;

    @Autowired
    private TestDataCreator testDataCreator;

    //@Scheduled(fixedDelay = FIVE_SECONS_IN_MS)
    @PostConstruct
    public void init() {
        log.info("Calling INIT-Method");
        testDataCreator.createAndSaveTestDataIfDatabaseEmpty();
    }
}
