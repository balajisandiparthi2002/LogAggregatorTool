package org.LogAggregatorTool.jdbc;

import org.LogAggregatorTool.constants.DatabaseConstants;
import org.LogAggregatorTool.constants.LogAggregatorConstants;
import org.LogAggregatorTool.dto.LogAggregatorAuditData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogAggregatorAuditDataEntry {
    /**
     * This method makes the connection to the database and inserts the whole data into it.
     *
     * @param logAggregatorAuditData The data that needed to be inserted into the database.
     */
    public void auditEntry(LogAggregatorAuditData logAggregatorAuditData) {
        try {
            Connection auditDatabaseConnection = DriverManager.getConnection(DatabaseConstants.DATABASE_URL, DatabaseConstants.USERNAME, DatabaseConstants.PASSWORD);
            PreparedStatement auditPreparedStatement = auditDatabaseConnection.prepareStatement(DatabaseConstants.AUDIT_INSERT_QUERY);
            auditPreparedStatement.setString(1, logAggregatorAuditData.getPathToTheFolder());
            auditPreparedStatement.setInt(2, logAggregatorAuditData.getNumberOfLogFiles());
            auditPreparedStatement.setString(3, logAggregatorAuditData.getNamesOfLogFiles());
            auditPreparedStatement.setString(4, logAggregatorAuditData.getDateTime());
            auditPreparedStatement.setString(5, logAggregatorAuditData.getResult());
            auditPreparedStatement.setString(6, logAggregatorAuditData.getOutputFileName());
            auditPreparedStatement.setString(7, logAggregatorAuditData.getErrorMessage());
            auditPreparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(LogAggregatorConstants.DATABASE_ERROR + sqlException.getMessage());
            sqlException.printStackTrace();
        }
    }
}
