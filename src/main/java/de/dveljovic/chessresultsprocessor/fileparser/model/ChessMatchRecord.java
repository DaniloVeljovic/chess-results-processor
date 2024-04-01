package de.dveljovic.chessresultsprocessor.fileparser.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChessMatchRecord {

    @CsvBindByPosition(position = 0)
    private int blackPlayerId;
    @CsvBindByPosition(position = 1)
    private int whitePlayerId;
    @CsvBindByPosition(position = 2)
    private int winnerId;
    @CsvDate("yyyy-MM-dd")
    @CsvBindByPosition(position = 3)
    private LocalDate matchDate;
}
