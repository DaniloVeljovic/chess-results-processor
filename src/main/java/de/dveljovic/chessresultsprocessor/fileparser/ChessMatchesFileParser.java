package de.dveljovic.chessresultsprocessor.fileparser;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import de.dveljovic.chessresultsprocessor.fileparser.model.ChessMatchRecord;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.List;

@Component
public class ChessMatchesFileParser {

    @SneakyThrows
    public List<ChessMatchRecord> parseRecords(final BufferedReader bufferedReader) {
        final ColumnPositionMappingStrategy<ChessMatchRecord> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(ChessMatchRecord.class);

        final CsvToBean<ChessMatchRecord> cb = new CsvToBeanBuilder<ChessMatchRecord>(bufferedReader)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .build();

        return cb.parse();
    }

}
