package com.gmail.tt195361.Regex;

import java.util.*;

/**
 * ������𐳋K�\���̕��^�̎w��Ƃ��ĉ��߂��܂��B
 * 
 * <h1 id="��舵�����K�\��">��舵�����K�\��</h1>
 * <p>
 * ���̃v���O�����Ŏ�舵�����K�\���̕��^���A���̕\�ɂ܂Ƃ߂܂��B
 * <table
 * 		border="1" rules="all"
 * 		summary="���̃v���O�����Ŏ�舵�����K�\���̕��^">
 *   <tr align="center" bgcolor="lightgray">
 *     <th>���^</th>
 *     <th>��v����Ώ�</th>
 *     <th>����镶��</th>
 *     <th>�Ăѕ�</th>
 *     <th>�g�p��</th>
 *     <th>��v��</th>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code .}</td>
 *     <td>�C�ӂ� 1 ����</td>
 *     <td>�ǂ��ł��悢</td>
 *     <td>�C�ӂ� 1 ����</td>
 *     <td align="center">{@code .}</td>
 *     <td>{@code a, A, 0, ...}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code ^}</td>
 *     <td>������̍ŏ�</td>
 *     <td>���K�\���̍ŏ�</td>
 *     <td>������̍ŏ�</td>
 *     <td align="center">{@code ^a}</td>
 *     <td>{@code a, ab, abc, ...}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code $}</td>
 *     <td>������̍Ō�</td>
 *     <td>���K�\���̍Ō�</td>
 *     <td>������̍Ō�</td>
 *     <td align="center">{@code z$}</td>
 *     <td>{@code z, yz, xyz, ...}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code [ ]}</td>
 *     <td>�w��̕����̒��� 1 ����</td>
 *     <td rowspan="2">�ǂ��ł��悢</td>
 *     <td rowspan="2">�����N���X</td>
 *     <td align="center">{@code [a-z]}</td>
 *     <td>{@code a, b, c, ..., z}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code [^ ]}</td>
 *     <td>�w��̕����̒��� 1 �����ȊO</td>
 *     <td align="center">{@code [^a-z]}</td>
 *     <td>{@code A, B, ..., 0, 1, ..., !, $, ...}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code *}</td>
 *     <td>���̑O�̕��^�� 0 ��ȏ�̌J��Ԃ�</td>
 *     <td>���̕��^�̌�</td>
 *     <td>��</td>
 *     <td align="center">{@code a*}</td>
 *     <td>{@code "", a, aa, aaa, ...}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">��L�ȊO�̕���</td>
 *     <td>���̕������g</td>
 *     <td>�ǂ��ł��悢</td>
 *     <td>�w��� 1 ����</td>
 *     <td align="center">{@code a}</td>
 *     <td>{@code a}</td>
 *   </tr>
 * </table>
 * 
 * <h1 id="���ʂȈӖ��̑ł�����">���ʂȈӖ��̑ł�����</h1>
 * <p>
 * ���ʂȈӖ��������������̕������g�Ƃ��Ď�舵���ɂ́A���̕����̑O�� {@code \} ��u���܂��B
 * ���Ƃ��� {@code \.} �́A{@code .} �̓��ʂȈӖ���ł������A{@code .} ���g
 * �Ƃ��Ď�舵���܂��B�܂��A{@code \\} �� {@code \} ���Ӗ����܂��B
 * {@code [ ]} ���A���邢�� {@code [^ ]} ���ł́A{@code \} �͎�舵���܂���B
 * 
 * <h1 id="��v�̏ڍ�">��v�̏ڍ�</h1>
 * <p>
 * ���K�\���ƕ�����̈�v�́A�ȉ��̂悤�Ɏ�舵���܂��B
 * <ul>
 *   <li id="�ō���v">�ō���v -- ������̂Ȃ�ׂ��������v�����܂��B
 *   	���Ƃ��΁A���K�\�� {@code "aa"} �́A������
 *   	{@code "aabaac"} �ƁA�J�n�ʒu 0 �� 3 �� 2 ���ň�v���܂����A��荶�̊J�n�ʒu 0 ����
 *   	��v�����܂��B</li>
 *   <li>�Œ���v -- ������̂Ȃ�ׂ����������ƈ�v�����܂��B���Ƃ��΁A���K�\�� {@code "a*"} ��
 *   	������ {@code "aaa"} �ɑ΂��āA{@code ""}�A{@code "a"}�A
 *      {@code "aa"}�A{@code "aaa"} �� 4 �ʂ�̈�v���l�����܂��B
 *   	���̒��ŁA��v���镶���񂪈�Ԓ��� {@code "aaa"} �����ʂƂ��܂��B</li>
 * </ul>
 */
class RegexParser {
	
	// ���K�\���̕��^�Ƃ��ē��ʂȈӖ����������̒�`�ł��B
	private static final char Any = '.';
	private static final char Start = '^';
	private static final char End = '$';
	private static final char Closure = '*';
	private static final char CharClassStart = '[';
	private static final char Escape = '\\';

	// ���̃N���X�� static ���\�b�h�����Ȃ̂ŁA�R���X�g���N�^�� private �ɂ��āA
	// �C���X�^���X�����Ȃ��悤�ɂ��܂��B 
	private RegexParser() {
		//
	}
	
	/**
	 * �w��̕�����𐳋K�\���̕��^�Ƃ��ĉ��߂��A���߂������e�����Ƃɍ쐬����
	 * {@link PatternEnumerator} �N���X�̃I�u�W�F�N�g��Ԃ��܂��B
 	 *  
	 * @param pattern ���K�\���̕��^���w�肷�镶����ł��B
	 * @return �w��̕���������߂������ʂƂ��č쐬����
	 * 		{@link PatternEnumerator} �N���X�̃I�u�W�F�N�g��Ԃ��܂��B
	 */
	static PatternEnumerator parse(String pattern) {
		StringEnumerator strEnum = StringEnumerator.makeForParse(pattern);
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
			
			// ���߂������ʂ���쐬�������K�\�����^�����X�g�ɒǉ����A
			// lastPat �Ɉ�O�̕��^�Ƃ��ċL�^���܂��B
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
			// '*' �ł��̑O�ɕ��^������ꍇ�́A���̕��^�� 0 ��ȏ�J��Ԃ�
			// ClosurePattern �ł��B��O�̕��^�� ClosurePattern �Ɏ�荞�ނ̂ŁA
			// ���X�g����폜���܂��B
			pattern = new ClosurePattern(lastPat);
			removeLastPat = true;
		}
		else if (ch == CharClassStart && !strEnum.isLast()) {
			// '[' �����^�̍Ō�łȂ���΁A�ȍ~�𕶎��N���X�Ƃ��ĉ��߂��A
			// CharClassPattern ���쐬���܂��B
			CharClassParser charClassParser = new CharClassParser();
			pattern =  charClassParser.parse(strEnum);
		}
		else {
			// ����ȊO�́A���̕������g���Ӗ����� OneCharPattern �ł��B
			// �G�X�P�[�v����Ă���ꍇ���l�����܂��B
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
