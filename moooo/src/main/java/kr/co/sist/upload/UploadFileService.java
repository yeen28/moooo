package kr.co.sist.upload;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadFileService {
	/**
	 * upload폴더에 존재하는 파일의 목록 조회
	 * @return
	 */
	//public UploadFileVO searchFileList(int idx){
	public UploadFileVO searchFile(){
		UploadFileVO list=new UploadFileVO();
		
		//1. 파일리스트를 가져올 파일을 생성한다.
		File temp=new File("C:/Users/user/git/moooo/moooo/src/main/webapp/common/images/upload");
		//2. 해당 폴더의 모든 파일, 디렉토리를 얻는다.
		//File[] listFiles=temp.listFiles();
		
		UploadFileVO ufVO=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd a HH:mm:ss");
		//for( File file : listFiles ) {
		//3. 파일에 대해서만 정보를 얻는다.
			if( temp.isFile() ) {
				ufVO=new UploadFileVO();
			//4. 얻은 정보를 VO에 할당
				ufVO.setFileName( temp.getName() );
				ufVO.setFileLen( temp.length() );
				ufVO.setLastModified(sdf.format(new Date(temp.lastModified())));
			}//end if
		//} //end for
		
		return list;
	}//searchFileList
}
