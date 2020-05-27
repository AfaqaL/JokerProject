package Databases;

import HelperClasses.TableHistory;

import java.sql.SQLException;
import java.util.List;

public interface HistoryDao {
    /**
     * Creates a new table. Throws
     * sqlException if table can't
     * be created.
     */
    void createTable() throws SQLException;

    /**
     * Drops table. Throws sqlException
     * if table can't be dropped.
     */
    void dropTable() throws SQLException;

    /**
     * @param id - id of the user
     * @return list of tables where the
     * user played
     */
    List<TableHistory> getUserHistory(long id);

    /**
     * Adds new history in the database.
     * @param history - struct to save information
     *                about table
     * @return true if user is added
     * in the database, false - otherwise
     */
    boolean addHistory(TableHistory history);
}
