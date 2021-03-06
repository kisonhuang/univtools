package org.univ.tools.api.java;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.univ.tools.api.ApiParser;
import org.univ.tools.utils.JsoupUtils;

public class Javase15ApiParser extends ApiParser {

	public static void main(String[] args) {
		new Javase15ApiParser().writeAll();
	}

	@Override
	protected String getProjectPath() {
		return "D:/Workspace/univtech/univ-tech/java/javase15";
	}

	@Override
	protected String getApiName() {
		return "JavaSE 15 API";
	}

	@Override
	protected String getApiUrl() {
		return "https://docs.oracle.com/en/java/javase/15/docs/api";
	}

	@Override
	protected String getAllClassUri() {
		return "allclasses-index.html";
	}

	@Override
	protected String getOverviewUri() {
		return "index.html";
	}

	@Override
	protected String getModuleUri() {
		return "module-summary.html";
	}

	@Override
	protected String getPackageUri() {
		return "package-summary.html";
	}

	@Override
	protected boolean writeModule() {
		return true;
	}

	@Override
	protected List<String> parseAllClass(String allClassUrl) {
		return parseTable(allClassUrl, "summary-table", 1, true);
	}

	@Override
	protected List<String> parseOverview(String overviewUrl) {
		return parseTable(overviewUrl, "summary-table", 1, false);
	}

	@Override
	protected Elements parseModule(Document htmlDocument) {
		return JsoupUtils.getElementsByTagAndClass(htmlDocument, "table", "summary-table");
	}

	@Override
	protected Elements parsePackage(Document htmlDocument) {
		return JsoupUtils.getElementsByTagAndClass(htmlDocument, "table", "summary-table");
	}

	@Override
	protected Elements parseColumnFirst(Element tableElement) {
		return tableElement.getElementsByClass("col-first");
	}

	@Override
	protected boolean printGenericType() {
		return true;
	}

}
