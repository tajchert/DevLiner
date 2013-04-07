import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//Class to keep historical information
public class DateAndLines implements Serializable{
	public Calendar date;
	public int lineNumber;
	private Format  dateFormatPrecise = new SimpleDateFormat("dd/MM/YYYY");
	
	public DateAndLines(Calendar date, int lines){
		this.date = date;
		this.lineNumber = lines;
	}
	
	public String getDate() {
		return dateFormatPrecise.format(date);
	}

	@Override
	public String toString() {
		return "Day [" + dateFormatPrecise.format(date.getTime()) + ", " + lineNumber+ "]";
	}

}
