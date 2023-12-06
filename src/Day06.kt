data class Race(
    val duration: Long,
    val distance: Long,
)

fun main() {
    fun parseInput(input: List<String>, takeSpacesIntoAccount: Boolean = true): List<Race> {
        val (_, timesStr) = input[0].split(": ")
            .filter(String::isNotBlank)
            .map(String::trim)

        val (_, distancesStr) = input[1].split(": ")
            .filter(String::isNotBlank)
            .map(String::trim)

        val times = when(takeSpacesIntoAccount) {
            true -> timesStr.split(" ")
                .filter(String::isNotBlank)
                .map { it.trim().toLong() }
            else -> listOf(timesStr.replace(" ", "").toLong())
        }

        val distances = when(takeSpacesIntoAccount) {
            true -> distancesStr.split(" ")
                .filter(String::isNotBlank)
                .map { it.trim().toLong() }
            else -> listOf(distancesStr.replace(" ", "").toLong())
        }

        return times.zip(distances).map { Race(it.first, it.second) }
    }

    fun processRaces(races: List<Race>): Long {
        return races.map { race ->
            (1 until race.duration).count { holdTime ->
                (race.duration - holdTime) * holdTime > race.distance
            }.toLong()
        }.reduce { acc, i -> acc * i }
    }

    fun part1(input: List<String>): Long {
        return processRaces(parseInput(input))
    }

    fun part2(input: List<String>): Long {
        return processRaces(parseInput(input, takeSpacesIntoAccount = false))
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}