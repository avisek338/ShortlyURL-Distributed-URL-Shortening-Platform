package avisek.example.analyticsservice.repository;


import avisek.example.analyticsservice.entity.ClickLog;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClickLogRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = """
        INSERT INTO click_logs (
           event_id,
           short_url_code,
            clicked_at
          )
           VALUES (?, ?, ?)
          ON CONFLICT(event_id) DO NOTHING
           """;


    public void bulkInsert(List<ClickLog> clickLogs) {

        jdbcTemplate.batchUpdate(
                INSERT_SQL,
                clickLogs,
                clickLogs.size(),
                (preparedStatement, clickLog) -> {
                    preparedStatement.setObject(1, clickLog.getEventId());
                    preparedStatement.setString(2, clickLog.getShortUrlCode());
                    preparedStatement.setTimestamp(
                            3,
                            Timestamp.from(clickLog.getClickedAt())
                    );
                }
        );
    }
}
