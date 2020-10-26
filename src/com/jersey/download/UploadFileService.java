package com.jersey.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("upload")
public class UploadFileService {
	
	public static final int BUFFER_SIZE = 8192;

	@POST
	@Path("/mp4")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

			String uploadedFileLocation = "C:\\Temp\\" + fileDetail.getFileName() + ".uploaded";

			try (
					FileOutputStream fos = new FileOutputStream(new File(uploadedFileLocation));
		        )
			{
				int read = 0;
				byte[] buffer = new byte[BUFFER_SIZE];

				while ((read = uploadedInputStream.read(buffer)) != -1) {
					fos.write(buffer, 0, read);
				}
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String output = "File uploaded to : " + uploadedFileLocation;

			return Response.status(200).entity(output).build();

		}

}
