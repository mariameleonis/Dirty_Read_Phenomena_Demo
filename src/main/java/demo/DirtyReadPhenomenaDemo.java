package demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class DirtyReadPhenomenaDemo {

  public static final String JDBC_URL = System.getenv("JDBC_URL");
  public static final String USER = System.getenv("JDBC_USER");
  public static final String PASSWORD = System.getenv("JDBC_PASSWORD");


  public static void main(String[] args) throws SQLException, IOException {

    try (val con1 = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {

      String dataSql;
      try (val is = DirtyReadPhenomenaDemo.class.getResourceAsStream("/data.sql")) {
        dataSql = new String(Objects.requireNonNull(is).readAllBytes(), StandardCharsets.UTF_8);
      }

      try (val stmt = con1.createStatement()) {
        con1.setAutoCommit(false);
        stmt.execute(dataSql);
        con1.commit();
      }

      // Start first transaction
      try (val con2 = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
        con2.setAutoCommit(false);
        try (val stmt1 = con1.createStatement()) {
          stmt1.executeUpdate("UPDATE student SET firstname = 'Duck' WHERE id = 1");
        }

        // Start second transaction
        try (val con3 = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {

          con3.setAutoCommit(false);

          try (val stmt2 = con2.createStatement()) {
            val rs = stmt2.executeQuery("SELECT firstname FROM student WHERE id = 1");
            while (rs.next()) {
              val name = rs.getString("firstname");
              log.info("Student's name: " + name);
            }
          }

          con2.commit();
          con3.commit();

        }
      }
    }
  }
}
