package com.korpi.domain.ports.dto

import org.jetbrains.exposed.sql.IntegerColumnType

fun integerPair(second: Int): Pair<IntegerColumnType, Any?> = Pair(IntegerColumnType(), second)