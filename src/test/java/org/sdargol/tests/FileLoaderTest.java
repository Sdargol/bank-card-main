package org.sdargol.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sdargol.utils.FileLoader;

import java.util.List;

public class FileLoaderTest {
    @Test
    @DisplayName("Load file test")
    void loadTest(){
        List<String> listFileData = FileLoader.load("/sql/init.sql");

        Assertions.assertTrue(listFileData.size() > 0);
    }

    @Test
    @DisplayName("Sql starts with")
    void initSqlTest(){
        List<String> listFileData = FileLoader.load("/sql/init.sql");
        String sql = FileLoader.listToString(listFileData);

        Assertions.assertTrue(sql.startsWith("CREATE TABLE cards"));
    }
}
