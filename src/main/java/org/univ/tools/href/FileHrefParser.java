package org.univ.tools.href;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.univ.tools.utils.DocsFileUtils;
import org.univ.tools.utils.LineUtils;

public class FileHrefParser {

	public static void main(String[] args) {
		new FileHrefParser().writeHrefs();
//		new FileHrefParser().printLinuxCmds();
	}

	private void writeHrefs() {
		List<String> hrefs = parseHrefs();

		Set<String> hrefUrls = new LinkedHashSet<>();
		for (String href : hrefs) {
			if (href.startsWith("#")) {
				continue;
			}
			hrefUrls.add(href);
		}

//		LineUtils.printLines(hrefs);
//		System.out.println("====================================================================================================");
		LineUtils.printLines(hrefUrls);
	}

	private List<String> parseHrefs() {
		try {
			String hrefBegin = "href=\"";
			String hrefEnd = "\"";
			List<String> lines = DocsFileUtils.readLines();

			List<String> hrefs = new ArrayList<>();
			for (String line : lines) {
				while (line.contains(hrefBegin)) { // 可能存在多个href
					line = line.substring(line.indexOf(hrefBegin) + hrefBegin.length());
					int endIndex = line.indexOf(hrefEnd);
					String href = line.substring(0, endIndex).trim();
					if (StringUtils.isNotEmpty(href)) {
						hrefs.add(href);
					}
					line = line.substring(endIndex);
				}
			}
//			Collections.sort(hrefs);
			return hrefs;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	private void printLinuxCmds() {
		List<String> commands = new ArrayList<>();
		List<String> lines = DocsFileUtils.readLines();
		for (String line : lines) {
			String[] cmds = line.split(" ");
			for (String cmd : cmds) {
				if (StringUtils.isNotBlank(cmd.trim())) {
					commands.add(cmd.trim());
				}
			}
		}
		Collections.sort(commands);
		LineUtils.printLines(commands);
	}

}
