fun main() {
    class Mapper {
        private var map = listOf<Pair<LongRange, LongRange>>()

        fun addMapRanges(rngPair: Pair<LongRange, LongRange>) {
            map = map + rngPair
        }

        fun map(src: Long): Long {
            val rangePair = map.firstOrNull { it.first.contains(src) } ?: return src
            val diff = src - rangePair.first.first

            return rangePair.second.first + diff
        }
    }

    fun parseMapRange(line: String): Pair<LongRange, LongRange> {
        val (destination, source, count) = line.split(" ").map(String::toLong)

        val srcRange = source until source + count
        val destRange = destination until destination + count

        return srcRange to destRange
    }

    val seeds = mutableListOf<Long>()
    val seedToSoil = Mapper()
    val soilToFertilizer = Mapper()
    val fertilizerToWater = Mapper()
    val waterToLight = Mapper()
    val lightToTemperature = Mapper()
    val temperatureToHumidity = Mapper()
    val humidityToLocation = Mapper()

    fun parseInput(input: List<String>) {
        var parsingSeedToSoil = false
        var parsingSoilToFertilizer = false
        var parsingFertilizerToWater = false
        var parsingWaterToLight = false
        var parsingLightToTemperature = false
        var parsingTemperatureToHumidity = false
        var parsingHumidityToLocation = false

        for (line in input) {
            if (line.isBlank()) {
                parsingSeedToSoil = false
                parsingSoilToFertilizer = false
                parsingFertilizerToWater = false
                parsingWaterToLight = false
                parsingLightToTemperature = false
                parsingTemperatureToHumidity = false
                parsingHumidityToLocation = false
                continue
            }

            if (line.startsWith("seeds:")) {
                seeds.addAll(line.split("seeds: ", " ").filter(String::isNotBlank).map(String::toLong))
                continue
            }

            if (line.startsWith("seed-to-soil map:")) {
                parsingSeedToSoil = true
                continue
            }
            if (line.startsWith("soil-to-fertilizer map:")) {
                parsingSoilToFertilizer = true
                continue
            }
            if (line.startsWith("fertilizer-to-water map:")) {
                parsingFertilizerToWater = true
                continue
            }
            if (line.startsWith("water-to-light map:")) {
                parsingWaterToLight = true
                continue
            }
            if (line.startsWith("light-to-temperature map:")) {
                parsingLightToTemperature = true
                continue
            }
            if (line.startsWith("temperature-to-humidity map:")) {
                parsingTemperatureToHumidity = true
                continue
            }
            if (line.startsWith("humidity-to-location map:")) {
                parsingHumidityToLocation = true
                continue
            }

            if (parsingSeedToSoil) seedToSoil.addMapRanges(parseMapRange(line))
            if (parsingSoilToFertilizer) soilToFertilizer.addMapRanges(parseMapRange(line))
            if (parsingFertilizerToWater) fertilizerToWater.addMapRanges(parseMapRange(line))
            if (parsingWaterToLight) waterToLight.addMapRanges(parseMapRange(line))
            if (parsingLightToTemperature) lightToTemperature.addMapRanges(parseMapRange(line))
            if (parsingTemperatureToHumidity) temperatureToHumidity.addMapRanges(parseMapRange(line))
            if (parsingHumidityToLocation) humidityToLocation.addMapRanges(parseMapRange(line))
        }
    }

    fun seedToLocation(seed: Long): Long {
        val soil = seedToSoil.map(seed)
        val fertilizer = soilToFertilizer.map(soil)
        val water = fertilizerToWater.map(fertilizer)
        val light = waterToLight.map(water)
        val temperature = lightToTemperature.map(light)
        val humidity = temperatureToHumidity.map(temperature)
        return humidityToLocation.map(humidity)
    }

    fun part1(input: List<String>): Long {
        parseInput(input)

        return seeds.minOfOrNull(::seedToLocation) ?: 0L
    }

    fun part2(input: List<String>): Long {
        var minLocation = Long.MAX_VALUE

        parseInput(input)

        seeds.chunked(2)
            .map { it[0] until (it[0] + it[1]) }
            .forEach { rng ->
                rng.forEach {
                    val x = seedToLocation(it)
                    if (x < minLocation) {
                        minLocation = x
                    }
                }
            }

        return minLocation
    }

    val input = readInput("Day05")
    //part1(input).println()
    part2(input).println()
}