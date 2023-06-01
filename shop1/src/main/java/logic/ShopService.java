package logic;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import dao.BoardDao;
import dao.ItemDao;
import dao.SaleDao;
import dao.SaleItemDao;
import dao.UserDao;

@Service //@Component + Service(controller 기능과 dao 기능의 중간 역할기능)
public class ShopService {
	@Autowired //객체 주입
	private ItemDao itemDao;
	@Autowired //객체 주입
	private UserDao userDao;
	@Autowired
	private SaleDao saleDao;
	@Autowired
	private SaleItemDao saleItemDao;
	@Autowired
	private BoardDao boardDao;
	
	public List<Item> itemList(){
		return itemDao.list();
	}

	public Item getItem(Integer id) {
		
		return itemDao.getItem(id);
	}

	public void itemCreate(Item item, HttpServletRequest request) {
		if(item.getPicture() != null && !item.getPicture().isEmpty()) {
			//업로드해야하는 파일의 내용이 있는 경우
			String path = request.getServletContext().getRealPath("/") + "img/";
			uploadFileCreate(item.getPicture(),path);
			//업로드된 파일이름
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		//db에 내용 저장
		int maxid = itemDao.maxId(); //item 테이블에 저장된 최대 id값
		item.setId(maxid+1);
		itemDao.insert(item); //db에 데이터 추가
	}

	public void uploadFileCreate(MultipartFile file, String path) {
		//file : 파일의 내용
		//path : 업로드 할 폴더 
		String orgFile = file.getOriginalFilename();  //파일이름
		File f = new File(path);
		if(!f.exists()) f.mkdirs();
		try {
			//transferTo : file에 저장된 내용을 파일로 저장
			file.transferTo(new File(path+orgFile));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void itemUpdate(Item item, HttpServletRequest request) {
		if(item.getPicture() != null && !item.getPicture().isEmpty()) {
			String path = request.getServletContext().getRealPath("/") + "img/";
			uploadFileCreate(item.getPicture(),path);
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		
		itemDao.update(item);
	}

	public void itemDelete(Integer id) {
		itemDao.delete(id);
	}

	public void userInsert( User user) {
		userDao.insert(user);
	}

	public User selectUserOne(String userid) {
		
		return userDao.selectOne(userid);
	}
	
	/*
	 * 1.로그인정보, 장바구니정보 = sale, saleitem 테이블의 데이터 저장
	 * 2.결과는 Sale 객체에 저장
	 * 	 - sale 테이블 저장 : saleid값 구하기. 최대값 +1
	 * 	 - saleitem 테이블 저장 : Cart 데이터를 이용하여 저장
	 */
	
	public Sale checkend(User loginUser, Cart cart) {
		int maxsaleid = saleDao.getMaxSaleId(); //saleid 최대값 조회
		Sale sale = new Sale();
		sale.setSaleid(maxsaleid+1);
		sale.setUser(loginUser);
		sale.setUserid(loginUser.getUserid());
		saleDao.insert(sale); //sale 테이블에 데이터 추가
		int seq = 0;
		for(ItemSet is : cart.getItemSetList()) {
			SaleItem saleItem = new SaleItem(sale.getSaleid(),++seq,is);
			sale.getItemList().add(saleItem);
			saleItemDao.insert(saleItem); //saleitem 테이블에 데이터 추가
		}
		return sale; //주문정보, 주문상품정보, 상품정보, 사용자정보
	}

	public List<Sale> salelist(String userid) {
		List<Sale> list = saleDao.list(userid);//id 사용자가 주문한 정보목록 
		for(Sale sa : list) {
			//saleitemlist : 한개의 주문에 해당하는 주문상품 목록
			List<SaleItem> saleitemlist =saleItemDao.list(sa.getSaleid());
			for(SaleItem si : saleitemlist) {
				Item item = itemDao.getItem(si.getItemid()); //상품정보
				si.setItem(item);
			}
			sa.setItemList(saleitemlist);
		}
		return list;
	}

	public void userUpdate(@Valid User user) {
		
		userDao.update(user);
	}

	public void userDelete(String userid) {
		
		userDao.delete(userid);
	}

	public void upDatePs(String password, String userid) {
		
		userDao.update(password,userid);
	}

	public List<User> userlist() {
		
		return userDao.userlist(); //회원목록
	}

	public List<User> getUserList(String[] idchks) {
		
		return userDao.list(idchks);
	}

	public String getSearch(User user) {
		
		return userDao.search(user);
	}

	public void boardWrite( Board board, HttpServletRequest request) {
		int maxnum = boardDao.maxNum(); //등록된 게시물의 최대 num값 리턴
		board.setNum(++maxnum);
		board.setGrp(maxnum);
		if(board.getFile1() != null && !board.getFile1().isEmpty()) {
			String path = request.getServletContext().getRealPath("/") + "board/file/";
			this.uploadFileCreate(board.getFile1(), path);
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		boardDao.insert(board);
	}

	public int boardcount(String boardid,String searchtype, String searchcontent) {
		return boardDao.count(boardid,searchtype,searchcontent);
	}

	public List<Board> boardlist(Integer pageNum, int limit, String boardid,  String searchtype,String searchcontent) {
		return boardDao.list(pageNum,limit,boardid,searchtype,searchcontent);
	}

	public Board getBoard(Integer num) {
		
		return boardDao.selectOne(num);
	}
	public void addReadcnt(Integer num) {
		
		boardDao.addReadcnt(num);
	}
	@Transactional // 트렌젝션 처리함. 업무를 원자화(all or nothing)
	public void boardReply(Board board) {
//		int maxnum = boardDao.maxNum();
//		Board b = boardDao.selectOne(num);
//		boardDao.grpStepAdd(b.getGrp(),b.getGrpstep());
//		board.setNum(maxnum+1);
//		board.setGrp(b.getGrp());
//		board.setGrplevel(b.getGrplevel()+1);
//		board.setGrpstep(b.getGrpstep()+1);
//		boardDao.insert(board);
		boardDao.updateGrpStep(board);  //이미 등록된 grpstep값 1씩 증가
		int max = boardDao.maxNum();    //최대 num 조회
		board.setNum(++max);  //원글의 num => 답변글의 num 값으로 변경
		                      //원글의 grp => 답변글의 grp 값을 동일. 설정 필요 없음
                              //원글의 boardid => 답변글의 boardid 값을 동일. 설정 필요 없음
		board.setGrplevel(board.getGrplevel() + 1); //원글의 grplevel => +1 답변글의 grplevel 설정
		board.setGrpstep(board.getGrpstep() + 1);   //원글의 grpstep => +1 답변글의 grpstep 설정
		boardDao.insert(board);
	}

	public void boardUpdate(@Valid Board board, HttpServletRequest request) {
		if(board.getFile1() != null && !board.getFile1().isEmpty()) {
			String path = request.getServletContext().getRealPath("/") + "board/file/";
			this.uploadFileCreate(board.getFile1(), path); //파일 업로드 : board.getFile1()의 내용을 파일로 생성
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		boardDao.update(board);
	}

	public void boardDelete(Integer num) {
		boardDao.delete(num);
	}
}
