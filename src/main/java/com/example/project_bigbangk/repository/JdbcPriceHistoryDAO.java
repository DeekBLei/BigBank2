// Created by Deek
// Creation date 12/14/2021

package com.example.project_bigbangk.repository;

import com.example.project_bigbangk.model.Client;
import com.example.project_bigbangk.model.PriceHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public class JdbcPriceHistoryDAO implements IPriceHistoryDAO {

    private final Logger logger = LoggerFactory.getLogger(JdbcPriceHistoryDAO.class);
    JdbcTemplate jdbcTemplate;

    public JdbcPriceHistoryDAO(JdbcTemplate jdbcTemplate) {
        super();
        logger.info("New JdbcPriceHistoryDAO");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void savePriceHistory(PriceHistory priceHistory) {
        String sql = "Insert into PriceHistory values(?,?,?);";
        try {
            jdbcTemplate.update(sql,
                    priceHistory.getDateTime(),
                    priceHistory.getAsset().getCode(),
                    priceHistory.getPrice());
        } catch (DataAccessException dataAccessException) {
           logger.info(dataAccessException.getMessage());
        }
    }

    private class PiceHistoryRowMapper implements RowMapper<PriceHistory> {
        @Override
        public PriceHistory mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            LocalDate localDate = resultSet.getDate("datetime").toLocalDate();
            LocalDateTime localDateTime = localDate.atTime(resultSet.getTime("datetime").toLocalTime());
            return new PriceHistory(
                    localDateTime, resultSet.getDouble("price"));
        }
    }
}