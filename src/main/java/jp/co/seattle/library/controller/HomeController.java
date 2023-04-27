package jp.co.seattle.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class HomeController {
	final static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String transitionHome(Model model) {
		//書籍の一覧情報を取得（タスク３）
		List<BookInfo> bookList = booksService.getBookList();
		if (!(bookList.isEmpty())) {
			model.addAttribute("BookList",bookList);
		} else {
			model.addAttribute("resultMessage", "書籍データは0件です。");
		}
		return "home";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchList(@RequestParam("bookTitle") String bookTitle, Model model) {
		List<BookInfo> searchBook= booksService.searchBook(bookTitle);
		if(searchBook.isEmpty()) {
			model.addAttribute("resultMessage", "書籍データは0件です。");
		}else {
			model.addAttribute("BookList", searchBook);
		}
		return "home";
	}

}
