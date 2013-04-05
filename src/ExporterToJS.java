import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;


public class ExporterToJS {
	//This class will wrap up content into file with JS chart, using Highchart
	//Will take list of project and print their days and lines at that time
	final static String dirStart ="D://Dropbox//Documents//Android//testingDevLiner";
	ArrayList<Project> projectsList = new ArrayList<Project>();		//List of project to print
	String fileOutName;		//By default it would be today date but in future overriding same file would be possible
	String fileBeginName="chartBegin.part";
	String beginHTML;
	String Output;
	
	public ExporterToJS(ArrayList<Project> inputList){
		this.projectsList=inputList;
		
	}
	
	private void createFile(){
		//Reading beginning of file
		try {
			beginHTML=readFile(fileBeginName);
		} catch (IOException e) {
			System.out.println("Problem with fileBeginName read");
			e.printStackTrace();
		}
		//END
		Date oldesOne = new Date();
		int c = 0;
		while(c < projectsList.size()){
			if(projectsList.get(c).getCreationDate().before(oldesOne)){
				oldesOne = projectsList.get(c).getCreationDate();
			}
			c++;
		}
		oldesOne.setSeconds(0);
		//Adding dates all between minimum and today
		//adding middle code
		//adding series - one for each project
		
	}
	
	
	
	
	
	public String show(){//For testing
		return beginHTML;//TODO delete it
	}
	
	
	private String readFile(String path) throws IOException {
		  FileInputStream stream = new FileInputStream(new File(path));
		  try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    /* Instead of using default, pass in a decoder. */
		    return Charset.defaultCharset().decode(bb).toString();
		  }
		  finally {
		    stream.close();
		  }
		}
	
	private void saveFile(){
		 BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter("out.html"));
			out.write("aString\nthis is a\nttest");
	        out.close();
		} catch (IOException e) {
			System.out.println("Saving failed!");
			e.printStackTrace();
		}
	        
	}
}
