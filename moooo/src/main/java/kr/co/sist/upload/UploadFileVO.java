package kr.co.sist.upload;

public class UploadFileVO {
	private String fileName, lastModified;
	private long fileLen;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	
	public long getFileLen() {
		return fileLen;
	}
	
	public void setFileLen(long fileLen) {
		this.fileLen = fileLen;
	}
}
