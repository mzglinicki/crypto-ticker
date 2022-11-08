package com.limba.cryptoticker.domain.utils

import com.limba.cryptoticker.R

enum class SupportedToken {
    BITCOIN {
        override val tokenName = "Bitcoin"
        override val tokenSymbol = "BTC"
        override val tickerUsdSymbol = "tBTCUSD"
        override val logoResId = R.drawable.bitcoin_btc_logo
    },
    ETHEREUM {
        override val tokenName = "Ethereum"
        override val tokenSymbol = "ETH"
        override val tickerUsdSymbol = "tETHUSD"
        override val logoResId = R.drawable.ethereum_eth_logo
    },
    SWISS_BORG {
        override val tokenName = "SwissBorg"
        override val tokenSymbol = "CHSB"
        override val tickerUsdSymbol = "tCHSB:USD"
        override val logoResId = R.drawable.swissborg_chsb_logo
    },
    LITECOIN {
        override val tokenName = "Litecoin"
        override val tokenSymbol = "LTC"
        override val tickerUsdSymbol = "tLTCUSD"
        override val logoResId = R.drawable.litecoin_ltc_logo
    },
    XRP {
        override val tokenName = "XRP"
        override val tokenSymbol = "XRP"
        override val tickerUsdSymbol = "tXRPUSD"
        override val logoResId = R.drawable.xrp_xrp_logo
    },
    DASHCOIN {
        override val tokenName = "Dashcoin"
        override val tokenSymbol = "DSH"
        override val tickerUsdSymbol = "tDSHUSD"
        override val logoResId = R.drawable.dash_dash_logo
    },
    RECOVERY_RIGHT_TOKEN {
        override val tokenName = "Recovery Right Token"
        override val tokenSymbol = "RRT"
        override val tickerUsdSymbol = "tRRTUSD"
        override val logoResId = R.drawable.bitfinex_rrt_logo
    },
    EOS {
        override val tokenName = "EOS"
        override val tokenSymbol = "EOS"
        override val tickerUsdSymbol = "tEOSUSD"
        override val logoResId = R.drawable.eos_eos_logo
    },
    STATUS {
        override val tokenName = "Status"
        override val tokenSymbol = "SNT"
        override val tickerUsdSymbol = "tSNTUSD"
        override val logoResId = R.drawable.status_snt_logo
    },
    DOGECOIN {
        override val tokenName = "Dogecoin"
        override val tokenSymbol = "DOGE"
        override val tickerUsdSymbol = "tDOGE:USD"
        override val logoResId = R.drawable.dogecoin_doge_logo
    },
    TERRA {
        override val tokenName = "Terra"
        override val tokenSymbol = "LUNA"
        override val tickerUsdSymbol = "tLUNA:USD"
        override val logoResId = R.drawable.terra_luna_logo_com
    },
    POLYGON {
        override val tokenName = "Polygon"
        override val tokenSymbol = "MATIC"
        override val tickerUsdSymbol = "tMATIC:USD"
        override val logoResId = R.drawable.polygon_matic_logo
    },
    NEXO {
        override val tokenName = "NEXO"
        override val tokenSymbol = "NEXO"
        override val tickerUsdSymbol = "tNEXO:USD"
        override val logoResId = R.drawable.nexo_nexo_logo
    },
    OCEAN_PROTOCOL {
        override val tokenName = "Ocean Protocol"
        override val tokenSymbol = "OCEAN"
        override val tickerUsdSymbol = "tOCEAN:USD"
        override val logoResId = R.drawable.ocean_protocol_ocean_logo
    },
    BITPANDA_ECOSYSTEM {
        override val tokenName = "Bitpanda Ecosystem"
        override val tokenSymbol = "BEST"
        override val tickerUsdSymbol = "tBEST:USD"
        override val logoResId = R.drawable.bitpanda_best_logo
    },
    AAVE {
        override val tokenName = "Aave"
        override val tokenSymbol = "AAVE"
        override val tickerUsdSymbol = "tAAVE:USD"
        override val logoResId = R.drawable.aave_aave_logo
    },
    PLUTON {
        override val tokenName = "Pluton"
        override val tokenSymbol = "PLU"
        override val tickerUsdSymbol = "tPLUUSD"
        override val logoResId = R.drawable.pluton_plu_logo
    },
    FILECOIN {
        override val tokenName = "Filecoin"
        override val tokenSymbol = "FIL"
        override val tickerUsdSymbol = "tFILUSD"
        override val logoResId = R.drawable.filecoin_fil_logo
    };

    abstract val tokenName: String
    abstract val tokenSymbol: String
    abstract val tickerUsdSymbol: String
    abstract val logoResId: Int

    companion object {
        fun getByTickerUsdSymbol(tickerUsdSymbol: String) =
            values().first { it.tickerUsdSymbol == tickerUsdSymbol }
    }
}