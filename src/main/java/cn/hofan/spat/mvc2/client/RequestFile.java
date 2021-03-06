package cn.hofan.spat.mvc2.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * A representation of an uploaded file received in a multipart request.
 *
 * <p>The file contents are either stored in memory or temporarily on disk.
 * In either case, the user is responsible for copying file contents to a
 * session-level or persistent store as and if desired. The temporary storages
 * will be cleared at the end of request processing.
 *
 * @author Juergen Hoeller
 * @author Trevor D. Cook
 * @since 29.09.2003
 * @see org.springframework.web.multipart.MultipartHttpServletRequest
 * @see org.springframework.web.multipart.MultipartResolver
 */
public class RequestFile {
	
	MultipartFile mFile;
	
	RequestFile(MultipartFile mFile) {
		super();
		this.mFile = mFile;
	}

	/**
	 * Return the name of the parameter in the multipart form.
	 * @return the name of the parameter (never <code>null</code> or empty)
	 */
	public String getName(){
		return mFile.getName();
	}

	/**
	 * Return the original filename in the client's filesystem.
	 * <p>This may contain path information depending on the browser used,
	 * but it typically will not with any other than Opera.
	 * @return the original filename, or the empty String if no file
	 * has been chosen in the multipart form
	 */
	public String getOriginalFilename(){
		return mFile.getOriginalFilename();
	}


	/**
	 * Return the content type of the file.
	 * @return the content type, or <code>null</code> if not defined
	 * (or no file has been chosen in the multipart form)
	 */
	public String getContentType(){
		return mFile.getContentType();
	}

	/**
	 * Return whether the uploaded file is empty, that is, either no file has
	 * been chosen in the multipart form or the chosen file has no content.
	 */
	public boolean isEmpty(){
		return mFile.isEmpty();
	}

	/**
	 * Return the size of the file in bytes.
	 * @return the size of the file, or 0 if empty
	 */
	public long getSize(){
		return mFile.getSize();
	}

	/**
	 * Return the contents of the file as an array of bytes.
	 * @return the contents of the file as bytes, or an empty byte array if empty
	 * @throws IOException in case of access errors (if the temporary store fails)
	 */
	public byte[] getBytes() throws IOException{
		return mFile.getBytes();
	}

	/**
	 * Return an InputStream to read the contents of the file from.
	 * The user is responsible for closing the stream.
	 * @return the contents of the file as stream, or an empty stream if empty
	 * @throws IOException in case of access errors (if the temporary store fails)
	 */
	public InputStream getInputStream() throws IOException{
		return mFile.getInputStream();
	}

	/**
	 * Transfer the received file to the given destination file.
	 * <p>This may either move the file in the filesystem, copy the file in the
	 * filesystem, or save memory-held contents to the destination file.
	 * If the destination file already exists, it will be deleted first.
	 * <p>If the file has been moved in the filesystem, this operation cannot
	 * be invoked again. Therefore, call this method just once to be able to
	 * work with any storage mechanism.
	 * @param dest the destination file
	 * @throws IOException in case of reading or writing errors
	 * @throws IllegalStateException if the file has already been moved
	 * in the filesystem and is not available anymore for another transfer
	 */
	public void transferTo(File dest) throws IOException, IllegalStateException{
		mFile.transferTo(dest);
	}

}
