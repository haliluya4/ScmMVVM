
package com.xjx.scm.db;



import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.xjx.scm.vo.User;

/**
 * 数据库
 */
@Database(entities = {User.class}, version = 2)
public abstract class ScmDataBase extends RoomDatabase {

    abstract public UserDao userDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL(
                    "CREATE TABLE USER_new (id INTEGER NOT NULL,"
                            + "userName TEXT,"
                            + "name TEXT,"
                            + "agentName TEXT,"
                            + "token TEXT,"
                            + "PRIMARY KEY(id))");
            // Copy the data
            database.execSQL("INSERT INTO USER_new (id, userName, name, agentName, token) "
                    + "SELECT * "
                    + "FROM USER");
            // Remove the old table
            database.execSQL("DROP TABLE USER");
            // Change the table name to the correct one
            database.execSQL("ALTER TABLE USER_new RENAME TO USER");
        }
    };
}
