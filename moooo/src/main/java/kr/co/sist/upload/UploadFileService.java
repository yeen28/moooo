package kr.co.sist.upload;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadFileService {
	/**
	 * upload������ �����ϴ� ������ ��� ��ȸ
	 * @return
	 */
	//public UploadFileVO searchFileList(int idx){
	public UploadFileVO searchFile(){
		UploadFileVO list=new UploadFileVO();
		
		//1. ���ϸ���Ʈ�� ������ ������ �����Ѵ�.
		File temp=new File("C:/Users/user/git/moooo/moooo/src/main/webapp/common/images/upload");
		//2. �ش� ������ ��� ����, ���丮�� ��´�.
		//File[] listFiles=temp.listFiles();
		
		UploadFileVO ufVO=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd a HH:mm:ss");
		//for( File file : listFiles ) {
		//3. ���Ͽ� ���ؼ��� ������ ��´�.
			if( temp.isFile() ) {
				ufVO=new UploadFileVO();
			//4. ���� ������ VO�� �Ҵ�
				ufVO.setFileName( temp.getName() );
				ufVO.setFileLen( temp.length() );
				ufVO.setLastModified(sdf.format(new Date(temp.lastModified())));
			}//end if
		//} //end for
		
		return list;
	}//searchFileList
}
