package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

public class pageObjectGenerator {
	static String fileName = "out.java";
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File input = new File("E:\\poc\\test.html");
		Scanner scanner = new Scanner(System. in); 
		String workingDir = System.getProperty("user.dir");
		String pageName = scanner. nextLine();
		fileName = workingDir+"/src/pageObjects/"+pageName+".java";


		try {
			classWrapper(fileName,pageName);

			System.out.println("creating Page Object");
			Document doc = Jsoup.parse(input, "UTF-8");
			Elements objects = doc.getElementsByTag("button");	
			getElements(objects);	
			objects = doc.getElementsByTag("input");
			getElements(objects);	
			objects = doc.getElementsByTag("textarea");
			getElements(objects);
			objects = doc.getElementsByTag("img");
			getElements(objects);	
			objects = doc.getElementsByTag("a");
			getElements(objects);
			objects = doc.getElementsByTag("select");
			getElements(objects);

			closewrapper(fileName);

			System.out.println("Created page object "+ pageName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void getElements(Elements objects) {

		for (Element object : objects) {
			// String linkText = link.text();
			getAttribute(object);
		}
	}

	public static void getAttribute(Element element) {
		String value = null;
		value = element.attr("id");
		String function = null;
		if (value == null || value.equals("")) {
			value = element.attr("name");
			if (value == null || value.equals("")) {
				value = element.attr("title");
				if (value == null || value.equals("")) {
					value = element.attr("class");
					if (value == null || value.equals("")) {
						value = element.text();
						if (value == null || value.equals("")) {
							value = element.attr("alt");
							if (value == null || value.equals("")) {

							} else {
								function = locateByXPath(element.tagName(), "alt", value);
							}
						} else {
							function = locateByXPath(element.tagName(), "text", value);
						}
					} else {


						function = locateByXPath(element.tagName(), "class", value);
					}
				} else {
					System.out.println(": title :" + value);
					function = locateByXPath(element.tagName(), "title", value);
				}
			} else {
				System.out.println(": name :" + value);
				function = locateByName(value);
			}
		} else {
			System.out.println(": id :" + value);
			String name = element.attr("name");
			if (name.equals("")) {
				String title = element.attr("title");
				if (title.equals("")) {
					function =locateById(value, value);
				} else {
					function =locateById(value, title);
				}
			} else {
				function =locateById(value, name);
			}
		}
		try {
			createUpdateFile(fileName,function);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String locateById(String id, String name) {
		String function = "public WebElement " + name + "(){" + "\n" + "return driver.findElement(By.id(\"" + id
				+ "\"));" + "\n" + "}" + "\n";

		return function;
	}

	public static String locateByName(String name) {
		String function = "public WebElement " + name + "(){" + "\n" + "return driver.findElement(By.name(\"" + name
				+ "\"));" + "\n" + "}" + "\n";

		return function;
	}

	public static String locateByLink(String link) {
		String temp[]=link.split(" ");
		String fnName = temp[0];
		if(temp.length>1)
		{
			for(int i=1;i<temp.length;i++)
			{
				fnName=fnName+"_"+temp[i];
			}
		}
		String function = "public WebElement " + fnName + "(){" + "\n" + "return driver.findElement(By.linkText(\"" + link
				+ "\"));" + "\n" + "}" + "\n";

		return function;
	}

	public static String locateByXPath(String tag, String type, String value) {
		String function = null;
		if (tag.equals("a")) {
			function=locateByLink(value);
		} else {
			if (type.equals("text")) {
				function = "public WebElement " + tag + value + "(){" + "\n" + "return driver.findElement(By.xpath(\"//"
						+ tag + "[text()=" + type + "=\"" + value + "\"]\"));" + "\n" + "}" + "\n";
			} else {
				if(type.equals("class"))
				{
					function = "public WebElement " + tag + value + "(){" + "\n" + "return driver.findElement(By.xpath(\"//"
							+ tag + "[@" + type + "='" + value + "']\"));" + "\n" + "}" + "\n";
				}
				else
				{
					function = "public WebElement " + tag + value + "(){" + "\n" + "return driver.findElement(By.xpath(\"//"
							+ tag + "[@" + type + "='" + value + "']\"));" + "\n" + "}" + "\n";
				}
			}
		}
		return function;
	}

	private static void createUpdateFile(String fileName, String data) throws IOException {
		File file = new File(fileName);
		FileWriter writer = new FileWriter(file, true);
		writer.append(data);
		writer.close();
	}

	public static void classWrapper(String fileName, String pageName)
	{
		String staetement = "package pageObjects;\r\n"
				+ "import org.openqa.selenium.By;\r\n" + 
				"import org.openqa.selenium.WebDriver;\r\n" + 
				"import org.openqa.selenium.WebElement;\r\n"+
				"import utils.AppLaunch;\r\n"+
				"\r\n"+
				"public class "+pageName+" {\r\n"+
				"\r\n"+
				"WebDriver driver = AppLaunch.driver;\r\n";

		try {
			File file = new File(fileName);
			FileWriter writer = new FileWriter(file, true);
			writer.append(staetement);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void closewrapper(String pageName)
	{
		try {
			File file = new File(pageName);
			FileWriter writer = new FileWriter(file, true);
			writer.append("\r\n}");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
