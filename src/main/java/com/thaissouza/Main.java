package com.thaissouza;

import com.thaissouza.persistence.migration.MigrationStrategy;

import java.sql.SQLException;

import static com.thaissouza.persistence.config.ConnectionConfig.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException {
        try(var connection = getConnection()){
            new MigrationStrategy(connection).executeMigration();
        }
    }
}