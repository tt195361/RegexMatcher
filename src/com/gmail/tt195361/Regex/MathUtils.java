package com.gmail.tt195361.Regex;

/**
 * 数値処理に関するメソッドを集めたクラスです。
 */
class MathUtils {
	
	// インスタンスを作成する必要はないので、コンストラクタを private にします。
	private MathUtils() {
		//
	}

	/**
	 * 2 つの文字のうちの小さいほうを返します。
	 * 
	 * @param ch1 1 つ目の文字です。
	 * @param ch2 2 つ目の文字です。
	 * @return　2 つの文字のうちのどちらか小さいほうを返します。
	 */
	static char minCh(char ch1, char ch2) {
		return ch1 <= ch2 ? ch1 : ch2;
	}
	
	/**
	 * 2 つの文字のうちの大きいほうを返します。
	 * 
	 * @param ch1 1 つ目の文字です。
	 * @param ch2 2 つ目の文字です。
	 * @return　2 つの文字のうちのどちらか大きいほうを返します。
	 */
	static char maxCh(char ch1, char ch2) {
		return ch1 <= ch2 ? ch2 : ch1;
	}
}
