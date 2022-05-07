package org.univ.tools.href;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.univ.tools.utils.DocsFileUtils;
import org.univ.tools.utils.LineUtils;

public class FileAnchorParser {

	public static void main(String[] args) {
		new FileAnchorParser().parseWords();
	}

	private void parseWords() {
		try {
			String filePath = DocsFileUtils.getDocsFilePath();
			Document html = Jsoup.parse(new File(filePath), "UTF-8");
			Elements lis = html.getElementsByTag("li");
			List<Element> wordLis = lis.stream().filter(li -> !li.hasClass("hidden")).collect(Collectors.toList());

			List<Element> anchors = new ArrayList<>();
			for (Element wordLi : wordLis) {
				anchors.addAll(wordLi.getElementsByTag("a"));
			}

			List<String> words = new ArrayList<>();
			for (Element anchor : anchors) {
				String word = anchor.text().trim();
				if (StringUtils.isNotBlank(word) && !words.contains(word)) {
					words.add(word);
				}
			}
			LineUtils.printLines(words);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
