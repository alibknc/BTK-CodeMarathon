package com.alibknc.wallet.services

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.alibknc.wallet.models.Cards

const val db_name = "Database"
const val table_name = "cards"

class DBHelper(var context: Context) : SQLiteOpenHelper(context, db_name, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $table_name(id INTEGER PRIMARY KEY AUTOINCREMENT, cardNumber VARCHAR(256), cardExpire VARCHAR(256), cvv VARCHAR(256), cardIssuer VARCHAR(256), cardType VARCHAR(256), cardOrg VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertData(card: Cards): Long {
        val temp = getCardWithNumber(card.cardNumber!!)
        val db = this.writableDatabase
        if (temp.id == null) {
            val cv = ContentValues()
            cv.put("cardNumber", card.cardNumber)
            cv.put("cardExpire", card.cardExpire)
            cv.put("cvv", card.cvv)
            cv.put("cardIssuer", card.cardIssuer)
            cv.put("cardType", card.cardType)
            cv.put("cardOrg", card.cardOrg)
            val sonuc = db.insert(table_name, null, cv)

            if (sonuc == (-1).toLong()) {
                Toast.makeText(context, "Kayıt Başarısız!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Kayıt Başarılı", Toast.LENGTH_LONG).show()
            }
            return sonuc
        } else {
            return (-2).toLong()
        }
    }

    fun updateData(card: Cards, pcn: String): Int {
        if(card.cardNumber != pcn){
            val temp = getCardWithNumber(card.cardNumber!!)
            if(temp.id != null){
                return -2
            }else{
                val db = this.writableDatabase
                val cv = ContentValues()
                cv.put("cardNumber", card.cardNumber)
                cv.put("cardExpire", card.cardExpire)
                cv.put("cvv", card.cvv)
                cv.put("cardIssuer", card.cardIssuer)
                cv.put("cardType", card.cardType)
                cv.put("cardOrg", card.cardOrg)
                val sonuc = db.update(table_name, cv, "id=?", arrayOf(card.id))

                if (sonuc == -1) {
                    Toast.makeText(context, "Güncelleme Başarısız!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Güncelleme Başarılı", Toast.LENGTH_LONG).show()
                }
                return sonuc
            }
        }else{
            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put("cardNumber", card.cardNumber)
            cv.put("cardExpire", card.cardExpire)
            cv.put("cvv", card.cvv)
            cv.put("cardIssuer", card.cardIssuer)
            cv.put("cardType", card.cardType)
            cv.put("cardOrg", card.cardOrg)
            val sonuc = db.update(table_name, cv, "id=?", arrayOf(card.id))

            if (sonuc == -1) {
                Toast.makeText(context, "Güncelleme Başarısız!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Güncelleme Başarılı", Toast.LENGTH_LONG).show()
            }
            return sonuc
        }
    }

    fun getCardList(): MutableList<Cards> {
        val cardList: MutableList<Cards> = ArrayList()
        val db = this.writableDatabase
        val sql = "SELECT * FROM $table_name"
        val result = db.rawQuery(sql, null)
        if (result.moveToFirst()) {
            do {
                val card = Cards()
                card.cardNumber = result.getString(result.getColumnIndexOrThrow("cardNumber"))
                card.cardExpire = result.getString(result.getColumnIndexOrThrow("cardExpire"))
                card.cvv = result.getString(result.getColumnIndexOrThrow("cvv"))
                card.cardIssuer = result.getString(result.getColumnIndexOrThrow("cardIssuer"))
                card.cardType = result.getString(result.getColumnIndexOrThrow("cardType"))
                card.cardOrg = result.getString(result.getColumnIndexOrThrow("cardOrg"))
                card.id = result.getString(result.getColumnIndexOrThrow("id"))

                cardList.add(card)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return cardList
    }

    private fun getCardWithNumber(cn: String): Cards {
        val card = Cards()
        val db = this.writableDatabase
        val sql = "SELECT * FROM $table_name WHERE cardNumber == $cn"
        val result = db.rawQuery(sql, null)
        if (result.moveToFirst()) {
            do {
                card.cardNumber = result.getString(result.getColumnIndexOrThrow("cardNumber"))
                card.cardExpire = result.getString(result.getColumnIndexOrThrow("cardExpire"))
                card.cvv = result.getString(result.getColumnIndexOrThrow("cvv"))
                card.cardIssuer = result.getString(result.getColumnIndexOrThrow("cardIssuer"))
                card.cardType = result.getString(result.getColumnIndexOrThrow("cardType"))
                card.cardOrg = result.getString(result.getColumnIndexOrThrow("cardOrg"))
                card.id = result.getString(result.getColumnIndexOrThrow("id"))
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return card
    }

    fun getCardWithID(id: String): Cards {
        val card = Cards()
        val db = this.writableDatabase
        val sql = "SELECT * FROM $table_name WHERE id == $id"
        val result = db.rawQuery(sql, null)
        if (result.moveToFirst()) {
            do {
                card.cardNumber = result.getString(result.getColumnIndexOrThrow("cardNumber"))
                card.cardExpire = result.getString(result.getColumnIndexOrThrow("cardExpire"))
                card.cvv = result.getString(result.getColumnIndexOrThrow("cvv"))
                card.cardIssuer = result.getString(result.getColumnIndexOrThrow("cardIssuer"))
                card.cardType = result.getString(result.getColumnIndexOrThrow("cardType"))
                card.cardOrg = result.getString(result.getColumnIndexOrThrow("cardOrg"))
                card.id = result.getString(result.getColumnIndexOrThrow("id"))
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return card
    }

    fun deleteCard(id: String): Int {
        val db = this.writableDatabase
        val result = db.delete(table_name, "id=?", arrayOf(id))
        if (result == -1) {
            Toast.makeText(context, "Silme Başarısız!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Silme Başarılı", Toast.LENGTH_LONG).show()
        }
        db.close()
        return result
    }

}