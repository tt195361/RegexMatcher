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
	 * {@link ElementEnumerator} �N���X�̃I�u�W�F�N�g��Ԃ��܂��B
 	 *  
	 * @param pattern ���K�\���̕��^���w�肷�镶����ł��B
	 * @return �w��̕���������߂������ʂ�\�킷 {@link ElementEnumerator} �N���X�̃I�u�W�F�N�g��Ԃ��܂��B
	 */
	static ElementEnumerator parse(String pattern) {
		StringEnumerator strEnum = new StringEnumerator(pattern, 0);
		List<RegexElement> elemList = new ArrayList<RegexElement>();
		RegexElement lastElem = null;

		// ������ɕ���������ԁA�J��Ԃ��܂��B
		while (strEnum.hasCurrent()) {
			// ������̌��݈ʒu���琳�K�\���v�f�����߂��A���̌��ʂ��擾���܂��B
			ElementParseResult elemParseResult = parseElement(strEnum, lastElem);

			// '*' �̏ꍇ�́A�Ō�̐��K�\���v�f���폜���܂��B
			if (elemParseResult.removeLastElem()) {
				elemList.remove(lastElem);
			}
			
			// ���߂������ʂ���쐬�������K�\���v�f�����X�g�ɒǉ����AlastElem �ɍŌ�̗v�f�Ƃ��ċL�^���܂��B
			RegexElement parsedElement = elemParseResult.getElement();
			elemList.add(parsedElement);
			lastElem = parsedElement;
			
			// ���̕����Ɉړ����܂��B
			strEnum.moveNext();
		}
		
		// ���K�\���v�f�̃��X�g����񋓎q���쐬���ĕԂ��܂��B
		return new ElementEnumerator(elemList);
	}
	
	// ������̌��݈ʒu���琳�K�\���v�f�����߂��A���ߌ��ʂ�Ԃ��܂��B
	private static ElementParseResult parseElement(
			StringEnumerator strEnum, RegexElement lastElem) {
		RegexElement element;
		boolean removeLastElem = false;
		
		char ch = strEnum.getCurrent();
		if (ch == Any) {
			// '.' �� AnyCharElement �ł��B
			element = new AnyCharElement();
		}
		else if (ch == Start && strEnum.isStart()) {
			// '^' �����^�̍ŏ��ɂ���ꍇ�� StartOfStringElement �ł��B
			element = new StartOfStringElement();
		}
		else if (ch == End && strEnum.isLast()) {
			// '$' �����^�̍Ō�ɂ���ꍇ�� EndOfStringElement �ł��B
			element = new EndOfStringElement();
		}
		else if (ch == Closure && lastElem != null) {
			// '*' �ł��̑O�ɗv�f������ꍇ�́A���̗v�f�� 0 ��ȏ�J��Ԃ� ClosureElement �ł��B
			// �O�̗v�f�� ClosureElement �Ɏ�荞�ނ̂ŁA���X�g����폜���܂��B
			element = new ClosureElement(lastElem);
			removeLastElem = true;
		}
		else if (ch == CharClassStart && !strEnum.isLast()) {
			// '[' �����^�̍Ō�łȂ���΁A�ȍ~�𕶎��N���X�Ƃ��ĉ��߂��ACharClassElement ���쐬���܂��B
			CharClassParser charClassParser = new CharClassParser();
			element =  charClassParser.parse(strEnum);
		}
		else {
			// ����ȊO�́A���̕������g���Ӗ����� OneCharElement �ł��B�G�X�P�[�v����Ă���ꍇ���l�����܂��B
			char escCh = parseEscapeChar(ch, strEnum);
			element = new OneCharElement(escCh);
		}
		
		return new ElementParseResult(element, removeLastElem);
	}

	/**
	 * �����̃G�X�P�[�v�����߂��A���߂������ʂ̕�����Ԃ��܂��B
	 * 
	 * @param ch ���߂��镶���ł��B
	 * @param strEnum ������̗񋓎q�ł��B�Ăяo�����̌��݈ʒu�́A���߂��镶���̈ʒu�ł�����̂Ƃ��܂��B
	 * 			�Ăяo����̌��݈ʒu�͍Ō�ɉ��߂��������Ɉړ����܂��B
	 * @return ���߂������ʂ̕�����Ԃ��܂��B
	 */
	static char parseEscapeChar(char ch, StringEnumerator strEnum) {
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
