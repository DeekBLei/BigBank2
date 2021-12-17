package com.example.project_bigbangk.repository;

import com.example.project_bigbangk.model.Asset;
import com.example.project_bigbangk.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcWalletDAO implements IWalletDAO{

    JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcWalletDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createNewWallet(Wallet wallet) {
        String slq = "Insert into wallet values(?,?);";
        jdbcTemplate.update(slq, wallet.getIban(), wallet.getBalance());
    }

    public void updateBalance(Wallet wallet) {
        String slq = "Update wallet Set balance = ? WHERE IBAN = ?";
        jdbcTemplate.update(slq, wallet.getBalance(), wallet.getIban());
    }

    public Wallet findWalletByIban(String iban) {
        String slq = "Select * From wallet Where IBAN = ?;";
        Wallet wallet = null;
        try {
            wallet = jdbcTemplate.queryForObject(slq, new walletRowMapper(), iban);
        } catch (DataAccessException dataAccessException) {
            System.err.println(dataAccessException.getMessage());
        }
        return wallet;
    }

    public void updateWalletAssets(Wallet wallet, Asset asset) {
        String sql = "Update wallet_has_asset Set amount = ? Where IBAN = ? And code = ?;";
        jdbcTemplate.update(sql, wallet.getIban(),asset.getAssetCodeName().getAssetCode());
    }

    @Override
    public Double findAmountOfAsset(String iban, String assetCode) {
        String sql = "SELECT * FROM wallet_has_asset WHERE IBAN = ? AND code = ?;";

        try {
            return jdbcTemplate.queryForObject(sql, new Wallet_has_assetRowMapper(), iban, assetCode);
        }  catch (DataAccessException dataAccessException) {
            System.err.println(dataAccessException.getMessage());
        }
        return null;
    }

    public class walletRowMapper implements RowMapper<Wallet> {

        @Override
        public Wallet mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Wallet( resultSet.getString("IBAN"), resultSet.getInt("balance"));
        }
    }

    public class Wallet_has_assetRowMapper implements RowMapper<Double> {

        @Override
        public Double mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return resultSet.getDouble("amount");
        }
    }

}
