package com.korpi.domain.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.korpi.config.DatabaseConfig
import com.korpi.domain.models.User
import com.korpi.domain.ports.dto.longPair
import com.korpi.domain.ports.dto.varcharPair
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.ResultSet

object UserService : CrudService<User> {
    private val database = DatabaseConfig.db
    private fun parseUserResult(rs: ResultSet): User? {
        return try {
            User(
                rs.getString("user_id").toLong(),
                rs.getString("email"),
                rs.getString("password")
            )
        } catch (_: Exception) {
            null
        }
    }

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
                parseUserResult(rs)
            }.firstOrNull()
        }
    }

    fun findByEmail(email: String): User? {
        return transaction(Database.connect(database.connect())) {
            database.read(
                "SELECT * FROM users WHERE email=?",
                varcharPair(email)
            ) { rs ->
                parseUserResult(rs)
            }.firstOrNull()
        }
    }

    fun findById(userId: Long): User? {
        return transaction(Database.connect(database.connect())) {
            database.read(
                "SELECT * FROM users WHERE user_id=?",
                longPair(userId)
            ) { rs ->
                parseUserResult(rs)
            }.firstOrNull()
        }
    }

}
