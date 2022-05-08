package com.example.database

import java.sql.DriverManager
import java.sql.Connection
import java.sql.Statement
import java.sql.ResultSet
import com.example.domain.Coffee

class Database {

    fun CreateTable(tableName: String) {
        var connection = DriverManager.getConnection("jdbc:sqlite:./coffees.db")
        var stmt = connection.createStatement()
        stmt.executeUpdate("create table if not exists "+tableName+" (" +
                "id TEXT, " +
                "name TEXT, " +
                "price NUMERIC, " +
                "weight NUMERIC, " +
                "roastlevel INT" +
            ")")
        stmt.close()
        connection.close()
    }

    fun Select(query: String): Array<Coffee?> {
        var conn = DriverManager.getConnection("jdbc:sqlite:./coffees.db")
        var stmt = conn.createStatement()
        var rs = stmt.executeQuery(query)

        var coffees: Array<Coffee?> = emptyArray()
        while (rs.next()) {
            var id = rs.getString("id")
            var name = rs.getString("name")
            var price = rs.getDouble("price")
            var weight = rs.getDouble("weight")
            var roastlevel = rs.getInt("roastlevel")
            if(id != null) {
                coffees = append(coffees, Coffee(id, name, price, weight, roastlevel))
            }
        }
        stmt.close()
        conn.close()
        return coffees
    }

    fun Insert(query: String, params: List<Any>): Boolean {
        var resp = execute(query, params)
        println("insert response " + resp)
        return resp
    }

    fun Update(query: String, params: List<Any>): Boolean {
        var resp = execute(query, params)
        println("update response " + resp)
        return resp
    }

    fun SelectOne(query: String, id: String): Coffee? {
        var conn = DriverManager.getConnection("jdbc:sqlite:./coffees.db")
        var stmt = conn.prepareStatement(query)
        stmt.setString(1, id)
        var rs = stmt.executeQuery()
        var coffees: Array<Coffee?> = emptyArray()
        while (rs.next()) {
            var id = rs.getString("id")
            var name = rs.getString("name")
            var price = rs.getDouble("price")
            var weight = rs.getDouble("weight")
            var roastlevel = rs.getInt("roastlevel")
            if(id != null) {
                coffees = append(coffees, Coffee(id, name, price, weight, roastlevel))
            }
        }
        stmt.close()
        conn.close()
        if (coffees.size > 0) {
            return coffees[0]
        }
        return null
    }

    fun execute(query: String, params: List<Any>): Boolean {
        var conn = DriverManager.getConnection("jdbc:sqlite:./coffees.db")
        var stmt = conn.prepareStatement(query)
        for (p in params.indices) {
            var i = p+1
            var current = params[p]
            if (current is Int) {
                stmt.setInt(i, current)
            } else if (current is String) {
                stmt.setString(i, current)
            } else if (current is Float) {
                stmt.setFloat(i, current)
            } else if (current is Boolean) {
                stmt.setBoolean(i, current)
            } else if (current is Double) {
                stmt.setDouble(i, current)
            }
        }
        var resp = stmt.execute()
        stmt.close()
        conn.close()
        return resp
    }

    fun <T> append(arr: Array<T>, element: T): Array<T?> {
        val array = arr.copyOf(arr.size + 1)
        array[arr.size] = element
        return array
    }
}
