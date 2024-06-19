package org.LogAggregatorTool.constants;

public class DatabaseConstants {
    public static final String AUDIT_INSERT_QUERY = "insert into audit(path_to_the_folder, number_of_files, names_of_files, date_time, result, output_file_name, error_message) values(?,?,?,?,?,?,?)";
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/poc1";
    public static final String PASSWORD = "Balaji@123";
    public static final String USERNAME = "root";
}
