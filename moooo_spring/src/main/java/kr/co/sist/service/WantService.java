package kr.co.sist.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.WantBuyDAO;
import kr.co.sist.dao.WantSellDAO;
import kr.co.sist.vo.WantBuyVO;
import kr.co.sist.vo.WantSellVO;

@Component
public class WantService {

	@Autowired(required = false)
	private WantBuyDAO bDAO;
	@Autowired(required = false)
	private WantSellDAO sDAO;
	
	public List<WantBuyVO> searchBuyList(int category, String page){
		List<WantBuyVO> list=null;
		
		try {
			int nowPage=nowPage(page); //���� ������
			int numPerPage=numPerPage(); // �� ������ �� ������ �Խù� ��
			int totData=searchAllCnt(category); //��ü �Խù� ��
			int lastPage=totalPage(totData,numPerPage); //������ ��������ȣ
			int blockPage=blockPage();
			
			int start=((nowPage-1)/blockPage)*10+1;
			int end=endNum(start, blockPage);
			if( end > lastPage ){
				end=lastPage;
			}//end if
			
			int rowBegin=startNum(nowPage,blockPage);
			int rowEnd=nowPage*numPerPage;
			list=bDAO.selectBuyTitle(category, rowBegin, rowEnd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	} //searchNoticeList
	
	/**
	 * ���� �������� ���ڷ� ��ȯ
	 * @param page ���� ������
	 * @return ���ڷ� ��ȯ�� ���� ������
	 */
	public int nowPage(String page) {
		int nowPage=0;
		
		try {
			nowPage=Integer.parseInt(page);
		} catch(NumberFormatException nfe) {
			nowPage=1;
		} //end catch
		
		return nowPage;
	} //nowPage
	
	/**
	 * ��ü �Խù��� ��
	 * @return
	 */
	public int searchAllCnt(int category) {
		int cnt=0;
		
		try {
			cnt = bDAO.selectBuyCnt(category);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return cnt;
	}//searchAllCnt
	
	/**
	 * �� ȭ�鿡 ������ �Խù��� ��
	 * @return
	 */
	public int numPerPage() {
		return 10;
	} //pageScale
	
	/**
	 * �ϴܿ� ������ ���������̼� ���� ��
	 * @return
	 */
	public int blockPage() {
		return 10;
	} //blockPage
	
	/**
	 * ��� �Խù��� �����ֱ� ���� ������ ��
	 * @param totData ��ü �Խù� ��
	 * @param numPerPage �� ������ �� ������ �Խù� ��
	 * @return
	 */
	public int totalPage(int totData, int numPerPage) {
		int totalPage=0;
		
		totalPage=(int)Math.ceil( (double)totData/numPerPage );
		
		return totalPage;
	} //totalPage
	
	/**
	 * ���� ����(rowBegin)
	 * @param nowPage ���� ������ ��ȣ
	 * @param blockPage �ϴܿ� ������ ���������̼� ���� ��
	 * @return
	 */
	public int startNum(int nowPage, int blockPage) {
		int startNum=0;
		
		//startNum=(nowPage-1)*numPerPage+1;
		startNum=nowPage*blockPage-blockPage+1;
		
		return startNum;
	} //startNum
	
	/**
	 * �� ����(end)
	 * @param start ���۹�ȣ
	 * @param blockPage �ϴܿ� ������ ������ ����
	 * @return
	 */
	public int endNum(int start, int blockPage) {
		int end=0;
		end=start+blockPage-1;
		
		return end;
	} //endNum
	
	public WantBuyVO getWantBuyDetail(int buy_id) {
		WantBuyVO wVO=null;
		
		try {
			wVO=bDAO.selectBuy(buy_id);
		} catch(SQLException se) {
			se.printStackTrace();
		}
		
		return wVO;
	} //getWantBuyDetail
	
	public boolean addBuy() {
		boolean result=false;
		
		
		
		return result;
	}
	
//	public boolean updateBuy(WantBuyVO wVO) {
//		
//	}
//	
//	public boolean deleteBuy(int) {
//		
//	}
//
//	public List<WantSellVO> searchSellList(int,int,int) {
//		
//	}
//	
//	public WantSellVO getWantSellDetail(int) {
//		
//	}
//	
//	public boolean addSell(WantSellVO) {
//		
//	}
//	
//	public boolean updateSell(WantSellVO) {
//		
//	}
//	public boolean deleteSell(int) {
//		
//	}
//
//	public boolean isWriter(String,String){
//		
//	}
//
//	public boolean addInterest(int,String) {
//		
//	}
	
}
