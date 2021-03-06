package org.univ.tools.api.java;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.univ.tools.api.ApiParser;
import org.univ.tools.utils.JsoupUtils;

public class Javase11ApiParser extends ApiParser {

	public static void main(String[] args) {
		new Javase11ApiParser().writeAll();
	}

	@Override
	protected String getProjectPath() {
		return "D:/Workspace/univtech/univ-tech/java/javase11";
	}

	@Override
	protected String getApiName() {
		return "JavaSE 11 API";
	}

	@Override
	protected String getApiUrl() {
		return "https://docs.oracle.com/en/java/javase/11/docs/api";
	}

	@Override
	protected String getAllClassUri() {
		return "allclasses.html";
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
	protected Elements parseAllClass(Document htmlDocument) {
		return JsoupUtils.getElementsByTagAndClass(htmlDocument, "main", "indexContainer");
	}

}
