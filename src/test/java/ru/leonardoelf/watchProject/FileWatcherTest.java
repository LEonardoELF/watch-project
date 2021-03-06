package ru.leonardoelf.watchProject;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class FileWatcherTest {
    @Test
    public void test() throws IOException, InterruptedException {
        File folder = new File("watchDir");
        final Map<String, String> map = new HashMap<>();
        FileWatcher watcher = new FileWatcher(folder);
        watcher.addListener(new FileAdapter() {
            public void onCreated(FileEvent event) {
                map.put("file.created", event.getFile().getName());
            }
            public void onModified(FileEvent event) {
                map.put("file.modified", event.getFile().getName());
            }
            public void onDeleted(FileEvent event) {
                map.put("file.deleted", event.getFile().getName());
            }
        }).watch();
        assertEquals(1, watcher.getListeners().size());
        wait(2000);
        File file = new File(folder + "/test.txt");
        //File file2 = new File(folder + "/test2.txt");
        //System.out.println(file.getAbsolutePath());
        try(FileWriter writer = new FileWriter(file)) {
            writer.write("Some String");
        }
        wait(2000);
        file.delete();
        wait(2000);
        System.out.println("asd");
        assertEquals(file.getName(), map.get("file.created"));
        //assertEquals(file2.getName(), map.get("file.created"));
        assertEquals(file.getName(), map.get("file.modified"));
        assertEquals(file.getName(), map.get("file.deleted"));
    }
    public void wait(int time) throws InterruptedException {
        Thread.sleep(time);
    }
}