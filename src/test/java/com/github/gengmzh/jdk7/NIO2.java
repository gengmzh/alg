/**
 * 
 */
package com.github.gengmzh.jdk7;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @since 2012-4-11
 * @author gmz
 * 
 */
public class NIO2 {

	@Test
	public void test_path_create() throws Exception {
		URL url = NIO2.class.getClassLoader().getResource("./");
		// System.out.println(url.toURI());
		Path path = Paths.get(url.toURI());
		System.out.println(path.toString());

		System.out.println(path.getParent());
		System.out.println(path.isAbsolute());
		System.out.print(path.getNameCount() + ": ");
		for (int i = 0; i < path.getNameCount(); i++) {
			System.out.print(path.getName(i) + " ");
		}
		System.out.println();

		Path p2 = path.resolve("nio2");
		System.out.println(p2 + ": " + p2.toFile().exists());
		p2 = path.resolve("../../src").normalize();
		System.out.println(p2 + ": " + Files.exists(p2));

	}

	@Test
	public void test_check_file() throws Exception {
		URL url = NIO2.class.getClassLoader().getResource("./com/github/gengmzh/jdk7/NIO2.class");
		Path path = Paths.get(url.toURI());

		System.out.println(path);
		System.out.println(Files.exists(path) + " " + Files.isRegularFile(path) + " " + Files.isReadable(path) + " "
				+ Files.isWritable(path) + " " + Files.isExecutable(path));

		Path other = path.resolve("../NIO2.class");
		System.out.println(Files.isSameFile(path, other));

	}

	@Test
	public void test_delete() throws Exception {
		URL url = NIO2.class.getClassLoader().getResource("./com/github/gengmzh/jdk7/");
		Path path = Paths.get(url.toURI()).resolve("/NIO2.class.txt");

		if (Files.notExists(path)) {
			Files.createFile(path);
		}
		Assert.assertTrue(Files.exists(path));

		Files.deleteIfExists(path);
		Assert.assertTrue(Files.notExists(path));
	}

	@Test
	public void test_copy_move() throws Exception {
		URL url = NIO2.class.getClassLoader().getResource("./com/github/gengmzh/jdk7/");
		Path path = Paths.get(url.toURI()).resolve("NIO2.class.txt");
		if (Files.notExists(path)) {
			Files.createFile(path);
		}
		Assert.assertTrue(Files.exists(path));

		// copy
		Path target = path.resolve("../../NIO2.class.txt").normalize();
		target = Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
		// System.out.println(target);
		Assert.assertTrue(Files.exists(path) && Files.exists(target));
		Files.delete(target);
		Assert.assertFalse(Files.exists(target));

		// move
		target = path.resolve("../../NIO2.class.txt").normalize();
		target = Files.move(path, target, StandardCopyOption.REPLACE_EXISTING);
		Assert.assertFalse(Files.exists(path));
		Assert.assertTrue(Files.exists(target));
		Files.delete(target);
		Assert.assertFalse(Files.exists(target));
	}

	@Test
	public void test_read_attribute() throws Exception {
		URL url = NIO2.class.getClassLoader().getResource("./com/github/gengmzh/jdk7/");
		Path path = Paths.get(url.toURI()).resolve("NIO2.class.txt");
		if (Files.notExists(path)) {
			Files.createFile(path);
		}
		Assert.assertTrue(Files.exists(path));

		// attribute
		Map<String, Object> attr = Files.readAttributes(path, "*");
		for (String name : attr.keySet()) {
			System.out.println(name + ": " + attr.get(name));
		}

		Files.delete(path);
	}

	@Test
	public void test_write_read() throws Exception {
		URL url = NIO2.class.getClassLoader().getResource("./com/github/gengmzh/jdk7/");
		Path path = Paths.get(url.toURI()).resolve("NIO2.class.txt");
		if (Files.notExists(path)) {
			Files.createFile(path);
		}
		Assert.assertTrue(Files.exists(path));

		String text = "File IO/NIO.2";
		Files.write(path, text.getBytes(), StandardOpenOption.WRITE);
		Files.write(path, text.getBytes(), StandardOpenOption.WRITE);

		text = new String(Files.readAllBytes(path));
		System.out.println(text);

		Files.deleteIfExists(path);
	}

	@Test
	public void test_walk_file_tree() throws Exception {
		URL url = NIO2.class.getClassLoader().getResource("./com/github/gengmzh/jdk7/");
		Path path = Paths.get(url.toURI()).resolve("../").normalize();

		Files.walkFileTree(path, new FileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				System.out.println("dir: " + dir);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				System.out.println("file: " + file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				exc.printStackTrace();
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}
		});

	}

	@Test
	public void test_watch() throws Exception {
		URL url = NIO2.class.getClassLoader().getResource("./com/github/gengmzh/jdk7/");
		final Path path = Paths.get(url.toURI());

		WatchService watcher = FileSystems.getDefault().newWatchService();
		WatchKey key = path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE);

		Thread th = new Thread() {
			public void run() {
				try {
					Path p = path.resolve("watch");
					if (Files.notExists(p)) {
						Files.createDirectory(p);
					}
					Thread.sleep(1000);
					Files.deleteIfExists(p);
					// System.out.println("create dir thread done");
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		};
		th.start();
		th.join();

		// long st = System.currentTimeMillis();
		// while ((System.currentTimeMillis() - st) > 60 * 1000) {
		List<WatchEvent<?>> events = key.pollEvents();
		for (WatchEvent<?> ev : events) {
			WatchEvent.Kind<?> k = ev.kind();
			Path p = (Path) ev.context();
			System.out.println(k + ": " + p);
		}
		// }
	}

}
