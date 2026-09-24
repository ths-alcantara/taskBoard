package com.thaissouza.ui;

import com.thaissouza.persistence.entity.BoardColumnEntity;
import com.thaissouza.persistence.entity.BoardColumnKindEnum;
import com.thaissouza.persistence.entity.BoardEntity;
import com.thaissouza.service.BoardQueryService;
import com.thaissouza.service.BoardService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.thaissouza.persistence.config.ConnectionConfig.getConnection;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException{
        System.out.println("Bem vindo ao gerenciador de quadros de tarefas, escolha a opção desejada");
        var option = -1;
        while(true){
            System.out.println("1 - Criar um novo quadro");
            System.out.println("2 - Selecionar um quadro existente");
            System.out.println("3 - Excluir um quadro");
            System.out.println("4 - Sair");
            option = scanner.nextInt();
            switch (option){
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu");
            }
        }
    }

    private void createBoard() throws SQLException {
        var entity = new BoardEntity();
        System.out.println("Informe o nome do seu quadro");
        entity.setName(scanner.next());

        System.out.println("Seu quadro terá mais colunas além das 3 padrões? Se sim informe quantas, se não digite '0'");
        var additionalColumns = scanner.nextInt();

        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do quadro");
        var initialColumnName = scanner.next();
        var initialColumn = createColumn(initialColumnName,BoardColumnKindEnum.INITIAL,0);
        columns.add(initialColumn);

        for (int i = 0; i < additionalColumns; i++) {
            System.out.println("Informe o nome da coluna de tarefa pendente");
            var pendingColumnName = scanner.next();
            var pendingColumn = createColumn(initialColumnName,BoardColumnKindEnum.PENDING,i + 1);
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna de tarefa concluída");
        var finalColumnName = scanner.next();
        var finalColumn = createColumn(initialColumnName,BoardColumnKindEnum.FINAL, additionalColumns + 1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de tarefa cancelada");
        var cancelColumnName = scanner.next();
        var cancelColumn = createColumn(initialColumnName,BoardColumnKindEnum.CANCEL, additionalColumns + 1);
        columns.add(finalColumn);

        entity.setBoardColumns(columns);
        try(var connection = getConnection()){
            var service = new BoardService(connection);
            service.insert(entity);
        }
    }

    private void selectBoard() throws SQLException {
        System.out.println("Informe o id do quadro que deseja selecionar");
        var id = scanner.nextLong();
        try(var connection = getConnection()){
            var queryService = new BoardQueryService(connection);
            var optional = queryService.findById(id);
            optional.ifPresentOrElse(
                    b -> new BoardMenu(optional.get()).execute(),
                    () -> System.out.printf("Não foi encontrado um quadro com o id %s", id));
        }
    }

    private void deleteBoard() throws SQLException {
        System.out.println("Informe o id do quadro que será excluído");
        var id = scanner.nextLong();
        try(var connection = getConnection()){
            var service = new BoardService(connection);
            if(service.delete(id)){
                System.out.printf("O quadro %s foi excluido\n", id);
            } else {
                System.out.printf("Não foi encontrado um quadro com o id %s", id);
            }
        }
    }

    private BoardColumnEntity createColumn (final String name, final BoardColumnKindEnum kind, final int order){
        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);
        return boardColumn;
    }

}
