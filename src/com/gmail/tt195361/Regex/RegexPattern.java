package com.gmail.tt195361.Regex;

/**
 * ���K�\�����\�������̕��^��\�킷���ۃN���X�ł��B
 * 
 * <h1 id="���^�̕\�킵��">���^�̕\�킵��</h1>
 * <p>
 * ���K�\���̕��^���v���O�����ŕ\�����邽�߁A���̋��ʓ_�Ƒ���_���l���܂��B
 * ���ׂĂ̕��^�ɋ��ʂ���̂́A�ǂ̕��^��������Ɉ�v����Ώۂ����邱�Ƃł��B
 * �������A������̂ǂ̕����ƈ�v���邩�́A���ꂼ��̕��^�ɂ���ĈقȂ�܂��B
 * <p>
 * �����̓_��\�킷���߂ɁA���ۓI�ȕ��^���Ӗ�������N���X�ƁA
 * ������p������̓I�ȕ��^��\�킷�h���N���X���g���Ă݂܂��B
 * ���N���X�͂��ׂĂ̔h���N���X�ɋ��ʂ��鐫�����A
 * �h���N���X�͂��ꂼ��̃N���X���ƂɈقȂ鐫�����L�q���܂��B
 * <p>
 * ���ׂĂ̕��^�͕�����Ɉ�v����Ώۂ�����A
 * ����𒲂ׂ��Ȃ���΂Ȃ�܂���B
 * ������s�����߂ɁA���N���X {@link RegexPattern} �ɁA
 * ���\�b�h {@link #oneMatch(PatternEnumerator, StringEnumerator)} ���`���܂��B
 * ���̃��\�b�h�̒��g�͕��^�ɂ���ĈقȂ�̂ŁA���̒i�K�ł͎����ł��܂���B
 * ���̂��߁A���̃��\�b�h�͒��g�̂Ȃ����ۃ��\�b�h�ɂ��܂��B
 * �����œn����� {@link PatternEnumerator} �� {@link StringEnumerator} �́A
 * ���K�\�����\�����镶�^��A�����񒆂̕��������ɗ񋓂���
 * <a href="PatternEnumerator.html#�񋓎q">�񋓎q</a> �ł��B
 * <pre>
        abstract boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum);
 * </pre>
 * <p>
 * ��舵�����ꂼ��̕��^�ɂ��āA���̃N���X���p�������A
 * �ȉ��̔h���N���X���쐬���܂��B
 * <div align="center">
 * <table
 *     border="1" rules="all"
 *     summary="���ꂼ��̕��^��\�킷�h���N���X">
 *   <tr bgcolor="lightgray">
 *     <th align="center">���^</th>
 * 	   <th align="center">�N���X</th>
 *   </tr>
 *   <tr>
 *     <td align="center">�C�ӂ� 1 ����</td>
 *     <td align="center">{@link AnyCharPattern}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">������̍ŏ�</td>
 *     <td align="center">{@link StartOfStringPattern}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">������̍Ō�</td>
 *     <td align="center">{@link EndOfStringPattern}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">�����N���X</td>
 *     <td align="center">{@link CharClassPattern}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">��</td>
 *     <td align="center">{@link ClosurePattern}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">�w��� 1 ����</td>
 *     <td align="center">{@link OneCharPattern}</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * ��L�̔h���N���X�́A���N���X�Œ�`���ꂽ���ۃ��\�b�h 
 * {@link #oneMatch(PatternEnumerator, StringEnumerator)} ���A
 * ���ꂼ��̕��^�ɉ����Ď������܂��B
 * ���̓��e�����ꂼ��̕��^�ňقȂ�Ƃ���ł���A
 * ���̕��^��������̂ǂ̕����ƈ�v���邩�A�̋L�q�ɂȂ�܂��B
 * <p>
 * �����Ő��������N���X�̊֌W���A���̐}�Ɏ����܂��B
 * <div align="center">
 *   <img
 *   	src="{@docRoot}/figures/regex-pattern-classes.png"
 *   	alt="���K�\���̕��^��\�킷�N���X">
 * </div>
 * <p>
 * ���N���X�Ɣh���N���X���g�����_�́A���̂悤�ɂȂ�܂��B
 * <ul>
 *   <li>�I�u�W�F�N�g�̃N���X����ʂ��邱�ƂȂ��A
 *   	�܂Ƃ߂Ď�舵����悤�ɂȂ�܂��B</li>
 *   <li>����Ɠ����ɁA�N���X����ʂ��邱�ƂȂ��A
 *   	�I�u�W�F�N�g�̐U�镑����ς����܂��B</li>
 * </ul>
 * <p>
 * ���Ƃ��΁A���K�\�����\�����镶�^�̃I�u�W�F�N�g��
 * �z��Ɋi�[���Ă݂܂��B
 * ���̔z��̌^�́A���N���X�� {@link RegexPattern} �ɂ��܂��B
 * ��������ƁA���̔z��ɂ͔h���N���X�̃I�u�W�F�N�g�Ȃ�΁A
 * �ǂ̃N���X����ʂ��邱�ƂȂ��A���N���X�̃I�u�W�F�N�g
 * �Ƃ��Ċi�[�ł��܂��B
 * <pre>
        RegexPattern[] patterns = new RegexPattern[3];
        patterns[0] = new StartOfStringPattern();
        patterns[1] = new OneCharPattern('a');
        patterns[2] = new EndOfStringPattern();
 * </pre>
 * <p>
 * ���̔z��Ɋi�[���ꂽ���^�̃I�u�W�F�N�g��������ƈ�v���邩
 * �ǂ����𒲂ׂ邽�߂ɁA{@code oneMatch} ���\�b�h���Ă�ł݂܂��B
 * ���̂Ƃ����ۂɌĂяo����郁�\�b�h�́A���ꂼ��̃I�u�W�F�N�g��
 * ������N���X�Œ�`���ꂽ���̂ɂȂ�܂��B���̂����݂��g���ƁA
 * �ǂ̃N���X����ʂ��邱�ƂȂ��A���ꂼ��̃N���X�ɑΉ�����
 * �ʁX�̃��\�b�h�����s�����邱�Ƃ��ł��܂��B
 * <pre>
       patterns[0].oneMatch(. . .);   // StartOfStringPattern.oneMatch
       patterns[1].oneMatch(. . .);   // OneCharPattern.oneMatch
       patterns[2].oneMatch(. . .);   // EndOfStringPattern.oneMatch
 * </pre>
 * 
 * <h1 id="���^�̈�v">���^�̈�v</h1>
 * <p>
 * ���ꂼ��̕��^�́A������̂ǂ̕����ƈ�v���邩�ɉ����āA
 * �ǂ̕��^�܂ň�v�𒲂ׂ邩�A������̂����̉�������
 * ��v���邩���قȂ�܂��B
 * �����ɂ��āA���̕\�ɂ܂Ƃ߂܂��B
 * <div align="center">
 * <table
 *     border="1" rules="all"
 *     summary="���ꂼ��̕��^��\�킷�h���N���X">
 *   <tr bgcolor="lightgray">
 *     <th align="center">���^</th>
 * 	   <th align="center">��v�𒲂ׂ镶�^</th>
 * 	   <th align="center">��v���镶����</th>
 *   </tr>
 *   <tr>
 *     <td align="center">�C�ӂ� 1 ����</td>
 *     <td>���̕��^�̈�v�̂�</td>
 *     <td align="center">1 ����</td>
 *   </tr>
 *   <tr>
 *     <td align="center">������̍ŏ�</td>
 *     <td>���̕��^�̈�v�̂�</td>
 *     <td align="center">0 ����</td>
 *   </tr>
 *   <tr>
 *     <td align="center">������̍Ō�</td>
 *     <td>���̕��^�̈�v�̂�</td>
 *     <td align="center">0 ����</td>
 *   </tr>
 *   <tr>
 *     <td align="center">�����N���X</td>
 *     <td>���̕��^�̈�v�̂�</td>
 *     <td align="center">1 ����</td>
 *   </tr>
 *   <tr>
 *     <td align="center">��</td>
 *     <td>��̈�v�ƁA��ɑ������^�̈�v�����ׂ�</td>
 *     <td align="center">0 �����ȏ�</td>
 *   </tr>
 *   <tr>
 *     <td align="center">�w��� 1 ����</td>
 *     <td>���̕��^�̈�v�̂�</td>
 *     <td align="center">1 ����</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * ���ꂼ��̕��^�̈�v�ł̂����̈قȂ�_�́A
 * ���ۃ��\�b�h {@link #oneMatch(PatternEnumerator, StringEnumerator)} ��
 * ��舵���܂��B���̃��\�b�h�́A�����Ƃ��� {@link PatternEnumerator} �� 
 * {@link StringEnumerator} ���󂯎��A���ꂼ���
 * ���݈ʒu�����̂悤�ɐi�߂܂��B
 * <ul>
 *   <li>{@link PatternEnumerator} -- 
 *       �Ō�Ɉ�v�𒲂ׂ����K�\�����^�Ɉړ����܂��B
 *       ���̕��^�̈�v�݂̂𒲂ׂ�ꍇ�A���݈ʒu�͂��̂܂܈ړ����܂���B
 *       ��̏ꍇ�A����ɑ����Ō�Ɉ�v�𒲂ׂ����^�܂ňړ����܂��B</li>
 *   <li>{@link StringEnumerator} -- 
 *       ���̐��K�\�����^�Ɉ�v�����������܂����z���A���̎��̕����Ɉړ����܂��B</li>
 * </ul>
 * <p>
 * ���ꂼ��̕��^�̈�v�ɂ��āA�ȉ��Ő������܂��B
 * <ul>
 *   <li><a href="AnyCharPattern.html#�C�ӂ� 1 �����̈�v">�C�ӂ� 1 �����̈�v</a></li>
 *   <li><a href="StartOfStringPattern.html#������̍ŏ��̈�v">������̍ŏ��̈�v</a></li>
 *   <li><a href="EndOfStringPattern.html#������̍Ō�̈�v">������̍Ō�̈�v</a></li>
 *   <li><a href="CharClassPattern.html#�����N���X�̈�v">�����N���X�̈�v</a></li>
 *   <li><a href="ClosurePattern.html#��̈�v">��̈�v</a></li>
 *   <li><a href="OneCharPattern.html#�w��� 1 �����̈�v">�w��� 1 �����̈�v</a></li>
 * </ul>
 */
abstract class RegexPattern {
	
	/**
	 * {@link RegexPattern} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 */
	protected RegexPattern() {
		//
	}
	
	/**
	 * ���̐��K�\�����^��������̌��݈ʒu�����v���邩�ǂ����𒲂ׂ�
	 * ���ۃ��\�b�h�ł��B
	 * 
	 * @param patEnum ���K�\�����^�̗񋓎q�ł��B�Ăяo�����̌��݈ʒu�́A
	 * 				���̐��K�\�����^�ł�����̂Ƃ��܂��B�Ăяo����̌��݈ʒu�́A
	 * 				�Ō�Ɉ�v���邩�ǂ������ׂ����K�\�����^�ɂȂ�܂��B
	 * @param strEnum ������̗񋓎q�ł��B�Ăяo����̌��݈ʒu�́A
	 * 				���̐��K�\�����^�Ɉ�v�����������܂����z���A
	 * 				���̎��̕����Ɉړ����܂��B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A
	 * 			���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	abstract boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum);
}
