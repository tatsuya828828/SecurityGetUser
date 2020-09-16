package com.kuma.domain.model;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserDetails implements UserDetails {
	// Springで必要なフィールド
	private String userId;
	private String password;
	private Date passUpdateDate; // パスワード更新日付
	private int loginMissTimes; // ログイン失敗回数
	private boolean unlock; // ロック状態フラグ
	private boolean enabled; // 有効・無効フラグ
	private Date userDueDate; // ユーザー有効期限
	// 権限のCollection
	private Collection<? extends GrantedAuthority> authority;
	// 独自のフィールド
	private String tenantId; // テナントID
	private String appUserName;
	private String mailAddress;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return authority;
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public String getUsername() {
		return this.userId;
	}
	// アカウントの有効期限チェック
	@Override
	public boolean isAccountNonExpired() {
		if(this.userDueDate.after(new Date())) {
			// 現在日付よりも後なら有効
			return true;
		} else {
			// 現在日付よりも前なら無効
			return false;
		}
	}
	// アカウントのロックチェック
	@Override
	public boolean isAccountNonLocked() {
		return this.unlock;
	}
	// パスワードの有効期限チェック
	public boolean isCredentialsNonExpired() {
		if(this.passUpdateDate.after(new Date())) {
			// 現在日付よりも後なら有効
			return true;
		} else {
			// 現在日付よりも前なら無効
			return false;
		}
	}
	// アカウントの有効・無効チェック
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
