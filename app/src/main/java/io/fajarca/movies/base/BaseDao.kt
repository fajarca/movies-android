package io.fajarca.movies.base

import androidx.room.*
import io.reactivex.Completable

interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param entity the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T) : Completable


    /**
     * Insert an array of objects in the database.
     *
     * @param entity the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entity: List<T>) : Completable

    /**
     * Update an object from the database.
     *
     * @param entity the object to be updated
     */
    @Update
    fun update(entity: T) : Completable

    /**
     * Delete an object from the database
     *
     * @param entity the object to be deleted
     */

    @Delete
    fun delete(entity: T) : Completable
}