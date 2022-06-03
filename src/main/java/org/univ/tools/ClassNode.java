package org.univ.tools;

import java.util.ArrayList;
import java.util.List;

public class ClassNode {

	private int level;

	private String name;

	private List<ClassNode> childs = new ArrayList<>();

	public ClassNode(int level, String name) {
		this.level = level;
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public List<ClassNode> getChilds() {
		return childs;
	}

	public void addChild(ClassNode child) {
		this.childs.add(child);
	}

}
