package at.fhtw.db;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection getConnection();
}
