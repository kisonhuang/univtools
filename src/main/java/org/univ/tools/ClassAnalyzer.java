package org.univ.tools;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class ClassAnalyzer {

	private static final String CLASS_SOURCE = "D:\\Workspace\\Workspace_Univ\\univdocs\\univdocs\\spring\\spring-framework\\api\\所有类.md";

	private static final String CLASS_NAME = "org.springframework.beans.factory.BeanFactory";

	private static final List<String> EXCLUDE_CLASS_NAMES = new ArrayList<>();

	static {
		EXCLUDE_CLASS_NAMES.add("org.springframework.transaction.jta.WebSphereUowTransactionManager");
		EXCLUDE_CLASS_NAMES.add("org.springframework.web.reactive.socket.server.upgrade.Jetty10RequestUpgradeStrategy");
		EXCLUDE_CLASS_NAMES.add("org.springframework.web.socket.server.jetty.Jetty10RequestUpgradeStrategy");
		EXCLUDE_CLASS_NAMES.add("org.springframework.web.socket.server.standard.GlassFishRequestUpgradeStrategy");
		EXCLUDE_CLASS_NAMES.add("org.springframework.web.socket.server.standard.WebLogicRequestUpgradeStrategy");
		EXCLUDE_CLASS_NAMES.add("org.springframework.web.socket.server.standard.WebSphereRequestUpgradeStrategy");
	}

	public static void main(String[] args) {
		analyseClasses();
	}

	private static void analyseClasses() {
		Map<String, Class<?>> classMap = loadClasses();
		ClassNode rootNode = analyseClasses(0, CLASS_NAME, classMap);
		printClasses(rootNode);
	}

	private static ClassNode analyseClasses(int level, String className, Map<String, Class<?>> classMap) {
		Class<?> currentClass = classMap.get(className);
		ClassNode currentNode = new ClassNode(level, className);

		for (Entry<String, Class<?>> childClassEntry : classMap.entrySet()) {
			String childClassName = childClassEntry.getKey();
			Class<?> childClass = childClassEntry.getValue();

			if (currentClass != childClass && isChildClass(currentClass, childClass)) {
				currentNode.addChild(analyseClasses(level + 1, childClassName, classMap));
			}
		}
		return currentNode;
	}

	private static boolean isChildClass(Class<?> currentClass, Class<?> childClass) {
		Class<?> superClass = childClass.getSuperclass();
		if (superClass != null && superClass.getName().equals(currentClass.getName())) {
			return true;
		}

		Class<?>[] superInterfaces = childClass.getInterfaces();
		if (ArrayUtils.isNotEmpty(superInterfaces)) {
			for (Class<?> superInterface : superInterfaces) {
				if (superInterface.getName().equals(currentClass.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	private static Map<String, Class<?>> loadClasses() {
		Map<String, Class<?>> classMap = new LinkedHashMap<>();

		try {
			List<String> classNames = FileUtils.readLines(new File(CLASS_SOURCE), StandardCharsets.UTF_8);

			for (String className : classNames) {
				if (StringUtils.isBlank(className) || EXCLUDE_CLASS_NAMES.contains(className)) {
					continue;
				}

				try {
					classMap.put(className, Class.forName(buildClassName(className)));
				} catch (Exception e) {
					System.out.println(className);
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return classMap;
	}

	private static String buildClassName(String className) {
		StringBuilder sbNewClassName = new StringBuilder();
		String[] classNameFrags = className.split("\\.");

		for (String classNameFrag : classNameFrags) {
			sbNewClassName.append(classNameFrag);
			if (Character.isUpperCase(classNameFrag.charAt(0))) {
				sbNewClassName.append("$");
			} else {
				sbNewClassName.append(".");
			}
		}

		String newClassName = sbNewClassName.toString();
		return newClassName.substring(0, newClassName.length() - 1);
	}

	private static void printClasses(ClassNode currentNode) {
		Collections.sort(currentNode.getChilds(), (first, second) -> {
			return truncateClassName(first.getName()).compareTo(truncateClassName(second.getName()));
		});

		System.out.println(buildClassName(currentNode));
		for (ClassNode childNode : currentNode.getChilds()) {
			printClasses(childNode);
		}
	}

	private static String buildClassName(ClassNode currentNode) {
		StringBuilder sbClassName = new StringBuilder();
		for (int index = 0; index < currentNode.getLevel(); index++) {
			sbClassName.append("    ");
		}
		if (currentNode.getLevel() > 0) {
			sbClassName.append("+ ");
		}
		sbClassName.append(currentNode.getName());
		return sbClassName.toString();
	}

	private static String truncateClassName(String className) {
		return className.substring(className.lastIndexOf(".") + 1);
	}

}
