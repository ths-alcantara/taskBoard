package com.thaissouza.persistence.entity;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@Data
public class BoardEntity {

    private Long id;
    private String name;
    @ToString.Exclude
    private List<BoardColumnEntity> boardColumns = new ArrayList<>();

    public BoardColumnEntity getInitialColumn(){
        return getFilteredColumn(bc -> bc.getKind().equals(BoardColumnKindEnum.INITIAL));
    }

    public BoardColumnEntity getCancelColumn(){
        return getFilteredColumn(bc -> bc.getKind().equals(BoardColumnKindEnum.CANCEL));
    }

    private BoardColumnEntity getFilteredColumn(Predicate<BoardColumnEntity> filter){
        return boardColumns.stream()
                .filter(bc -> bc.getKind().equals(BoardColumnKindEnum.INITIAL))
                .findFirst().orElseThrow();
    }
}
