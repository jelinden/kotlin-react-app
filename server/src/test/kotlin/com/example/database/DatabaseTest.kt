package com.example.database

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertTrue

import com.example.domain.Coffee
import org.junit.runners.*
import org.junit.FixMethodOrder

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
internal class DatabaseTest {

    private val db: Database = Database()
    val testTableName: String = "coffeetesttable"

    @Test
    fun testDatabase_01() {
        val arr = db.Select("select * from "+testTableName+";") as Array<Coffee>
        for (coffee: Coffee in arr) {
            println(coffee)
        }
        assertTrue(arr.size > 0, "there should be atleast one Coffee in database")
    }

    @Test
    fun testDatabase_02() {
        val cof = db.SelectOne("select * from "+testTableName+" where id=?;", "abc") as Coffee
        println(cof)
        assertTrue(cof.id == "abc", "there should be Coffee with id abc in database")
    }

    @Test
    fun testRemoveCoffee_03() {
        val resp = db.Insert("delete from "+testTableName+" where id = ?;", listOf<Any>("abc"))
        println("testRemoveCoffee " + resp)
        val arr = db.Select("select * from "+testTableName+";") as Array<Coffee>
        assertTrue(arr.size == 0, "after delete table should be empty")
    }

    @BeforeTest
    fun before() {
        db.CreateTable(testTableName)
        db.Insert("insert into " + testTableName + " values(?,?,?,?,?);",
            listOf<Any>("abc", "name", 5.8, 0.700, 3))
    }
}
