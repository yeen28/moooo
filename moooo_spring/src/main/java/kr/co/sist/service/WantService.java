package kr.co.sist.service;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import kr.co.sist.dao.ReportDAO;
import kr.co.sist.dao.WantBuyDAO;
import kr.co.sist.dao.WantSellDAO;
import kr.co.sist.vo.PaginationVO;
import kr.co.sist.vo.ReportReasonVO;
import kr.co.sist.vo.ReportVO;
import kr.co.sist.vo.WantBuyVO;
import kr.co.sist.vo.WantSellVO;

@Component
public class WantService {

	@Autowired(required = false)
	private WantBuyDAO bDAO;
	@Autowired(required = false)
	private WantSellDAO sDAO;
	@Autowired(required = false)
	private ReportDAO rDAO;
	
	/**
	 * �� ��� ���
	 * @param category
	 * @param page
	 * @return
	 */
	public List<WantBuyVO> searchBuyList(int category, String page){
		List<WantBuyVO> list=null;
		
		PaginationVO pVO=getPagination(String.valueOf(category), page);
		
		DecimalFormat df=new DecimalFormat("#,###,###");
		
		try {
			list=bDAO.selectBuyTitle(category, pVO.getRowBegin(), pVO.getRowEnd());
			for( WantBuyVO wVO : list ) {
				wVO.setStringPrice(df.format(wVO.getPrice()));
			} //end for
			
		} catch (SQLException e) {
			e.printStackTrace();
		} //end catch
		
		return list;
	} //searchNoticeList
	
	/**
	 * ���������̼� ���
	 * @param category
	 * @param page
	 * @return
	 */
	public PaginationVO getPagination(String paramCategory, String page) {
		
		
		PaginationVO pVO=new PaginationVO();
		
		int category=0;
		try{
			category=Integer.parseInt(paramCategory);
		} catch (NumberFormatException nfe){
			category=0;
		}//end catch
		
		int numPerPage=numPerPage(); //�� ������ �� ������ �Խù��� ��
		int totData=searchAllCnt(category); //��ü �Խù� ��
		int lastPage=totalPage(totData,numPerPage); //������ ��������ȣ
//		int lastPage=totData/numPerPage; //������ ��������ȣ
//		if(totData % numPerPage > 0){
//			++lastPage;
//		}
		int blockPage=blockPage();
		int nowPage=1; //���� ������
		try{
			nowPage=Integer.parseInt(page);
		} catch (NumberFormatException nfe){
			nowPage=1;
		}
		int start=((nowPage-1)/blockPage)*10+1;
		int end=start+blockPage-1;
//		int end=endNum(start, blockPage);
		if( end > lastPage ){
			end=lastPage;
		} //end if

		int rowBegin=(nowPage-1)*numPerPage+1;
//		int rowBegin=startNum(nowPage,blockPage);
		int rowEnd=nowPage*numPerPage;
		
		pVO.setNowPage(nowPage);
		pVO.setStart(start);
		pVO.setEnd(end);
		pVO.setRowBegin(rowBegin);
		pVO.setRowEnd(rowEnd);
		pVO.setLastPage(lastPage);
		
		return pVO;
	} //getPagination
	
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
			cnt=0;
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
		int end=start+blockPage-1;
		return end;
	} //endNum
	
	/**
	 * ���;�� �������� ���� ���
	 * @param buy_id
	 * @return WantBuyVO
	 */
	public WantBuyVO getWantBuyDetail(int buy_id) {
		WantBuyVO wVO=null;
		
		DecimalFormat df=new DecimalFormat("#,###,###");
		
		try {
			wVO=bDAO.selectBuy(buy_id);
			wVO.setStringPrice(df.format(wVO.getPrice()));
		} catch(SQLException se) {
			se.printStackTrace();
		}
		
		return wVO;
	} //getWantBuyDetail
	
	/**
	 * �� �߰� ó��
	 * @throws DataAccessException
	 */
	public void addBuy(WantBuyVO wv) throws DataAccessException {
		bDAO.insertBuy(wv);
	} //addBuy
//	public boolean addBuy(String ipAddr, String sessionId, WantBuyVO wv) throws DataAccessException {
//		boolean result=false;
//		
//		bDAO.insertBuy(wv);
//		
//		return result;
//	} //addBuy
	
	/**
	 * �� ���� ó��
	 * @param wVO
	 * @return true ���� | false ����
	 * @throws DataAccessException
	 */
	public boolean updateBuy(WantBuyVO wVO) throws DataAccessException {
		int cnt=bDAO.updateBuy(wVO);
		if(cnt == 0) {
			return false;
		} else {
			return true;
		} //end else
	} //updateBuy
	
	/**
	 * '���;��' �� ����
	 * @param buy_id
	 * @return true ���� | false ����
	 * @throws SQLException
	 */
	public boolean deleteBuy(int buy_id, String user_id) throws SQLException {
		if( bDAO.deleteBuy(buy_id, user_id) != 0) { //���� ����
			return true;
		} else { //����
			return false;
		} //end else
	} //deleteBuy
	
	/**
	 * �� �˻� ó�� ��������
	 * @param searchWord
	 * @return WantBuyVO List
	 * @throws SQLException
	 */
	public List<WantBuyVO> searchWordBuyList(String searchWord) throws SQLException {
		DecimalFormat df=new DecimalFormat("#,###,###");
		List<WantBuyVO> list=bDAO.selectSearch(searchWord);
		for( WantBuyVO wVO : list ) {
			wVO.setStringPrice(df.format(wVO.getPrice()));
		} //end for
		return list;
	} //searchWordBuyList

	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * '�Ⱦƿ�' �� ��� ���
	 * @param category
	 * @param page
	 * @return
	 */
	public List<WantSellVO> searchSellList(int category, String page) {
		List<WantSellVO> list=null;
		
		PaginationVO pVO=getPagination(String.valueOf(category), page);
		
		DecimalFormat df=new DecimalFormat("#,###,###");
		
		try {
			list=sDAO.selectSellTitle(category, pVO.getRowBegin(), pVO.getRowEnd());
			for( WantSellVO sVO : list ) {
				sVO.setString_price(df.format(sVO.getPrice()));
			} //end for
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * �Ⱦƿ� �������� ���� ���
	 * @param buy_id
	 * @return WantBuyVO
	 */
	public WantSellVO getWantSellDetail(int sell_id) {
		WantSellVO wVO=null;
		
		DecimalFormat df=new DecimalFormat("#,###,###");
		
		try {
			wVO=sDAO.selectSell(sell_id);
			wVO.setString_price(df.format(wVO.getPrice()));
		} catch(SQLException se) {
			se.printStackTrace();
		}
		
		return wVO;
	} //getWantSellDetail
	
	/**
	 * �� �߰� ó��
	 * @throws DataAccessException
	 */
	public void addSell(WantSellVO wv) throws DataAccessException {
		sDAO.insertSell(wv);
	} //addSell
	
	/**
	 * �� ���� ó��
	 * @param wVO
	 * @return true ���� | false ����
	 * @throws DataAccessException
	 */
	public boolean updateSell(WantSellVO wVO) throws DataAccessException {
		int cnt=sDAO.updateSell(wVO);
		if(cnt == 0) {
			return false;
		} else {
			return true;
		} //end else
	} //updateSell
	
	/**
	 * '�Ⱦƿ�' �� ����
	 * @param buy_id
	 * @return true ���� | false ����
	 * @throws SQLException
	 */
	public boolean deleteSell(int sell_id, String user_id) throws SQLException {
		if( sDAO.deleteSell(sell_id, user_id) != 0) { //���� ����
			return true;
		} else { //����
			return false;
		} //end else
	} //deleteSell
	
	/**
	 * �Ű����� ���
	 * @return ReportReasonVO List
	 * @throws SQLException
	 */
	public List<ReportReasonVO> getReportReasonList() throws SQLException{
		return rDAO.selectReport();
	} //getReportReasonList
	
	/**
	 * ������ ���� ����ڸ� �Ű��� �̷��� �ִ��� Ȯ��
	 * @param rVO
	 * @return true ���� | false ����
	 * @throws SQLException
	 */
	public boolean chkBeforeReport(ReportVO rVO) throws SQLException {
		if( 	rDAO.selectBeforeReport(rVO) != "" ) { // �Ű��� �� ����
			return true;
		} else { //����
			return false;
		} //end else
	} //chkBeforeReport
	
	/**
	 * �Ű�ó�� ��������
	 * @return true ���� | false ����
	 * @throws SQLException 
	 */
	public boolean reportProc(ReportVO rVO) throws SQLException {
		try {
			rDAO.insertReport(rVO);
			if( 	rDAO.updateReport(rVO.getReported_user_id()) != 0) {
				return true;
			} //end if
			return false;
		} catch(DataAccessException dae) { 
			return false;
		} //end catch
	} //reportProc
	
//	public boolean isWriter(String,String){
//		
//	}
//
	
	/**
	 * ���ɱ۷� �����Ǿ� �ִ��� Ȯ��
	 * @param sell_id
	 * @param user_id
	 * @return
	 * @throws DataAccessException 
	 */
	public boolean isInterest(int sell_id ,String user_id) {
		try {
			if( sDAO.selectIsInterest(sell_id, user_id) != 0 ) {
				return true;
			} else {
				return false;
			} //end else
		} catch(DataAccessException dae) {
			return false;
		} //end catch
	} //isInterest
	
	/**
	 * ���ɱ۷� �߰�/����
	 * @param sell_id
	 * @param user_id
	 * @param control
	 * @return
	 * @throws DataAccessException 
	 */
	public void updateInterest(int sell_id ,String user_id, String control) throws DataAccessException {
		if( "add".equals(control) ) { //���ɱ۷� �߰�
			sDAO.insertInterest(user_id, sell_id);
		} //end if
		
		if( "remove".equals(control) ) { //���ɱۿ��� ����
			sDAO.deleteInterest(user_id, sell_id);
		} //end if
	} //updateInterest
	
	/**
	 * �� �˻� ó�� ��������
	 * @param searchWord
	 * @return WantBuyVO List
	 * @throws SQLException
	 */
	public List<WantSellVO> searchWordSellList(String searchWord) throws SQLException {
		DecimalFormat df=new DecimalFormat("#,###,###");
		List<WantSellVO> list=sDAO.selectSearch(searchWord);
		for( WantSellVO wVO : list ) {
			wVO.setString_price(df.format(wVO.getPrice()));
		} //end for
		return list;
	} //searchWordBuyList
	
} //class
