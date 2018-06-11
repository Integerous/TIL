package kr.cashcloud.web;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import kr.cashcloud.model.ArtcCmnt;
import kr.cashcloud.model.Article;
import kr.cashcloud.model.ArticleKey;
import kr.cashcloud.service.BoardService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/article")
public class ArticleController {

	@Resource
	private BoardService boardService;
	
	
	/**
	 * 게시판 리스트 
	 * @param article
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String listArticle(Article article, Model model) {
		
		//각 게시물의 댓글 개수 리스트에 뿌리기위해
		int cmntSize = boardService.listArtcCmnt(article).size();
		article.setCmntCnt(cmntSize);
		
		List<Article> articles = boardService.listArticle(article);
		model.addAttribute("articles", articles);
	
		return "fp/article/list";
	}
	
	/**
	 * 신규 게시물 리스트(대시보드용)
	 * @param model
	 * @return
	 */
	@RequestMapping("/newList")
	public String listNewArticle(Model model) {
		
		List<Article> newArticles = boardService.listNewArticle();
		model.addAttribute("newArticles", newArticles);
		
		return "fp/article/newList";
	}
	
	
	/**
	 * 게시물 등록 폼 
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insertArticle() {
		
		return "fp/article/insert";
	}
	
	/**
	 * 게시물 등록
	 * @param article
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertArticle(Article article) {
		
		article.setBoardSeq(1);
		article.setMemSeq((long) 2);
		article.setUseCond("01");
		boardService.insertArticle(article);
		
		return "redirect:list";
	}
	
	/**
	 * 답변글 등록 폼 
	 * @return
	 */
	@RequestMapping(value="/insertCmnt", method = RequestMethod.GET)
	public String insertArtcCmntDWR(@RequestParam(value="boardSeq", defaultValue="1") int boardSeq
			, @RequestParam(value="artcSeq", defaultValue="1") long artcSeq
			, Article article
			, Model model) {
		
		Article artc = boardService.selectArticle(article);		
		model.addAttribute("toReply", artc);
		
		return "fp/article/insertCmnt";
	}
	/**
	 * 답변글 등록 
	 * @param cmnt
	 * @return
	 */
	@RequestMapping(value="/insertCmnt", method = RequestMethod.POST)
	public String insertArtcCmntDWR(ArtcCmnt cmnt
			, @ModelAttribute("toReply") Article toReply ) {
		
		Long artcSeq = toReply.getArtcSeq();
		System.out.println("---------------");
		System.out.println(artcSeq);
		
		
		int cmntSeq = (int) (new Date().getTime());
		
		cmnt.setBoardSeq(1);
		cmnt.setMemSeq((long) 2);
		cmnt.setCmntSeq(cmntSeq);
		cmnt.setAdminId("Ryan");
		cmnt.setUseCond("01"); //이게 지금 null로 들어가서 notnull 잠시 해제해둠
		
		boardService.insertArtcCmntDWR(cmnt);
		
		return "redirect:detail?boardSeq="+toReply.getBoardSeq()+"&artcSeq="+toReply.getArtcSeq();
	}
	

	
	/**
	 * 게시물 상세 
	 * @param boardSeq
	 * @param artcSeq
	 * @param article
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail")
	public String selectArticle(@RequestParam(value="boardSeq", defaultValue="1") int boardSeq
			, @RequestParam("artcSeq") long artcSeq
			, Article article
			, Model model) {
		
		Article artc = boardService.selectArticle(article);

		model.addAttribute("artc", artc);
		
		return "fp/article/detail";
	}
	
	
	
	/**
	 * 게시물 수정 폼 
	 * @param boardSeq
	 * @param artcSeq
	 * @param article
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/update", method = RequestMethod.GET)
	public String updateByPrimaryKey(@RequestParam(value="boardSeq", defaultValue="1") int boardSeq
			, @RequestParam(value="artcSeq", defaultValue="1") long artcSeq
			, Article article
			, Model model) {
		
		Article artc = boardService.selectArticle(article);		
		model.addAttribute("toUpdate", artc);
		
		return "fp/article/update";
	}
	
	
	/**
	 * 게시물 수정 
	 * @param toUpdate
	 * @return
	 */
	@RequestMapping(value = "/update2")
	public String updatByPrimaryKey(@ModelAttribute("toUpdate") Article toUpdate
			, RedirectAttributes rttr) {
		
		//수정 폼으로부터 artcSeq 값 들어오는지 테스트
		Long temp = toUpdate.getArtcSeq();
		System.out.println("-------------------------------");
		System.out.println(temp);
		
		//최초 등록시 boardSeq값 넣었지만 update는 artcSeq와 boardSeq를 참조해서 하므로 boardSeq 강제로 추가ㅋ 
		toUpdate.setBoardSeq(1);
		
		boardService.updateByPrimaryKey(toUpdate);
		
		return "redirect:detail?boardSeq=" + toUpdate.getBoardSeq() + "&artcSeq=" + toUpdate.getArtcSeq();
	}
	
	
	/**
	 * 게시물 삭제 
	 * @param boardSeq
	 * @param artcSeq
	 * @return
	 */
	@RequestMapping(value= "/delete")
	public String deleteByPrimaryKey(@RequestParam(value="boardSeq", defaultValue="1") int boardSeq
			, @RequestParam(value="artcSeq", defaultValue="1") long artcSeq) {

		ArticleKey key = new ArticleKey();
		key.setArtcSeq(artcSeq);
		key.setBoardSeq(boardSeq);
		
		boardService.deleteByPrimaryKey(key);
		
		return "redirect:list";
	}
	
	

}




