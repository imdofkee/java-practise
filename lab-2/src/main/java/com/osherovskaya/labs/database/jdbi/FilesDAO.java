//package com.osherovskaya.labs.database.jdbi;
//
////public class FilesDAO {
////}
//
//
////package com.osherovskaya.labs.database.jdbi.FilesDAO;
//
//import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
//import org.jdbi.v3.sqlobject.customizer.Bind;
//import org.jdbi.v3.sqlobject.statement.SqlQuery;
//import com.osherovskaya.labs.database.model.File;
//
//import java.util.List;
//import java.util.Optional;
//
//@RegisterRowMapper(FilesMapper.class)
//public interface OrdersDAO {
//
//    @SqlQuery("SELECT idFROM orders WHERE id=:id")
//    Optional<File> getById(@Bind("id") int id);
//
//    @SqlQuery("SELECT id, customer_name, amount FROM orders")
//    List<File> getAll();
//
//}
