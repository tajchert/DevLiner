import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ExporterToJS {
	//This class will wrap up content into file with JS chart, using Highchart
	//Will take list of project and print their days and lines at that time
	final static String dirStart ="D://Dropbox//Documents//Android//testingDevLiner";
	ArrayList<Project> projectsList = new ArrayList<Project>();		//List of project to print
	String fileOutName;		//By default it would be today date but in future overriding same file would be possible
	String fileBeginName="chartBegin.part";
	String fileEndName="chartEnd.part";
	String tmpHTML;
	String Output;
	
	public ExporterToJS(ArrayList<Project> inputList){
		this.projectsList=inputList;
		
	}
	
	public void createFile(){
		//Reading beginning of file
		try {
			tmpHTML=readFile(fileBeginName);
		} catch (IOException e) {
			System.out.println("Problem with fileBeginName read");
			e.printStackTrace();
		}
		//END
		//Adding data for each project
		Output =tmpHTML;
		int c = 0;
		while(c < projectsList.size()){
			//name: 'Winter 2007-2008',
			if(c>0){
				Output +=",";
			}
			Output += "{\nname: '"+projectsList.get(c).name +"',\ndata: [";
			Output +=projectsList.get(c).getChartData() +"]\n}";
			c++;
		}
		//END
		// Reading end of file
		try {
			tmpHTML = readFile(fileEndName);
		} catch (IOException e) {
			System.out.println("Problem with fileBeginName read");
			e.printStackTrace();
		}
		// END
		Output +=tmpHTML;
		saveFile();
		//Adding dates all between minimum and today
		//adding middle code
		//adding series - one for each project
		
	}
	
	
	
	
	
	public String show(){//For testing
		return tmpHTML;//TODO delete it
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
			out.write(Output);
	        out.close();
		} catch (IOException e) {
			System.out.println("Saving failed!");
			e.printStackTrace();
		}
	        
	}
}
