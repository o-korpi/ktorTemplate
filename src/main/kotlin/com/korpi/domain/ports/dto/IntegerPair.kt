package com.korpi.domain.ports.dto

import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.LongColumnType

fun integerPair(second: Int): Pair<IntegerColumnType, Any?> = Pair(IntegerColumnType(), second)

fun longPair(second: Long): Pair<LongColumnType, Any?> = Pair(LongColumnType(), second)