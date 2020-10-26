package com.jersey.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

@Path("download")
public class DownloadFileService {
	
	public static final int BUFFER_SIZE = 8192;

	@GET
	@Path("/mp4")
	public Response downloadFile() {

		StreamingOutput fileStream = new StreamingOutput() {

			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {

				byte[] buffer = new byte[BUFFER_SIZE];
				try (
			    		FileInputStream fis = new FileInputStream(new File("C:\\Temp\\ThepowerofthenewMicrosoftProjectandMicrosoft365.mp4"));
			        )
				{
					int read = 0;
					while ((read = fis.read(buffer)) > 0) {
						output.write(buffer, 0, read);
					}
					output.flush();
				} catch (Exception e) {
					throw new WebApplicationException("File Not Found!");
				}

			}
		};
		return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
				.header("content-disposition", "attachment; filename = ThepowerofthenewMicrosoftProjectandMicrosoft365.mp4").build();
	}
}
