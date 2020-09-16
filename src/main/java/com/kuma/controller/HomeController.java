package com.kuma.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kuma.domain.model.AppUserDetails;

import lombok.extern.slf4j.Slf4j;

@Controller
// Lombokが用意している、Slf4jアノテーションをつけると、ログ出力をすることができる
// 例えば、log.info("XXX")とすれば、INFOレベルのログが出力される
@Slf4j
public class HomeController {
	// @AuthenticationPrincipalをUserクラスにつけると、Userクラスにログイン情報が渡される
	// このUserクラスは、Springが用意しているクラスである
	@GetMapping("/home")
	public String getHome(Model model, @AuthenticationPrincipal AppUserDetails user) {
		log.info("HomeController Start");

		// ログインユーザー情報の表示
		log.info(user.toString());
		log.info("HomeController End");
		return "home";
	}

	// =====================================
	// @AuthenticatoinPrincipalを使わない場合
	// =====================================

	@GetMapping("/home2")
	public String getHome2(Model model, Principal principal) {
		// ログインユーザー情報の取得
		Authentication authentication = (Authentication) principal;
		AppUserDetails user1 = (AppUserDetails) authentication.getPrincipal();
		log.info("user1:"+ user1.toString());

		// ログインユーザー情報の取得
		// SecurityContextHolderを使うことでも、ユーザー情報を取得することができる
		AppUserDetails user2 = (AppUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		log.info("user2:"+ user2.toString());
		return "home";
	}
}
