package com.osherovskaya.labs.database.repository;

import jakarta.transaction.Transactional;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import com.osherovskaya.labs.database.model.File;

import java.util.List;
import java.util.Optional;

@RegisterRowMapper(FilesMapper.class)

public interface FilesDAO {
    @SqlQuery("SELECT id, file_name, size_in_kb FROM files WHERE id=:id")
    Optional<File> getById(@Bind("id") int id);

    @SqlQuery("SELECT id, file_name, size_in_kb FROM files")
    List<File> getAll();

    @SqlUpdate("INSERT INTO files (id, file_name, size_in_kb) VALUES (:id, :name, :size_in_kb)")
    @GetGeneratedKeys
    int insert(@Bind("id") int id, @Bind("name") String name, @Bind("size_in_kb") String size_in_kb);

    @SqlUpdate("UPDATE files SET file_name = :name, size_in_kb = :size_in_kb WHERE id = :id")
    void update(@Bind("id") int id, @Bind("name") String name, @Bind("size_in_kb") String size_in_kb);

    @SqlQuery("SELECT * FROM files WHERE name LIKE :name")
    List<File> findLikeName(@Bind("name") String name);

    @SqlUpdate("DELETE FROM files WHERE id=:id")
    void delete(@Bind("id") int id);
}
