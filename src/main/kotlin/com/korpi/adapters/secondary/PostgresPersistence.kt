package com.korpi.adapters.secondary

import com.korpi.config.DatabaseConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

object PostgresPersistence : Persistence() {
    private val host: String = DatabaseConfig.host.value
    private val port: Int = DatabaseConfig.port.value
    private val name: String = DatabaseConfig.name

    override val db = connect()
        /*Database.connect(
        "jdbc:postgresql://${DatabaseConfig.host}:${DatabaseConfig.port}/${DatabaseConfig.dbName}",
        "org.postgresql.driver",
        DatabaseConfig.user,
        DatabaseConfig.pass
    )

         */

    override fun connect(): DataSource {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql://$host:$port/$name"
        config.username = DatabaseConfig.user
        config.password = DatabaseConfig.pass
        config.driverClassName = "org.postgresql.Driver"

        return HikariDataSource(config)
    }
}
