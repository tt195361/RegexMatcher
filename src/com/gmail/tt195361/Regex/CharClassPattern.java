package com.gmail.tt195361.Regex;

import java.util.HashSet;

/**
 * �����N���X��\�킷���K�\�����^�ł��B�����N���X��
 * �w��̕������܂܂�Ă��邩�ǂ�������舵���܂��B
 * 
 * <h1 id="�����o�[�ϐ�">�����o�[�ϐ�</h1>
 * <p>
 * {@link CharClassPattern} �N���X�́A�ȉ��̃����o�[�ϐ����g���܂��B
 * <div align="center">
 * <table
 * 		border="1" rules="all"
 * 		summary="StringEnumerator �N���X�̃����o�[�ϐ�">
 *   <tr bgcolor="lightgray">
 *     <th align="center">���O</th>
 *     <th align="center">�^</th>
 *     <th align="center">����</th>
 *   </tr>
 *   <tr>
 *     <td>{@code _notContains}</td>
 *     <td>{@code final boolean}</td>
 *     <td>�w��̕������܂܂�Ă��Ȃ����Ƃ������܂��B
 *         {@code [ ]} �̏ꍇ�� {@code false} �ɁA
 *         {@code [^ ]} �̏ꍇ�� {@code true} �ɂȂ�܂��B
 *     	   ���̕ϐ��̒l�́A�R���X�g���N�^�ōŏ��ɐݒ肵�����Ƃ͕ύX���Ȃ��̂ŁA
 *     	   {@code final} �ɂ��܂��B
 *         <p>
 *         ���̕ϐ��̖��O�ł����A�����w�肵�Ȃ�
 *         �f�t�H���g�̒l�� {@code false} �ɂȂ�悤�ɂ��܂����B
 *         �����ł� {@code [ ]} �̏ꍇ��
 *         �l�� {@code false} �ɂȂ�悤�A�ϐ����� {@code _contains}
 *         �ł͂Ȃ� {@code _notContains} �ɂ��Ă��܂��B</td>
 *   </tr>
 *   <tr>
 *     <td>{@code _charSet}</td>
 *     <td>{@code final HashSet<Character>}</td>
 *     <td>{@code [ ]} ���Ŏw�肳�ꂽ������
 *         �i�[���� {@code Character} �� {@link java.util.HashSet} �ł��B
 *         ���̒l���A�R���X�g���N�^��
 *         �ŏ��ɐݒ肵�����Ƃ͕ύX���Ȃ��̂ŁA{@code final} �ɂ��܂��B</td>
 *   </tr>
 * </table>
 * </div>
 * 
 * <h1 id="�����N���X�̈�v">�����N���X�̈�v</h1>
 * <p>
 * �����N���X����v����̂́A���݈ʒu�ɕ���������A���A
 * �����o�[�ϐ� {@code _notContains} �� {@code _charSet} ��
 * ���ƂŐ�����������𖞂����ꍇ�ł��B
 * ���̏����𒲂ׂ郁�\�b�h�� {@code matchConditionSatisfied()} �Ƃ���ƁA
 * {@link #oneMatch(PatternEnumerator, StringEnumerator)} ���\�b�h�́A
 * ���̂悤�ɂȂ�܂��B
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            if (!strEnum.hasCurrent()) {
                return false;
            }

            char ch = strEnum.getCurrent();
            if (!matchConditionSatisfied(ch)) {
                return false;
            }

            strEnum.moveNext();
            return true;
        }
 * </pre>
 * <p>
 * �����o�[�ϐ� {@code _notContains} �� {@code _charSet}�A
 * ����ь��݈ʒu�̕��� {@code ch} ����v�̂��߂ɖ����������́A
 * ���̂悤�ɂȂ�܂��B
 * <div align="center">
 * <table
 * 		border="1" rules="all"
 * 		summary="��v�̏���">
 *   <tr bgcolor="lightgray" align="center">
 *     <th>{@code _notContains}</th>
 *     <th>{@code _charSet.contains(ch)}</th>
 *     <th>����</th>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code false}</td>
 *     <td align="center">{@code true}</td>
 *     <td>{@code [ ]} �̏ꍇ�́A���݈ʒu�̕����� {@code _charSet} ��
 *         �܂܂�Ă���Ώ����𖞂����܂��B</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code true}</td>
 *     <td align="center">{@code false}</td>
 *     <td>{@code [^ ]} �̏ꍇ�́A���݈ʒu�̕����� {@code _charSet} ��
 *         �܂܂�Ă��Ȃ���Ώ����𖞂����܂��B</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * ���̏����́A�r���I�_���a (XOR) ���g���ċL�q�ł��܂��B
 * {@code matchConditionSatisfied()} ���\�b�h�́A
 * �ȉ��̂悤�ɂȂ�܂��B
 * <pre>
        private boolean matchConditionSatisfied(char ch) {
            // ^ �͔r���I�_���a�ŁA"false ^ true" ���邢�� "true ^ false" �̂Ƃ��A
            // ���ʂ� true �ɂȂ�܂��B
            return _notContains ^ _charSet.contains(ch);
        }
 * </pre>
 */
class CharClassPattern extends RegexPattern {

	// �����o�[�ϐ�: �܂܂�Ă��Ȃ��̎w��Ǝw��̕������i�[���܂��B
	private final boolean _notContains;
	private final HashSet<Character> _charSet;
	
	/**
	 * �p�����[�^�Ŏw��̓��e��\�킷 {@link CharClassPattern} �N���X�̃I�u�W�F�N�g��
	 * �쐬���܂��B
	 * 
	 * @param notContains �w��̕������܂܂�Ă��Ȃ����Ƃ��w�肵�܂��B
	 * @param charSet �w��̕������i�[���� {@link HashSet} �ł��B
	 */
	CharClassPattern(boolean notContains, HashSet<Character> charSet) {
		_notContains = notContains;
		_charSet = charSet;
	}
	
	/**
	 * �w��̕������܂܂�Ă��Ȃ����Ƃ��w�肷��l���擾���܂��B
	 * 
	 * @return �w��̕������܂܂�Ă��Ȃ����Ƃ��w�肷��l��Ԃ��܂��B
	 */
	boolean getNotContains() {
		return _notContains;
	}
	
	/**
	 * �w��̕������i�[���� {@link HashSet} ���擾���܂��B
	 * 
	 * @return �w��̕������i�[���� {@link HashSet} ��Ԃ��܂��B
	 */
	HashSet<Character> getCharSet() {
		return _charSet;
	}
	
	/**
	 * ���̐��K�\�����^��������̌��݈ʒu�����v���邩�ǂ����𒲂ׂ܂��B
	 * �����N���X�́A���݈ʒu�ɕ���������A���A���݈ʒu�̕����� 
	 * {@link #getNotContains} �� {@link #getCharSet} �̕Ԃ��l�ƁA
	 * �ȉ��̂����ꂩ�̏����𖞂����ꍇ�Ɉ�v���܂��B
	 * <ul>
	 *   <li>{@link #getNotContains} �� {@code false} ��Ԃ��ꍇ�A
	 *   	���݈ʒu�̕����� {@link #getCharSet} �̕Ԃ� {@link HashSet} ��
	 *      �܂܂�Ă���B</li>
	 *   <li>{@link #getNotContains} �� {@code true} ��Ԃ��ꍇ�A
	 *   	���݈ʒu�̕����� {@link #getCharSet} �̕Ԃ� {@link HashSet} ��
	 *      �܂܂�Ă��Ȃ��B</li>
	 * </ul>
	 * 
	 * @param patEnum ���K�\�����^�̗񋓎q�ł��B�Ăяo����̌��݈ʒu�͈ړ����܂���B
	 * @param strEnum ������̗񋓎q�ł��B��v�ɐ��������ꍇ�A�Ăяo����̌��݈ʒu��
	 * 				���̕����Ɉړ����܂��B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A
	 * 			���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	@Override
	boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
		if (!strEnum.hasChar()) {
			return false;
		}
		
		char ch = strEnum.getCurrent();
		if (!matchConditionSatisfied(ch)) {
			return false;
		}

		strEnum.moveNext();
		return true;
	}
	
	// �w��̕�������v�̏����𖞂����Ă��邩�ǂ����𒲂ׂ܂��B
	private boolean matchConditionSatisfied(char ch) {
		// ^ �͔r���I�_���a�ŁA"false ^ true" ���邢�� "true ^ false" �̂Ƃ��A
		// ���ʂ� true �ɂȂ�܂��B
		return _notContains ^ _charSet.contains(ch);
	}
	
	/**
	 * {@link CharClassPattern} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return String.format(
				"CharClassPattern: _notContains=%s, _charSet=%s",
				Boolean.toString(_notContains), _charSet.toString());
	}
}
