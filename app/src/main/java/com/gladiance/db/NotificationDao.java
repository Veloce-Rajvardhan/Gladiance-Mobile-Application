package com.gladiance.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.gladiance.AppConstants;
import com.gladiance.ui.activities.EspNode;

import java.util.List;

@Dao
public interface NotificationDao {
    @Query("SELECT * FROM " + AppConstants.NODE_TABLE)
    List<EspNode> getNodesFromStorage();

    /**
     * Update node if it exist in database, insert node otherwise.
     *
     * @param node Node to be inserted / updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(EspNode node);

    /**
     * Delete the node from database.
     *
     * @param node Node to be deleted.
     */
    @Delete
    void delete(EspNode node);

    /**
     * Delete all nodes from node table.
     */
    @Query("DELETE FROM " + AppConstants.NODE_TABLE)
    void deleteAll();
}
