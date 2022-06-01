package org.univ.tools;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.univ.tools.utils.LineUtils;

public class ApiPrinter {

	public static final String PATH = "D:\\Workspace\\Workspace_Univ\\univdocs\\univdocs\\spring\\spring-framework\\api\\核心";

	public static final String PREFIX = "org.springframework";

	public static void main(String[] args) {
		printFiles();
	}

	private static void printFiles() {
		Set<String> allLines = new HashSet<>();
		File[] files = new File(PATH).listFiles(fileFilter());
		readLines(allLines, files);
		LineUtils.printLines(allLines);
	}

	private static void printDirs() {
		Set<String> allLines = new HashSet<>();
		File[] dirs = new File(PATH).listFiles(dirFilter());
		for (File dir : dirs) {
			File[] files = dir.listFiles(fileFilter());
			readLines(allLines, files);
		}
		LineUtils.printLines(allLines);
	}

	private static void readLines(Set<String> allLines, File[] files) {
		for (File file : files) {
			try {
				List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);

				lines.forEach(line -> {
					if (line.contains(PREFIX)) {
						allLines.add(line.replace("+", "").trim());
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static FileFilter fileFilter() {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile();
			}
		};
	}

	private static FileFilter dirFilter() {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};
	}

}
