package org.univ.tools;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.univ.tools.utils.LineUtils;

public class ApiPrinter {

	public static final String SOURCE = "D:\\Workspace\\Workspace_Univ\\univdocs\\univdocs\\spring\\spring-framework\\api\\所有类2.md";

	public static final String TARGET = "D:\\Workspace\\Workspace_Univ\\univdocs\\univdocs\\spring\\spring-framework\\api\\体系结构.md";

	public static final String PREFIX = "org";

	public static void main(String[] args) {
		printFile();
	}

	private static void printFile() {
		Set<String> allLines = new HashSet<>();
		File file = new File(TARGET);
		readLines(allLines, file);
		removeLines(allLines);
		LineUtils.printLines(allLines);
	}

	private static void printFiles() {
		Set<String> allLines = new HashSet<>();
		File[] files = new File(TARGET).listFiles(fileFilter());
		readLines(allLines, files);
		removeLines(allLines);
		LineUtils.printLines(allLines);
	}

	private static void printDirs() {
		Set<String> allLines = new HashSet<>();
		File[] dirs = new File(TARGET).listFiles(dirFilter());
		for (File dir : dirs) {
			File[] files = dir.listFiles(fileFilter());
			readLines(allLines, files);
		}
		removeLines(allLines);
		LineUtils.printLines(allLines);
	}

	private static void readLines(Set<String> allLines, File... files) {
		for (File file : files) {
			try {
				List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);

				for (String line : lines) {
					if (!line.startsWith("#") && line.contains(PREFIX)) {
						int endIndex = line.indexOf("<") != -1 ? line.indexOf("<") : line.length();
						allLines.add(line.substring(0, endIndex).replace("+", "").trim());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void removeLines(Set<String> targetLines) {
		try {
			File source = new File(SOURCE);
			List<String> sourceLines = FileUtils.readLines(source, StandardCharsets.UTF_8);
			sourceLines.removeAll(targetLines);
			FileUtils.writeLines(source, sourceLines);
		} catch (IOException e) {
			e.printStackTrace();
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
