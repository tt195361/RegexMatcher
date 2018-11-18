/**
 * ���K�\����������ƈ�v���邩�ǂ����𒲂ׂ�v���O���� {@link com.gmail.tt195361.Regex.RegexMatcher} �ɂ��Đ������܂��B
 * {@link com.gmail.tt195361.Regex.RegexMatcher} �́A���ۂɓ��삷�邠����x�̑傫���̃v���O�����ŁA������ǂ̂悤�ɍl���č쐬��������������܂��B
 * <p>
 * ���̃v���O�����Ɛ������쐬����ɂ�����A
 * <a
 *   href="https://www.amazon.co.jp/%E3%82%BD%E3%83%95%E3%83%88%E3%82%A6%E3%82%A7%E3%82%A2%E4%BD%9C%E6%B3%95-Brian-W-Kernighan/dp/4320021428">
 *   "�\�t�g�E�F�A��@, Brian W.Kernighan, P.J.Plauger ���A�ؑ� �� ��"
 * </a>
 * ���Q�l�ɂ����Ă��������܂����B�����ɋL���āA���ӂ������܂��B
 * <p>
 * {@link com.gmail.tt195361.Regex.RegexMatcher} �ɂ��āA�ȉ��̓��e�����ɐ������܂��B
 * <ul>
 *   <li>��舵�����K�\�� -- ���̃v���O��������舵�����K�\���ɂ��Đ������܂��B</li>
 *   <li>�g���� -- ���̃v���O�������ǂ̂悤�Ɏg�����������܂��B</li>
 *   <li>����̗��� -- �v���O�����S�̂̓����������܂��B</li>
 *   <li>�N���X�̍\�� -- �v���O�������̃N���X�̍\���ƐӖ��̕��S�ɂ��Đ������܂��B</li>
 *   <li>��v�̒��ו� -- ���K�\���ƕ�����̈�v�𒲂ׂ�菇��������܂��B</li>
 * </ul>
 * <h2>��舵�����K�\��</h2>
 * <p>
 * ���̃v���O�����ł́A�ȉ��̐��K�\������舵���܂��B
 * <table
 * 		border="1" rules="all"
 * 		summary="���̃v���O�����Ŏ�舵�����K�\��">
 *   <tr align="center">
 *     <th>���K�\��</th>
 *     <th>��v����Ώ�</th>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code .}</td>
 *     <td>�C�ӂ� 1 �����ƈ�v���܂��B</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code ^}</td>
 *     <td>���K�\���̕��^�̍ŏ��ɂ���ꍇ�́A������̍ŏ��ƈ�v���܂��B
 *   		����ȊO�̏ꍇ�A���̕������g�ƈ�v���܂��B</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code $}</td>
 *     <td>���K�\���̕��^�̍Ō�ɂ���ꍇ�́A������̍Ō�ƈ�v���܂��B
 *   		����ȊO�̏ꍇ�́A���̕������g�ƈ�v���܂��B</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code [ ]}</td>
 *     <td>{@code [ ]} ���Ŏw��̕����̒��� 1 �����ƈ�v���܂��B�����͈ȉ��̂悤�Ɏw�肵�܂��B
 *       <ul>
 *         <li>���� -- �w��̕������܂߂܂��B���Ƃ��΁A {@code [abc]} �́A
 *       		{@code a}, {@code b}, {@code c} �̂��ꂼ��̕������w�肵�܂��B</li>
 *         <li>�J�n����{@code -}�I������ -- �J�n�����ƏI�������� {@code -} �ŋ�؂�ƁA
 *       		���͈̔͂̕������w�肵�܂��B���Ƃ��΁A{@code [A-Z]} �͉p�啶��
 *       		({@code A}, {@code B}, ..., {@code Z}) ���A{@code [0-9]} �͐���
 *       		({@code 0}, {@code 1}, ..., {@code 9}) ���A
 *       		{@code [A-Z0-9]} �́A�p�啶���Ɛ�����\�킵�܂��B</li>
 *       </ul>
 *     </td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code [^ ]}</td>
 *     <td>{@code [ ]} ���Ŏw��̕����̒��� 1 �����ȊO�ƈ�v���܂��B</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code *}</td>
 *     <td>���̑O�̕��^�� 0 ��ȏ�̌J��Ԃ��ƈ�v���܂��B���Ƃ��΁A{@code a*} ��
 *   		{@code a} �� 0 ��ȏ�̌J��Ԃ��A{@code .*} �͔C�ӂ̕����� 0 ��ȏ��
 *   		�J�Ԃ��A{@code [0-9]*} �͐����� 0 ��ȏ�̌J��Ԃ��ƈ�v���܂��B</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code \}</td>
 *     <td>���̌�̕��������̕������g�Ƃ��Ď�舵���܂��B{@code [ ]} ���ł��g���܂��B
 *   		���Ƃ��� {@code \.} �́A{@code .} �̓��ʂȈӖ���ł������A{@code .} ���g
 *   		�Ƃ��Ď�舵���܂��B�܂��A{@code \\} �� {@code \} ���Ӗ����܂��B</td>
 *   </tr>
 *   <tr>
 *     <td align="center">��L�ȊO�̕���</td>
 *     <td>���̕������g�ƈ�v���܂��B</td>
 *   </tr>
 * </table>
 * <p>
 * ���K�\���ƕ�����̈�v�́A�ȉ��̂悤�Ɏ�舵���܂��B
 * <ul>
 *   <li>������̂Ȃ�ׂ��n�߂����v�����܂��B���Ƃ��΁A���K�\�� "aa" �́A������
 *   	"aabaac" �ƁA�J�n�ʒu 0 �� 3 �� 2 ���ň�v���܂����A���n�߂̊J�n�ʒu 0 ����
 *   	��v�����܂��B</li>
 *   <li>������̂Ȃ�ׂ����������ƈ�v�����܂��B���Ƃ��΁A���K�\�� "a*" �́A
 *   	������ "aaa" �̈�v�ł́A "", "a", "aa", "aaa" �� 4 �ʂ肪�l�����܂����A
 *   	��Ԓ��� "aaa" �����ʂƂ��܂��B</li>
 * </ul>
 * <h2>���̃v���O�����̎g����</h2>
 * <p>
 * ���̃v���O�������g���āA�w��̐��K�\���̕��^�����������v���邩�ǂ����𒲂ׂ�菇�́A
 * �ȉ��̂悤�ɂȂ�܂��B
 * <ol>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher} �N���X�̃R���X�g���N�^
 *   	{@link com.gmail.tt195361.Regex.RegexMatcher#RegexMatcher(String)} ���A���K�\���̕��^��\�킷�������
 *   	�����Ƃ��ČĂяo���A{@link com.gmail.tt195361.Regex.RegexMatcher} �N���X�̃I�u�W�F�N�g���쐬���܂��B</li>
 *   <li>�쐬���� {@link com.gmail.tt195361.Regex.RegexMatcher} �N���X�̃I�u�W�F�N�g�ɑ΂��āA��v���邩�ǂ����𒲂ׂ�
 *   	������������Ƃ��ā@{@link RegexMatcher#match} ���\�b�h���Ăяo���܂��B</li>
 *   <li>{@link RegexMatcher#match} ���\�b�h�́A{@link com.gmail.tt195361.Regex.MatchResult} �N���X�̃I�u�W�F�N�g��
 *   	�Ԃ��܂��B���̃I�u�W�F�N�g�͈�v�ɐ����������ǂ�����Ԃ��Ȃǂ̃��\�b�h������A��v�𒲂ׂ����ʂ�
 *   	�񋟂��܂��B</li>
 * </ol>
 * <p>
 * ���Ƃ��΁A�ȉ��̃v���O�����́A���K�\���̕��^ {@code pattern} �������� {@code str} ��
 * ��v���邩�ǂ����𒲂ׂ܂��B
 * <pre>
 * <code>
 *		RegexMatcher matcher = new RegexMatcher(pattern);
 *		MatchResult result = matcher.match(str);
 *		if (result.isSuccess()) {
 *			// ���K�\�����A������Ɉ�v�����B
 *			. . . . .
 *		}
 * </code>
 * </pre>
 * <h2>�v���O�����̍\��</h2>
 * <p>
 * {@link com.gmail.tt195361.Regex.RegexMatcher} �v���O�����́A�ȉ��̗���œ��삵�܂��B
 * <ol>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher} �N���X�̃R���X�g���N�^
 *   	{@link com.gmail.tt195361.Regex.RegexMatcher#RegexMatcher(String)} �Ŏ󂯎�������K�\����
 *   	���^��\�킷����������߂��A�v���O�������Ŏg�p����\�����쐬���܂��B</li>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher#match} ���\�b�h�Ŏw�肳�ꂽ������ɑ΂��āA
 *   	�쐬���������\������v���邩�ǂ����𒲂ׂ܂��B</li>
 * </ol>
 * <h2>���K�\���̓����\��</h2>
 * <p>
 * ���K�\���̕����񂩂�쐬����v���O�����̓����\���́A�ȉ��̒ʂ�ł��B
 * <ul>
 *   <li>��舵�����ꂼ��̐��K�\���̕��^�ɂ��āA���̕��^��\�킷�N���X���쐬���܂��B</li>
 *   <li>���ꂼ��̐��K�\���̕��^��\�킷�N���X�́A���ۃN���X {@link com.gmail.tt195361.Regex.RegexPattern} ����h�������܂��B</li>
 *   <li>. . . . .</li>
 * </ul>
 * <table
 * 		border="1" rules="all"
 * 		summary="�N���X�̕���">
 *   <tr>
 *     <th>����</th>
 *     <th>�N���X</th>
 *   </tr>
 *   <tr>
 *     <td rowspan="2" valign="top">�S�̂̐���</td>
 *     <td>RegexMatcher</td>
 *   </tr>
 *   <tr>
 *     <td>MatchResult</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="3" valign="top">���K�\���̕��^�̉���</td>
 *     <td>RegexParser</td>
 *   </tr>
 *   <tr>
 *     <td>CharClassParser</td>
 *   </tr>
 *   <tr>
 *     <td>PatternParseResult</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="7" valign="top">���K�\���̈�̕��^</td>
 *     <td>RegexPattern</td>
 *   </tr>
 *   <tr>
 *     <td>AnyCharPattern</td>
 *   </tr>
 *   <tr>
 *     <td>CharClassPattern</td>
 *   </tr>
 *   <tr>
 *     <td>ClosurePattern</td>
 *   </tr>
 *   <tr>
 *     <td>EndOfStringPattern</td>
 *   </tr>
 *   <tr>
 *     <td>OneCharPattern</td>
 *   </tr>
 *   <tr>
 *     <td>StartOfStringPattern</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="3" valign="top">�R���N�V�����̍��ڂ̗�</td>
 *     <td>PatternEnumerator</td>
 *   </tr>
 *   <tr>
 *     <td>StringEnumerator</td>
 *   </tr>
 *   <tr>
 *     <td>EnumeratorState</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="1" valign="top">���[�e�B���e�B</td>
 *     <td>MathUtils</td>
 *   </tr>
 * </table>
 */
package com.gmail.tt195361.Regex;