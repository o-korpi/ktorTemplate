package com.korpi.config

import com.korpi.adapters.secondary.Persistence
import com.korpi.adapters.secondary.PostgresPersistence
import com.korpi.domain.ports.dto.Host
import com.korpi.domain.ports.dto.Port

object DatabaseConfig {
    val host: Host = Host("localhost")
    val port: Port = Port(5432)
    const val name: String = "ktortestdb"
    val user: String = System.getenv("DB_USER")
    val pass: String = System.getenv("DB_PASS")
    val db: Persistence = PostgresPersistence  // Important: Must be below all other variables

}
