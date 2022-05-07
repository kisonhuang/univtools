package org.univ.tools.href;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.univ.tools.utils.LineUtils;

public class HtmlHrefParser {

	public static void main(String[] args) {
		String url = "https://nodejs.org/en/docs/guides/";
//		String url = "https://spring.io/projects/spring-boot";

//		String tag = "body";
		String tag = "article";

//		List<String> ignoreTags = Arrays.asList();
		List<String> ignoreTags = Arrays.asList("header", "footer", "nav");

		new HtmlHrefParser().writeHrefs(url, tag, ignoreTags);
	}

	public void writeHrefs(String url, String tag, List<String> ignoreTags) {
		List<String> hrefs = parseHrefs(url, tag, ignoreTags);
		LineUtils.printLines(hrefs);
	}

	public List<String> parseHrefs(String url, String tag, List<String> ignoreTags) {
		try {
			Document htmlDocument = Jsoup.connect(url).get();
			return parseHrefs(htmlDocument, tag, ignoreTags);
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public List<String> parseHrefs(Document htmlDocument, String tag, List<String> ignoreTags) {
		Element bodyElement = htmlDocument.getElementsByTag(tag).get(0);
		if (CollectionUtils.isNotEmpty(ignoreTags)) {
			for (String ignoreTag : ignoreTags) {
				bodyElement.getElementsByTag(ignoreTag).remove();
			}
		}
		Elements anchorElements = bodyElement.getElementsByTag("a");

		List<String> hrefs = new ArrayList<>();
		for (Element anchorElement : anchorElements) {
			String href = anchorElement.attr("href").trim();
			if (StringUtils.isNotEmpty(href) && !hrefs.contains(href)) {
				hrefs.add(href);
			}
		}

		Collections.sort(hrefs);
		return hrefs;
	}

}
