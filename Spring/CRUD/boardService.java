package kr.cashcloud.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.Count;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gargoylesoftware.htmlunit.javascript.host.Console;

import kr.cashcloud.common.config.CommonConstants;
import kr.cashcloud.common.model.ResultModel;
import kr.cashcloud.common.service.BaseService;
import kr.cashcloud.mapper.ArtcCmntMapper;
import kr.cashcloud.mapper.ArtcMemoMapper;
import kr.cashcloud.mapper.ArticleMapper;
import kr.cashcloud.mapper.BoardMapper;
import kr.cashcloud.mapper.CommonMapper;
import kr.cashcloud.model.Admin;
import kr.cashcloud.model.ArtcCmnt;
import kr.cashcloud.model.ArtcMemo;
import kr.cashcloud.model.Article;
import kr.cashcloud.model.ArticleKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RemoteProxy(name = "boardService")
@Service
public class BoardService extends BaseService {
	@Resource
	private BoardMapper boardMapper;
	@Resource
	private ArticleMapper articleMapper;
	@Resource
	private ArtcCmntMapper artcCmntMapper;
	@Resource
	private ArtcMemoMapper artcMemoMapper;
	@Resource
	private CommonMapper commonMapper;
	
	@Value("${service.mode}")
	private String serviceMode;

	@Value("${web_root_path}")
	private String serverRootPath;
	
	/**
	 * 게시물 목록
	 * @param article
	 * @return
	 */
	public List<Article> listArticle(Article article) {
		
		return articleMapper.listArticle(article);
	}
	
	/**
	 * 신규 게시물 목록(대시보드용)
	 * @return
	 */
	public List<Article> listNewArticle() {
		return articleMapper.listNew();
	}
	
	
	/**
	 * 게시물/문의사항 등록
	 * @param article
	 * @return
	 */
	public int insertArticle(Article article) {
		Long artcSeq = commonMapper.selectNextval("sq_article");
		article.setArtcSeq(artcSeq);
		// 첨부파일 업로드 처리
		CommonsMultipartFile attach = article.getAttach();
		if (attach != null && !attach.isEmpty()) {
			String path = File.separator + "upload" + File.separator + "article_" + serviceMode + File.separator
					+ artcSeq + File.separator;
			// 디렉토리 생성
			File dir = new File(serverRootPath + path);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String fileName = attach.getOriginalFilename();
			String ext = "";
			if (fileName.indexOf(".") >= 0) {
				ext = fileName.substring(fileName.lastIndexOf("."));
			}
			fileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;

			try {
				attach.transferTo(new File(serverRootPath + path + fileName));
				article.setFileName(path + fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return articleMapper.insert(article);
	}
	
	
	
	/**
	 * 게시물 상세
	 * @param article
	 * @return
	 */
	public Article selectArticle(Article article) {
		article = articleMapper.selectArticle(article);
		if(article != null) {
			List<ArtcCmnt> cmntList = artcCmntMapper.listArtcCmnt(article);
				article.setCmntList(cmntList);
			
			int cmntCnt = cmntList.size();			
				article.setCmntCnt(cmntCnt);
		}
		
		return article;
	}
	
	public List<ArtcCmnt> listArtcCmnt(Article article) {
		return artcCmntMapper.listArtcCmnt(article);
	}
	
	
	/**
	 * 게시물 삭제
	 * @param artcSeq
	 * @return
	 */
	public long deleteByPrimaryKey(ArticleKey artcSeq) {
		return articleMapper.deleteByPrimaryKey(artcSeq);
	} 
	
	/**
	 * 게시물 수정 
	 * @param article
	 * @return
	 */
	public int updateByPrimaryKey(Article article) {
		return articleMapper.updateByPrimaryKey(article);
		
	}
	
	
	/**
	 * 답변글 등록
	 * @param cmnt
	 * @return
	 */
	public ResultModel insertArtcCmntDWR(ArtcCmnt cmnt) {
		ResultModel result = new ResultModel();
		
		/*Admin admin = getLoginAdmin();
		if(admin == null) {
			result.setCode(CommonConstants.CD_NOT_LOGIN);
			result.setMsg(CommonConstants.MSG_NOT_LOGIN);
			return result;
		}
		cmnt.setAdminId(admin.getAdminId());*/
		cmnt.setUseCond("01");
		int row = artcCmntMapper.insert(cmnt); // 왜 row에 담았는지
		result.setCode(CommonConstants.CD_OK);
		result.setMsg(CommonConstants.MSG_OK);
		return result;
		
	}
	

	
	
	/**
	 * 게시글 관리자 메모목록
	 * @param memo
	 * @return
	 */
	public List<ArtcMemo> listArtcMemo(Article article) {
		return artcMemoMapper.listArtcMemo(article);
	}
	
	/**
	 * 게시글 관리자 메모 삭제(무효처리)
	 * @param memo
	 * @return
	 */
//	public ResultModel deleteArtcMemo(ArtcMemo memo) {
//		memo.setUseCond("00");
//		ResultModel result = new ResultModel();
//		result.setCode(CommonConstants.CD_OK);
//		result.setMsg(CommonConstants.MSG_OK);
//		
//		artcMemoMapper.update(memo);
//		
//		return result;
//	}
	
	/**
	 * 관리자 메모 추가
	 * @param memo
	 * @return
	 */
	public ResultModel insertArtcMemoDWR(ArtcMemo memo) {
		ResultModel result = new ResultModel();
		result.setCode(CommonConstants.CD_OK);
		result.setMsg(CommonConstants.MSG_OK);
		Admin admin = getLoginAdmin();
		if(admin == null) {
			result.setCode(CommonConstants.CD_NOT_LOGIN);
			result.setMsg(CommonConstants.MSG_NOT_LOGIN);
			return result;
		}
		memo.setAdminId(admin.getAdminId());
		artcMemoMapper.insert(memo);
		
		return result;
	}


	


}
