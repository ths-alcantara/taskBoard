package com.thaissouza.persistence.dao;

import com.mysql.cj.jdbc.StatementImpl;
import com.thaissouza.dto.BoardColumnDTO;
import com.thaissouza.persistence.entity.BoardColumnEntity;
import com.thaissouza.persistence.entity.BoardColumnKindEnum;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.thaissouza.persistence.entity.BoardColumnKindEnum.findByName;

@AllArgsConstructor
public class BoardColumnDAO {

    private Connection connection;

    public BoardColumnEntity insert(BoardColumnEntity entity) throws SQLException{
        var sql = "INSERT INTO BOARDS_COLUMNS(name, `order`, kind, board_id) VALUES (?, ?, ?, ?);";
        try(var statement = connection.prepareStatement(sql)) {
            var i = 1;
            statement.setString(i ++, entity.getName());
            statement.setInt(i++, entity.getOrder());
            statement.setString(i++, entity.getKind().name());
            statement.setLong(i, entity.getBoard().getId());
            statement.executeUpdate();
            if(statement instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
            return entity;
        }
    }

    public List<BoardColumnEntity> findByBoardId(final Long boardId) throws SQLException{
        List<BoardColumnEntity> entities = new ArrayList<>();
        var sql = "SELECT id, name, `order`, kind FROM BOARD_COLUMNS WHERE board_id = ? ORDER BY `order`";
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while(resultSet.next()){
                var entity = new BoardColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setOrder(resultSet.getInt("order"));
                entity.setKind(findByName(resultSet.getString("kind")));
                entities.add(entity);
            }
        }
        return entities;
    }

    public List<BoardColumnDTO> findByBoardIdWithDetails(final Long boardId) throws SQLException{
        List<BoardColumnDTO> dtos = new ArrayList<>();
        var sql = """
                SELECT bc.id, bc.name, bc.`order`, bc.kind,
                COUNT(SELECT c.id FROM CARDS c WHERE c.board_column_id = bc.id) cards_amount
                FROM BOARD_COLUMNS INNER JOIN CARDS WHERE board_id = ? ORDER BY `order`""";
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while(resultSet.next()){
                var dto = new BoardColumnDTO(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("order"),
                        findByName(resultSet.getString("kind")),
                        resultSet.getInt("cards_amount")
                );
                dtos.add(dto);
            }
        }
        return dtos;
    }

    public List<BoardColumnEntity> findById(final Long boardId) throws SQLException{
        List<BoardColumnEntity> entities = new ArrayList<>();
        var sql = "SELECT id, name, `order`, kind FROM BOARD_COLUMNS WHERE board_id = ? ORDER BY `order`";
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while(resultSet.next()){
                var entity = new BoardColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setOrder(resultSet.getInt("order"));
                entity.setKind(findByName(resultSet.getString("kind")));
                entities.add(entity);
            }
        }
        return entities;
    }
}
