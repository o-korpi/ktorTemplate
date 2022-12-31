package com.korpi.adapters.secondary

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.IColumnType
import org.jetbrains.exposed.sql.statements.api.PreparedStatementApi
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.ResultSet
import javax.sql.DataSource

abstract class Persistence {
    protected abstract val db: DataSource

    fun insert(statement: String, vararg params: Pair<IColumnType, Any?>) {
        fun prepareStatement(query: String): PreparedStatementApi {
            return TransactionManager.current()
                .connection
                .prepareStatement(query, false)
        }

        prepareStatement(statement).apply {
            fillParameters(params.toList())
        }.executeUpdate()
    }

    fun <T> read(statement: String, vararg params: Pair<IColumnType, Any?>, transform: (ResultSet) -> T): List<T> {
        val result = arrayListOf<T>()

        fun prepareStatement(query: String): PreparedStatementApi {
            return TransactionManager.current()
                .connection
                .prepareStatement(query, false)
        }

        val rs = prepareStatement(statement).apply {
            fillParameters(params.toList())
        }.executeQuery()

        while (rs.next()) {
            result += transform(rs)
        }
        return result
    }

    abstract fun connect(): DataSource
}
