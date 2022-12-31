package com.korpi.domain.ports.dto

import org.jetbrains.exposed.sql.VarCharColumnType

fun varcharPair(second: Any?) = Pair(VarCharColumnType(), second)