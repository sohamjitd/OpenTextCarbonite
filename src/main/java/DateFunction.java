import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFunction {

	public static void main(String[] args) throws ParseException {

		
		//Example
		System.out.println(dateconverter("06-02-2020","MM/DD/YYYY"));
		System.out.println(dateconverter("06-02-2020","DD/MM/YYYY"));
		System.out.println(dateconverter("06-02-2020","YYYY/MM/DD"));
		
	}
	
	public static String dateconverter(String input, String format) throws ParseException {
		
		SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
		String dateString = format1.format( new Date()   );
		Date   date       = format1.parse ( input ); 
		
		SimpleDateFormat DateFor = new SimpleDateFormat(format.replace("D", "d").replace("Y","y"));
		String stringDate = DateFor.format(date);
		
		return stringDate;
		
		
	}
	
	 

}
