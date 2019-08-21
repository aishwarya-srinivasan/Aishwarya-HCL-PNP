package testscripts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class API_Execution {

	static StringBuffer videoName = new StringBuffer();
	static StringBuffer uploadStatus = new StringBuffer();
	static StringBuffer uploadVerify = new StringBuffer();

	public static void executeApI(String cmd, StringBuffer output) throws IOException, InterruptedException {
		Process proc = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", cmd });
		proc.waitFor();

		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String line = "";

		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");

		}

		System.out.println(output);
	}

	public static void writeToFile(String File, StringBuffer sbf) throws IOException {
		BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(File)));

		// write contents of StringBuffer to a file
		bwr.write(sbf.toString());

		// flush the stream
		bwr.flush();

		// close the stream
		bwr.close();

		System.out.println("Content of StringBuffer written to File.");
	}


	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		// API to get the video name
		String cmdToGetVideo = "curl -iX GET http://54.169.34.162:5252/video";

		// Step 5
		// Execution of get request to get the video

		executeApI(cmdToGetVideo, videoName);

		// Step 12
		// upload the json file

		String cmdToUploadJSON = "curl -X POST http://54.169.34.162:5252/upload -H 'content-type: multipart/form-data' -F 'file=@output/videos.json";
		executeApI(cmdToUploadJSON, uploadStatus);

		// Step 13
		// validate the uploaded file
		String checkUploadStatus = "curl -iX GET http://54.169.34.162:5252/result/" + uploadStatus;
		executeApI(cmdToUploadJSON, uploadVerify);
		//need to check the content of the upload
		
		//step 14
		//Write the output to rsults.json file
		writeToFile("output/results.json", uploadVerify);
		

	}

}
