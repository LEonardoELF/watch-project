package ru.leonardoelf.watchProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Главный класс
 */
public class WatchProjectApplication {

	public static void main(String[] args) throws IOException, InterruptedException {

		WatchService watchService = FileSystems.getDefault().newWatchService();

		Path path = Paths.get(getPath().getName());


		//регистрация событий за которыми нужно следить
		path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);


		boolean poll = true;
		while (poll) {
			WatchKey key = watchService.take();
			for (WatchEvent<?> event : key.pollEvents()) {
				System.out.println("Тип события : " + event.kind() + " - Файл : " + event.context());
			}
			poll = key.reset();
		}
		/*File folder = getPath();
		System.out.println(folder.getAbsolutePath());
		FileWatcher watcher = new FileWatcher(folder);
		watcher.addListener(new FileAdapter() {
			public void onCreated(FileEvent event) {
				System.out.printf("Файл %s создан",event.getFile().getName());
				System.out.println();
			}
			public void onModified(FileEvent event) {
				System.out.printf("Файл %s изменен",event.getFile().getName());
				System.out.println();
			}
			public void onDeleted(FileEvent event) {
				System.out.printf("Файл %s удален",event.getFile().getName());
				System.out.println();
			}
		}).watch();
		wait(1000);
		File file = new File(folder + "/test.txt");
		wait(1000);
		File file2 = new File(folder + "/test2.txt");
		wait(1000);
		System.out.println(file.getAbsolutePath());
		try(FileWriter writer = new FileWriter(file)) {
			writer.write("Some String");
		}
		wait(1000);
		file.delete();*/
	}

	/**
	 * Метод для создания пути к отслеживаемой папке
	 * Должен принимать путь извне(из таблицы базы данных или application.properties)
	 * @return путь
	 */
	private static File getPath() {
		//String appHomePath = System.getProperty("user.dir");
		File watchDir = new File("watchDir");
		if (!watchDir.exists()) {
			watchDir.mkdir();

		}
		//System.out.println(watchDir.getAbsolutePath());
		//Path path = Paths.get( "watchDir");
		return watchDir;
	}
	public static void  wait(int time) throws InterruptedException {
		Thread.sleep(time);
	}
}
