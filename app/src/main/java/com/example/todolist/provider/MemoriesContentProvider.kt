package com.example.todolist.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.todolist.App
import com.example.todolist.database.MemoryDao
import com.example.todolist.entities.MemoryEntity

class MemoriesContentProvider() : ContentProvider() {


    /** The authority of this content provider.  */
    val AUTHORITY = "com.example.todolist.provider"

    /** The URI for the Menu table.  */
    val URI_MENU = Uri.parse(
        "content://" + AUTHORITY + "/" + MemoryEntity.TABLE_NAME
    )

    /** The match code for some items in the Menu table.  */
    private val CODE_MENU_DIR = 1

    /** The match code for an item in the Menu table.  */
    private val CODE_MENU_ITEM = 2

    /** The URI matcher.  */
    private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)
    lateinit var dao: MemoryDao

    init
    {
        MATCHER.addURI(AUTHORITY, MemoryEntity.TABLE_NAME, CODE_MENU_DIR)
        MATCHER.addURI(AUTHORITY, MemoryEntity.TABLE_NAME + "/*", CODE_MENU_ITEM)
    }




    override fun onCreate(): Boolean {
        dao= (context!!.applicationContext as App).daoMemory
        return true
    }


    override fun query(
         uri: Uri,  projection: Array<String?>?,  selection: String?,
         selectionArgs: Array<String?>?,  sortOrder: String?
    ): Cursor? {
        val code: Int =MATCHER.match(uri)
        return if (code == CODE_MENU_DIR || code == CODE_MENU_ITEM) {
            val context = context ?: return null

            val cursor =
                if (code == CODE_MENU_DIR) {
                    dao.selectAll()
                } else {
                    dao.selectById(ContentUris.parseId(uri))
                }

            cursor!!.setNotificationUri(context.contentResolver, uri)

            cursor

        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }


    override fun getType( uri: Uri): String? {
        return when (MATCHER.match(uri)) {
            CODE_MENU_DIR -> "vnd.android.cursor.dir/" + AUTHORITY + "." + MemoryEntity.TABLE_NAME
            CODE_MENU_ITEM -> "vnd.android.cursor.item/" + AUTHORITY + "." + MemoryEntity.TABLE_NAME
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
/////////////

    override fun insert( uri: Uri, values: ContentValues?): Uri? {
        return when (MATCHER.match(uri)) {
            CODE_MENU_DIR -> {
                val context = context ?: return null
                val id: Long = dao.insert(values?.let { MemoryEntity.fromContentValues(it) })
                context.contentResolver.notifyChange(uri, null)
                ContentUris.withAppendedId(uri, id)
            }

            CODE_MENU_ITEM -> throw IllegalArgumentException(
                "Invalid URI, cannot insert with ID: $uri"
            )

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun delete(
         uri: Uri,  selection: String?,
         selectionArgs: Array<String?>?
    ): Int {
        return when (MATCHER.match(
            uri
        )) {
            CODE_MENU_DIR -> throw IllegalArgumentException(
                "Invalid URI, cannot update without ID$uri"
            )

            CODE_MENU_ITEM -> {
                val context = context ?: return 0
                val count: Int = dao.deleteById(ContentUris.parseId(uri))
                context.contentResolver.notifyChange(uri, null)
                count
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun update(
         uri: Uri,  values: ContentValues?,  selection: String?,
         selectionArgs: Array<String?>?
    ): Int {
        return when (MATCHER.match(uri)) {
            CODE_MENU_DIR -> throw IllegalArgumentException(
                "Invalid URI, cannot update without ID$uri"
            )

            CODE_MENU_ITEM -> {
                val context = context ?: return 0
                val menu: MemoryEntity? = values?.let { MemoryEntity.fromContentValues(it) }
                menu!!.id = ContentUris.parseId(uri)
                val count: Int =dao.update(menu)
                context.contentResolver.notifyChange(uri, null)
                count
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }


 /*   @Throws(OperationApplicationException::class)
    fun applyBatch(
        operations: ArrayList<ContentProviderOperation?>?
    ): Array<ContentProviderResult?>? {
        val context = context ?: return arrayOfNulls(0)
        val database: SampleDatabase = SampleDatabase.getInstance(context)
        database.beginTransaction()
        return try {
            val result = super.applyBatch(operations!!)
            database.setTransactionSuccessful()
            result
        } finally {
            database.endTransaction()
        }
    }*/

    override fun bulkInsert( uri: Uri,  valuesArray: Array<ContentValues?>): Int {
        return when (MATCHER.match(uri)) {
            CODE_MENU_DIR -> {
                val context = context ?: return 0
                val menus: Array<MemoryEntity?> = arrayOfNulls<MemoryEntity>(valuesArray.size)
                var i = 0
                while (i < valuesArray.size) {
                    menus[i] = valuesArray[i]?.let { MemoryEntity.fromContentValues(it) }
                    i++
                }
                dao.insertAll(menus)!!.size
            }

            CODE_MENU_ITEM -> throw IllegalArgumentException(
                "Invalid URI, cannot insert with ID: $uri"
            )

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

}