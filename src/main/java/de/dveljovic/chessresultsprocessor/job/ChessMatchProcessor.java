package de.dveljovic.chessresultsprocessor.job;

import de.dveljovic.chessresultsprocessor.feign.FakerApiClient;
import de.dveljovic.chessresultsprocessor.feign.UuidGeneratorClient;
import de.dveljovic.chessresultsprocessor.feign.dto.GetPersonsRequest;
import de.dveljovic.chessresultsprocessor.feign.dto.GetPersonsResponse;
import de.dveljovic.chessresultsprocessor.fileparser.ChessMatchesFileParser;
import de.dveljovic.chessresultsprocessor.fileparser.model.ChessMatchRecord;
import de.dveljovic.chessresultsprocessor.repository.ChessMatchRecordEntity;
import de.dveljovic.chessresultsprocessor.semaphore.SemaphoreThrottler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChessMatchProcessor {

    private final FakerApiClient fakerApiClient;
    private final Semaphore throttler;
    private final UuidGeneratorClient uuidGeneratorClient;
    private final ChessMatchesFileParser chessMatchesFileParser;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void runJob() {
        log.info("Started the job");

        final GetPersonsResponse persons = fakerApiClient.getPersons(GetPersonsRequest.builder()._quantity(1000).build());
        final ClassPathResource fileResource = new ClassPathResource("chess-matches-2024-04-01.csv");
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(fileResource.getFile()));
        final List<ChessMatchRecord> chessMatchRecords = chessMatchesFileParser.parseRecords(bufferedReader).stream().toList();

        try (final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            final long startFetching = System.currentTimeMillis();
            final List<Callable<ChessMatchRecordEntity>> futures = chessMatchRecords
                    .stream()
                    .map(record -> (Callable<ChessMatchRecordEntity>) () -> {
                        throttler.acquire();
                        final String uuid = uuidGeneratorClient.getUuid();
                        throttler.release();
                        return ChessMatchRecordEntity.builder()
                                .id(uuid)
                                .blackPlayerId(record.getBlackPlayerId())
                                .matchDate(record.getMatchDate())
                                .whitePlayerId(record.getWhitePlayerId())
                                .winnerId(record.getWinnerId())
                                .build();
                    })
                    .toList();

            final List<Future<ChessMatchRecordEntity>> invokedFutures = executorService.invokeAll(futures);
            executorService.close();
            executorService.shutdown();

            final List<ChessMatchRecordEntity> entities = invokedFutures.stream().map(future -> {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }).toList();

            log.info("Finished fetching records in {} millis", System.currentTimeMillis() - startFetching);
        }


        log.info("Finished the job");
    }
}
