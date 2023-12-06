package day5

import java.io.File
import java.math.BigInteger

val input = File("src/main/kotlin/day5/input.txt")

val numberPattern = Regex("\\d+") // matches any number

val seedToSoilMappers = mutableListOf<SourceDestinationMapper>()
val soilToFertilizerMappers = mutableListOf<SourceDestinationMapper>()
val fertilizerToWaterMappers = mutableListOf<SourceDestinationMapper>()
val waterToLightMappers = mutableListOf<SourceDestinationMapper>()
val lightToTemperatureMappers = mutableListOf<SourceDestinationMapper>()
val temperatureToHumidityMappers = mutableListOf<SourceDestinationMapper>()
val humidityToLocationMappers = mutableListOf<SourceDestinationMapper>()

val mappers = mapOf(
    0 to seedToSoilMappers,
    1 to soilToFertilizerMappers,
    2 to fertilizerToWaterMappers,
    3 to waterToLightMappers,
    4 to lightToTemperatureMappers,
    5 to temperatureToHumidityMappers,
    6 to humidityToLocationMappers
)

fun main() {
    part2()
}

private fun part2() {
    val input = input.readLines()
    val seeds = mutableListOf<LongRange>()

    var currentMapper = -1

    input.filterNot { it.isBlank() }.forEachIndexed { index, line ->
        val numbers = numberPattern.findAll(line).map { it.value.toLong() }.toList()

        if (index == 0) {
            numbers.chunked(2).forEach {
                seeds.add(it[0]..it[0] + it[1])
            }
            return@forEachIndexed
        }

        if (numbers.isEmpty()) { // go to next mapper
            currentMapper++
            return@forEachIndexed
        } else {
            val sourceDestinationMapper = SourceDestinationMapper(numbers[1], numbers[0], numbers[2])
            mappers[currentMapper]?.add(sourceDestinationMapper) ?: throw IllegalStateException("unknown mapper")
        }
    }

    var currentLocation = 0L
    while (true) {
        val seed = mapSeedToLocation(currentLocation, true)
        if (seeds.any { it.contains(seed) }) {
            println("seed: $seed")
            println("location " + mapSeedToLocation(seed, false))
            break
        }
        currentLocation++
    }
}


private fun part1() {
    val input = input.readLines()
    val seeds = mutableListOf<Long>()

    var currentMapper = -1

    input.filterNot { it.isBlank() }.forEachIndexed { index, line ->
        val numbers = numberPattern.findAll(line).map { it.value.toLong() }.toList()

        if (index == 0) {
            seeds.addAll(numbers)
            return@forEachIndexed
        }

        if (numbers.isEmpty()) { // go to next mapper
            currentMapper++
            return@forEachIndexed
        } else {
            val sourceDestinationMapper = SourceDestinationMapper(numbers[1], numbers[0], numbers[2])
            mappers[currentMapper]?.add(sourceDestinationMapper) ?: throw IllegalStateException("unknown mapper")
        }
    }

    println(seeds.minOfOrNull(::mapSeedToLocation))
}

private fun mapSeedToLocation(source: Long, reversed: Boolean = false): Long {
    var tempNumber = source

    val mappers = if (reversed) mappers.map { it.value }.reversed() else mappers.map { it.value }
    mappers.forEach { tempNumber = mapSourceToDestination(tempNumber, it, reversed) }
    return tempNumber
}

private fun mapSourceToDestination(
    source: Long,
    mappers: List<SourceDestinationMapper>,
    reversed: Boolean = false
): Long =
    mappers.find { it.containsSource(source, reversed) }?.map(source, reversed) ?: source

data class SourceDestinationMapper(
    val sourceRangeStart: Long,
    val destinationRangeStart: Long,
    val rangeLength: Long
) {
    fun containsSource(source: Long, reversed: Boolean) = if (reversed) {
        source in destinationRangeStart..<destinationRangeStart + rangeLength
    } else {
        source in sourceRangeStart..<sourceRangeStart + rangeLength
    }

    fun map(source: Long, reversed: Boolean) = if (reversed) sourceRangeStart + (source - destinationRangeStart) else
        destinationRangeStart + (source - sourceRangeStart)
}