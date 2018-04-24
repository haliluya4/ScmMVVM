package com.xjx.scm.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.xjx.scm.vo.User;

/**
 * 用户数据DAO
 */
@Dao
public abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(User... users);

    @Query("SELECT * FROM User LIMIT 1")
    public abstract LiveData<User> getCurrentUser();

    @Query("DELETE FROM User")
    abstract int deleteAllUser();

    /**
     * 登录后保存当前用户（顺便清空旧的）
     * @param user 最新用户
     */
    public void updateCurrentUser(User user) {
        deleteAllUser();
        insert(user);
    }
}
