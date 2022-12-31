package com.korpi.config

import com.korpi.adapters.secondary.PostgresPersistence
import com.korpi.adapters.secondary.Persistence
import com.korpi.domain.ports.dto.Host
import com.korpi.domain.ports.dto.Port

object DatabaseConfig {
    val host: Host = Host("localhost")
    val port: Port = Port(5432)
    val name: String = "ktortestdb"
    const val user: String = "" // TODO: env variable
    const val pass: String = "" // env
    val db: Persistence = PostgresPersistence  // Important: Must be below all other variables

}
