import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

//Class to keep historical information
public class DateAndLines implements Serializable{
	public Date date;
	public int lineNumber;
	private Format  dateFormatPrecise = new SimpleDateFormat("dd/MM/YYYY");
	
	public DateAndLines(Date date, int lines){
		this.date = date;
		this.lineNumber = lines;
	}
	
	public String getDate() {
		return dateFormatPrecise.format(date);
	}

	@Override
	public String toString() {
		return "Day [" + date + ", " + lineNumber+ "]";
	}

}
