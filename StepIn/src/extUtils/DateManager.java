package extUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManager {

	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyhhmm");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getDateFormat() {
		String CC = "20";
		DateFormat dateFormat = new SimpleDateFormat(CC + "yyMMdd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
