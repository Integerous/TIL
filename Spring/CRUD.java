package kr.cashcloud.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import kr.cashcloud.model.Article;
import kr.cashcloud.model.ArticleKey;
import kr.cashcloud.model.Board;
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
	 * @param board
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String listArticle(Article article, Board board, Model model) {
		
		List<Article> articles = boardService.listArticle(article);
		model.addAttribute("articles", articles);
		model.addAttribute("1", board);
		
		return "fp/article/list";
	}
	
	
	/**
	 * 게시물 등록 
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insertArticle() {
		
		return "fp/article/insert";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public String insertArticle(Article article) {
		
		article.setBoardSeq(1);
		article.setMemSeq((long) 2);
		article.setUseCond("01");
		boardService.insertArticle(article);
		
		return "redirect:list";
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
		
		model.addAttribute("article", artc);
		
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
	public String updatByPrimaryKey(@ModelAttribute("toUpdate") Article toUpdate) {
		
		//update에는 artcSeq와 boardSeq가 필요하므로
		toUpdate.setBoardSeq(1);
		boardService.updateByPrimaryKey(toUpdate);
		
		return "redirect:list";
	}
	
	
	/**
	 * 게시물 삭제 
	 * @param boardSeq
	 * @param artcSeq
	 * @param article
	 * @param model
	 * @param request
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




