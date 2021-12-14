package com.example.project_bigbangk.model;

import javax.lang.model.element.Name;

public enum AssetCode {
    BTC("Bitcoin"), ETH("Ethereum"),
    BNB("Binance Coin"), USDT("Tether"),
    SOL("Solana"), USDC("USD Coin"),
    ADA("Cardano"), XRP("XRP"),
    DOT("Polkadot"), DOGE("Dogecoin"),
    LUNA("Terra"), AVAX("Avalanche"),
    SHIB("SHIBA INU"), BUSD("Binance USD"),
    CRO("Crypto.com Coin"), MATIC("Polygon"),
    WBTC("Wrapped Bitcoin"), LTC("Litecoin"),
    UNI("Uniswap"), DAI("Dai");


    private String coinName;

    private AssetCode(String coinName) {
        this.coinName = coinName;
    }

    @Override
    public String toString() {
        return coinName;
    }

    public String getAssetCode() {
        return this.name();
    }

    public String getAssetName() {
        return coinName;
    }
}
