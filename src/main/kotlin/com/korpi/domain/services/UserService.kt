package com.korpi.domain.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.korpi.config.DatabaseConfig
import com.korpi.domain.models.User
import com.korpi.domain.ports.dto.varcharPair
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object UserService : CrudService<User> {
    private val database = DatabaseConfig.db
    fun create(email: String, rawPassword: String): User? {
        val password = BCrypt.withDefaults().hashToString(12, rawPassword.toCharArray())
        val connection = Database.connect(database.connect())
        transaction(connection) {
            database.insert(
                "INSERT INTO users (email, password) VALUES (?, ?)",
                varcharPair(email),
                varcharPair(password)
            )
        }

        return transaction(connection) {
            database.read(
                "SELECT * FROM users WHERE email=?",
                varcharPair(email)
            ) { rs ->
                User(
                    rs.getString("user_id").toLong(),
                    rs.getString("email"),
                    rs.getString("password")
                )
            }.firstOrNull()
        }
    }

    fun findByEmail(email: String): User? {
        return transaction(Database.connect(database.connect())) {
            database.read(
                "SELECT * FROM users WHERE email=?",
                varcharPair(email)
            ) { rs ->
                User(
                    rs.getString("user_id").toLong(),
                    rs.getString("email"),
                    rs.getString("password")
                )
            }.firstOrNull()
        }
    }

}
