package com.korpi.domain.ports

import com.korpi.domain.ports.dto.UserId
import java.util.*

interface SessionStorage {
    suspend fun write(userId: Long, sessionId: UUID)
    suspend fun read(sessionId: UUID): UserId?
    suspend fun exists(sessionId: UUID): Boolean
    suspend fun invalidate(sessionId: UUID)
    suspend fun extend(sessionId: UUID)
    suspend fun extend(sessionId: String)
}