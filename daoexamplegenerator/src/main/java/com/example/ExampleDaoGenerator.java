package com.example;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "me.itangqi.greendao");
        addNote(schema);
        new DaoGenerator().generateAll(schema, "\\Android_Internet\\Android_My_Project\\Framework_Project\\MVP\\MVP_BeautyListView\\app\\src\\main\\java-gen");
    }
    private static void addNote(Schema schema) {
        Entity cache = schema.addEntity("Cache");
        cache.addIdProperty();
        cache.addStringProperty("imgPath")
                .notNull();
        cache.addIntProperty("pxScreenHeight")
                .notNull();
    }
}
