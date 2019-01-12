package com.gmail.tt195361.Regex;

import java.util.*;

/**
 * ������𐳋K�\���̕��^�̎w��Ƃ��ĉ��߂��܂��B
 */
class RegexParser {
	
	// ���K�\���̕��^�Ƃ��ē��ʂȈӖ����������̒�`�ł��B
	private static final char Any = '.';
	private static final char Start = '^';
	private static final char End = '$';
	private static final char Closure = '*';
	private static final char CharClassStart = '[';
	private static final char Escape = '\\';

	// ���̃N���X�� static ���\�b�h�����Ȃ̂ŁA�R���X�g���N�^�� private �ɂ��āA�C���X�^���X�����Ȃ��悤�ɂ��܂��B 
	private RegexParser() {
		//
	}
	
	/**
	 * �w��̕�����𐳋K�\���̕��^�Ƃ��ĉ��߂��A���߂������e�����Ƃɍ쐬����
	 * {@link PatternEnumerator} �N���X�̃I�u�W�F�N�g��Ԃ��܂��B
 	 *  
	 * @param pattern ���K�\���̕��^���w�肷�镶����ł��B
	 * @return �w��̕���������߂������ʂ�\�킷 {@link PatternEnumerator} �N���X�̃I�u�W�F�N�g��Ԃ��܂��B
	 */
	static PatternEnumerator parse(String pattern) {
		StringEnumerator strEnum = new StringEnumerator(pattern);
		List<RegexPattern> patList = new ArrayList<RegexPattern>();
		RegexPattern lastPat = null;

		// ������ɕ���������ԁA�J��Ԃ��܂��B
		while (strEnum.hasCurrent()) {
			// ������̌��݈ʒu���琳�K�\�����^�����߂��A���̌��ʂ��擾���܂��B
			PatternParseResult patParseResult = parsePattern(strEnum, lastPat);

			// '*' �̏ꍇ�́A��O�̐��K�\�����^���폜���܂��B
			if (patParseResult.removeLastPat()) {
				patList.remove(lastPat);
			}
			
			// ���߂������ʂ���쐬�������K�\�����^�����X�g�ɒǉ����AlastPat �Ɉ�O�̕��^�Ƃ��ċL�^���܂��B
			RegexPattern parsedPattern = patParseResult.getPattern();
			patList.add(parsedPattern);
			lastPat = parsedPattern;
			
			// ���̕����Ɉړ����܂��B
			strEnum.moveNext();
		}
		
		// ���K�\�����^�̃��X�g����񋓎q���쐬���ĕԂ��܂��B
		RegexPattern[] patArray = new RegexPattern[patList.size()];
		return new PatternEnumerator(patList.toArray(patArray));
	}
	
	// ������̌��݈ʒu���琳�K�\�����^�����߂��A���ߌ��ʂ�Ԃ��܂��B
	private static PatternParseResult parsePattern(
			StringEnumerator strEnum, RegexPattern lastPat) {
		RegexPattern pattern;
		boolean removeLastPat = false;
		
		char ch = strEnum.getCurrent();
		if (ch == Any) {
			// '.' �� AnyCharPattern �ł��B
			pattern = new AnyCharPattern();
		}
		else if (ch == Start && strEnum.isStart()) {
			// '^' �����^�̍ŏ��ɂ���ꍇ�� StartOfStringPattern �ł��B
			pattern = new StartOfStringPattern();
		}
		else if (ch == End && strEnum.isLast()) {
			// '$' �����^�̍Ō�ɂ���ꍇ�� EndOfStringPattern �ł��B
			pattern = new EndOfStringPattern();
		}
		else if (ch == Closure && lastPat != null) {
			// '*' �ł��̑O�ɕ��^������ꍇ�́A���̕��^�� 0 ��ȏ�J��Ԃ� ClosurePattern �ł��B
			// ��O�̕��^�� ClosurePattern �Ɏ�荞�ނ̂ŁA���X�g����폜���܂��B
			pattern = new ClosurePattern(lastPat);
			removeLastPat = true;
		}
		else if (ch == CharClassStart && !strEnum.isLast()) {
			// '[' �����^�̍Ō�łȂ���΁A�ȍ~�𕶎��N���X�Ƃ��ĉ��߂��ACharClassParser ���쐬���܂��B
			CharClassParser charClassParser = new CharClassParser();
			pattern =  charClassParser.parse(strEnum);
		}
		else {
			// ����ȊO�́A���̕������g���Ӗ����� OneCharPattern �ł��B�G�X�P�[�v����Ă���ꍇ���l�����܂��B
			char escCh = parseEscapeChar(ch, strEnum);
			pattern = new OneCharPattern(escCh);
		}
		
		return new PatternParseResult(pattern, removeLastPat);
	}

	// �����̃G�X�P�[�v�����߂��A���߂������ʂ̕�����Ԃ��܂��B
	private static char parseEscapeChar(char ch, StringEnumerator strEnum) {
		if	(ch != Escape) {
			// Escape �łȂ���΁A���̕����ł��B
			return ch;
		}
		
		if (strEnum.isLast()) {
			// Escape ��������̍Ō�Ȃ�� Escape ��Ԃ��܂��B
			return Escape;
		}
		
		// Escape ��������̍Ō�łȂ���΁A���̎��̕�����Ԃ��܂��B
		strEnum.moveNext();
		return strEnum.getCurrent();
	}
}
