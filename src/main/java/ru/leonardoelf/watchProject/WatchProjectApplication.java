package ru.leonardoelf.watchProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

@SpringBootApplication
public class WatchProjectApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("hello world");
		SpringApplication.run(WatchProjectApplication.class, args);

		WatchService watchService = FileSystems.getDefault().newWatchService();

		String appHomePath=System.getProperty("user.dir");
		File watchDir=new File(appHomePath+"\\watchDir");
		if(!watchDir.exists()) {
            watchDir.mkdir();
        }
		Path path = Paths.get(appHomePath+"\\watchDir");


		//будем следить за созданием, изменение и удалением файлов.
		path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
		boolean poll = true;
		while (poll) {
			WatchKey key = watchService.take();
			for (WatchEvent<?> event : key.pollEvents()) {
				System.out.println("Event kind : " + event.kind() + " - File : " + event.context());
			}
			poll = key.reset();
		}
	}

}
