import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

//Class to keep historical information
public class DateAndLines {
	public Date date;
	public int lineNumber;
	private Format dateFormatPrecise = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	
	public String printDate() {
		String res = dateFormatPrecise.format(date);
		return res;
	}

}
