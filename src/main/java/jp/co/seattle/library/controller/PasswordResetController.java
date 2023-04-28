package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.service.UsersService;

@Controller
public class PasswordResetController {
	final static Logger logger = LoggerFactory.getLogger(DeleteBookController.class);
	//loggerでログを記録 インスタンス生成

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/passwordReset", method = RequestMethod.GET)
	public String deleteBook(Locale locale, Model model) {
		logger.info("Welcome passwordReset.java! The client locale is {}.", locale);
		return "passwordReset";
	}
	
	
	@Transactional
	@RequestMapping(value = "/passwordUpdate", method = RequestMethod.POST)
	public String createAccount(Locale locale, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("passwordForCheck") String passwordForCheck,
			Model model) {
		// デバッグ用ログ
		logger.info("Welcome passwordUpdate! The client locale is {}.", locale);

		if(password.length() >= 8 && password.matches("^[A-Za-z0-9]+$")) {
			if(password.equals(passwordForCheck)) {
				UserInfo userInfo = new UserInfo();
				userInfo.setEmail(email);
				userInfo.setPassword(password);
				usersService.reset(userInfo);
				return "redirect:/";
			}else {
				model.addAttribute("errorMessage", "パスワードと確認用パスワードが一致していません。");
				return "passwordReset";
			}
		}else {
			model.addAttribute("errorMessage", "パスワードは8文字以上の半角英数字に設定してください。");
			return "passwordReset";
		}
	}
}

