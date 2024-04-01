package de.dveljovic.chessresultsprocessor.repository;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChessMatchRecordEntity {

    private String id;

    private int blackPlayerId;

    private int whitePlayerId;

    private int winnerId;

    private LocalDate matchDate;
}
