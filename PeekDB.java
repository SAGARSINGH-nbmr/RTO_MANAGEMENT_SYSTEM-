import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class PeekDB {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:./rto_db;AUTO_SERVER=TRUE", "sa", "");
            Statement stmt = conn.createStatement();

            System.out.println("\n========== ALL USERS ==========");
            printTable(stmt, "SELECT username, password, email, role, phone FROM users");

            System.out.println("\n========== ALL VEHICLES ==========");
            printTable(stmt, "SELECT registration_number, type, model, owner_id, color, engine_number FROM vehicles");

            System.out.println("\n========== VEHICLE REQUESTS ==========");
            printTable(stmt, "SELECT request_id, vehicle_type, vehicle_model, applicant_id, status FROM vehicle_requests");

            System.out.println("\n========== ALL LICENSES ==========");
            printTable(stmt, "SELECT license_id, license_type, user_id, status, issue_date, expiry_date FROM licenses");

            System.out.println("\n========== ALL CHALLANS ==========");
            printTable(stmt, "SELECT challan_id, vehicle_vin, offense_type, amount, is_paid, issue_date FROM challans");

            System.out.println("\n========== ALL TRANSACTIONS ==========");
            printTable(stmt, "SELECT transaction_id, user_id, amount, payment_method, transaction_type, status FROM transactions");

            System.out.println("\n========== CBT TEST RESULTS ==========");
            printTable(stmt, "SELECT result_id, user_id, score, total_questions, passed, test_date FROM cbt_results");

            System.out.println("\n========== TRANSFER REQUESTS ==========");
            printTable(stmt, "SELECT transfer_id, vehicle_vin, seller_id, buyer_id, buyer_mobile, status, transfer_token FROM transfer_requests");

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printTable(Statement stmt, String sql) {
        try {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();

            // Print header
            StringBuilder sep = new StringBuilder("+");
            StringBuilder header = new StringBuilder("|");
            for (int i = 1; i <= cols; i++) {
                String name = meta.getColumnName(i);
                int width = Math.max(name.length(), 18);
                sep.append("-".repeat(width + 2)).append("+");
                header.append(String.format(" %-" + width + "s |", name));
            }
            System.out.println(sep);
            System.out.println(header);
            System.out.println(sep);

            // Print rows
            int count = 0;
            while (rs.next()) {
                StringBuilder row = new StringBuilder("|");
                for (int i = 1; i <= cols; i++) {
                    String val = rs.getString(i);
                    if (val == null) val = "NULL";
                    int width = Math.max(meta.getColumnName(i).length(), 18);
                    if (val.length() > width) val = val.substring(0, width - 2) + "..";
                    row.append(String.format(" %-" + width + "s |", val));
                }
                System.out.println(row);
                count++;
            }
            System.out.println(sep);
            System.out.println("  Total rows: " + count);
            rs.close();
        } catch (Exception e) {
            System.out.println("  (Empty or not found)");
        }
    }
}
