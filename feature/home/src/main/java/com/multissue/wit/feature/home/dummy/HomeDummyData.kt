package com.multissue.wit.feature.home.dummy

import com.multissue.wit.feature.home.state.CityState
import com.multissue.wit.feature.home.state.CountryState
import com.multissue.wit.feature.home.state.PopularDestinationState
import com.multissue.wit.feature.home.state.RegionState

val popularDestinationDummy = listOf(
    PopularDestinationState("\uD83C\uDDEF\uD83C\uDDF5", "오사카", 34.6937, 135.5023),
    PopularDestinationState("\uD83C\uDDED\uD83C\uDDF0", "홍콩섬", 22.2577, 114.1733),
    PopularDestinationState("\uD83C\uDDF8\uD83C\uDDEC", "센토사", 1.2494, 103.8303),
    PopularDestinationState("\uD83C\uDDF2\uD83C\uDDFE", "코타키나발루", 5.9788, 116.0753),
    PopularDestinationState("\uD83C\uDDF9\uD83C\uDDED", "방콕", 13.7563, 100.5018),
    PopularDestinationState("\uD83C\uDDEE\uD83C\uDDF3", "델리", 28.6139, 77.2090),
)

val regionDummyData = listOf(
    RegionState(
        id = "east_asia",
        name = "동아시아",
        emoji = "\uD83C\uDF0F",
        countries = listOf(
            CountryState(
                id = "kr",
                name = "대한민국",
                flagEmoji = "\uD83C\uDDF0\uD83C\uDDF7",
                cities = listOf(
                    CityState("kr_seoul", "서울", 37.5665, 126.9780),
                    CityState("kr_busan", "부산", 35.1796, 129.0756),
                    CityState("kr_jeju", "제주", 33.4996, 126.5312),
                ),
            ),
            CountryState(
                id = "jp",
                name = "일본",
                flagEmoji = "\uD83C\uDDEF\uD83C\uDDF5",
                cities = listOf(
                    CityState("jp_tokyo", "도쿄", 35.6762, 139.6503),
                    CityState("jp_osaka", "오사카", 34.6937, 135.5023),
                    CityState("jp_fukuoka", "후쿠오카", 33.5902, 130.4017),
                ),
            ),
            CountryState(
                id = "hk",
                name = "홍콩",
                flagEmoji = "\uD83C\uDDED\uD83C\uDDF0",
                cities = listOf(
                    CityState("hk_island", "홍콩섬", 22.2577, 114.1733),
                    CityState("hk_central", "센트럴", 22.2820, 114.1581),
                    CityState("hk_tsimshatsui", "침사추이", 22.2988, 114.1722),
                ),
            ),
            CountryState(
                id = "tw",
                name = "대만",
                flagEmoji = "\uD83C\uDDF9\uD83C\uDDFC",
                cities = listOf(
                    CityState("tw_taipei", "타이베이", 25.0330, 121.5654),
                    CityState("tw_taichung", "타이중", 24.1477, 120.6736),
                    CityState("tw_kaohsiung", "가오슝", 22.6273, 120.3014),
                ),
            ),
        ),
    ),
    RegionState(
        id = "southeast_asia",
        name = "동남아시아",
        emoji = "\uD83C\uDF0F",
        countries = listOf(
            CountryState(
                id = "sg",
                name = "싱가포르",
                flagEmoji = "\uD83C\uDDF8\uD83C\uDDEC",
                cities = listOf(
                    CityState("sg_marina", "마리나 베이", 1.2847, 103.8610),
                    CityState("sg_sentosa", "센토사", 1.2494, 103.8303),
                    CityState("sg_orchard", "오차드", 1.3048, 103.8318),
                ),
            ),
            CountryState(
                id = "id",
                name = "인도네시아",
                flagEmoji = "\uD83C\uDDEE\uD83C\uDDE9",
                cities = listOf(
                    CityState("id_bali", "발리", -8.4095, 115.1889),
                    CityState("id_jakarta", "자카르타", -6.2088, 106.8456),
                ),
            ),
            CountryState(
                id = "my",
                name = "말레이시아",
                flagEmoji = "\uD83C\uDDF2\uD83C\uDDFE",
                cities = listOf(
                    CityState("my_kl", "쿠알라룸푸르", 3.1390, 101.6869),
                    CityState("my_kk", "코타키나발루", 5.9788, 116.0753),
                    CityState("my_langkawi", "랑카위", 6.3500, 99.8000),
                ),
            ),
            CountryState(
                id = "th",
                name = "태국",
                flagEmoji = "\uD83C\uDDF9\uD83C\uDDED",
                cities = listOf(
                    CityState("th_bangkok", "방콕", 13.7563, 100.5018),
                    CityState("th_chiangmai", "치앙마이", 18.7883, 98.9853),
                    CityState("th_phuket", "푸켓", 7.8804, 98.3923),
                ),
            ),
        ),
    ),
)

