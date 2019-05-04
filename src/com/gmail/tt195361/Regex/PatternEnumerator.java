package com.gmail.tt195361.Regex;

/**
 * ���K�\���̕��^�̕��т��Ǘ������ɗ񋓂���񋓎q�ł��B
 * 
 * <h1 id="���K�\���̕\�킵��">���K�\���̕\�킵��</h1>
 * <p>
 * ���K�\�����v���O�����łǂ̂悤�ɕ\�����邩�l���܂��B
 * ���鐳�K�\���́A������\�����镶�^�����ɕ��ׂ����̂ł��B
 * ���Ƃ��΁A���K�\�� {@code "^[a-z]$"} �́A
 * ������̍ŏ��A�����N���X: {@code a, b, ..., z}�A������̍Ō�A
 * �� 3 �̕��^�����ɕ��ׂ����̂ł��B
 * <p>
 * ���̃v���O�����ł́A���K�\�����\�����镶�^�̕��т��Ǘ����邽�߂�
 * {@link PatternEnumerator} �N���X���g���܂��B
 * ���̃N���X�̃I�u�W�F�N�g�́A���K�\���̕��^��\�킷
 * {@link RegexPattern} �I�u�W�F�N�g�̕��т�ێ����A
 * ���������ɗ񋓂��������S���܂��B
 * <p>
 * ���̐}�́A���̃v���O�����Ŏg�p���鐳�K�\����\�킷�N���X�̍\���������܂��B
 * {@link PatternEnumerator} �̃I�u�W�F�N�g�́A
 * 0 �ȏ�� {@link RegexPattern} �̃I�u�W�F�N�g��ێ����܂��B
 * {@link RegexPattern} �N���X���p������ {@link AnyCharPattern} ��
 * {@link StartOfStringPattern} �Ȃǂ�
 * �N���X����̓I�ȕ��^��\�킵�܂��B
 * <br>
 * <div align="center">
 *   <img
 *   	src="{@docRoot}/figures/regex-representation-classes.png"
 *   	alt="���K�\����\�킷�N���X">
 * </div>
 * <p>
 * ���K�\�� {@code "^[a-z]$"} �́A{@link PatternEnumerator} �̃I�u�W�F�N�g���A
 * {@link StartOfStringPattern},
 * {@link CharClassPattern}: {@code (_charSet=a, b, ..., z)},
 * {@link EndOfStringPattern}�@�� 3 �̃I�u�W�F�N�g��
 * �ێ�������̂Ƃ��ĕ\�킹�܂��B���̃I�u�W�F�N�g�}�́A���̂悤�ɂȂ�܂��B
 * <br>
 * <div align="center">
 *   <img
 *   	src="{@docRoot}/figures/example-regex-representation-instances.png"
 *   	alt="���K�\�� &quot;^[a-z]$&quot; �̃C���X�^���X�}">
 * </div>
 * 
 * <h1 id="�񋓎q">�񋓎q</h1>
 * <p>
 * <a href="RegexMatcher.html#��v�𒲂ׂ�菇">��v�𒲂ׂ�菇</a>
 * �Ő��������悤�ɁA���K�\���ƕ�����̈�v�𒲂ׂ�ɂ́A
 * ���K�\�����\�����镶�^�ƕ����񒆂̕��������Ɉ����Ă䂫�܂��B
 * ���̂��߂ɂ́A�ȉ��̕ϐ����K�v�ɂȂ�܂��B
 * <div align="center">
 * <table
 *     border="1" rules="all"
 *     summary="���^�ƕ����̗񋓂ɕK�v�ȕϐ�">
 *   <tr bgcolor="lightgray">
 *     <th align="center">����</th>
 * 	   <th align="center">���K�\���̕��^</th>
 * 	   <th align="center">������</th>
 *   </tr>
 *   <tr>
 *     <td align="center" bgcolor="lightgray">�񋓂��鍀�ڂ̕��т��i�[����</td>
 *     <td align="center">���^���i�[����z��</td>
 *     <td align="center">��������i�[����ϐ�</td>
 *   </tr>
 *   <tr>
 *     <td align="center" bgcolor="lightgray">���ڂ̈ʒu������</td>
 *     <td align="center">���݈ʒu�������ϐ�</td>
 *     <td align="center">�J�n�ʒu�ƌ��݈ʒu�������ϐ�</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * �����̕ϐ��́A�v���O�����̒��ł܂Ƃ܂��Ďg���邱�ƂɂȂ�܂��B
 * ���^�������������ꍇ�́A�ȉ��̂悤�ɂȂ�ł��傤�B
 * <pre>
        RegexPattern[] patterns;        // ���^���i�[����z��
        int patternCurrentIndex = 0;    // ���^�̌��݈ʒu
        while (patternCurrentIndex < patterns.length) {            // ���^������ԁA�J��Ԃ��B
            RegexPattern pattern = patterns[patternCurrentIndex];  // ���݈ʒu�̕��^���擾����B
            // ���̕��^��������ƈ�v���邩���ׂ�A�A�A
            ++patternCurrentIndex;                                 // ���̕��^�Ɉړ�����B
        }
 * </pre>
 * <p>
 * �����̕ϐ���ʁX�ɂ��Ă����̂ł͂Ȃ��A
 * �N���X���쐬�����̃����o�[�ɂ��܂��B
 * ��������ƁA�֘A���镡���̂��̂��܂Ƃ߂Ĉ�����̂ŁA
 * ���ȒP�ɂȂ�܂��B
 * <p>
 * ���̃v���O�����ł́A���̃N���X���쐬���܂��B
 * �����̃N���X��񋓎q�ƌĂԂ��Ƃɂ��܂��B
 * <ul>
 *   <li>{@link PatternEnumerator} --
 *   	���K�\�����\�����镶�^��񋓂��܂��B</li>
 *   <li>{@link StringEnumerator} --
 *      �����񒆂̕�����񋓂��܂��B</li>
 * </ul>
 * <p>
 * �񋓎q�́A�񋓂��鍀�ڂ̕��тƁA���̒��̈���w���������݈ʒu��ێ����܂��B
 * �ȉ��̃��\�b�h��p���āA���݈ʒu�����ɐi�߁A���ڂ̕��т�񋓂��܂��B
 * <div align="center">
 * <table
 *     border="1" rules="all"
 *     summary="���ڂ̕��т�񋓂��郁�\�b�h">
 *   <tr bgcolor="lightgray">
 * 	   <th align="center">����</th>
 *     <th align="center">{@link PatternEnumerator}</th>
 * 	   <th align="center">{@link StringEnumerator}</th>
 *   </tr>
 *   <tr>
 *     <td align="center" bgcolor="lightgray">���݈ʒu�ɍ��ڂ����邩�ǂ����𒲂ׂ܂��B</td>
 *     <td align="center">{@link #hasCurrent() PatternEnumerator.hasCurrent()}</td>
 *     <td align="center">{@link StringEnumerator#hasCurrent()}</td>
 *   </tr>
 *   <tr>
 *     <td align="center" bgcolor="lightgray">���݈ʒu�̍��ڂ��擾���܂��B</td>
 *     <td align="center">{@link #getCurrent() PatternEnumerator.getCurrent()}</td>
 *     <td align="center">{@link StringEnumerator#getCurrent()}</td>
 *   </tr>
 *   <tr>
 *     <td align="center" bgcolor="lightgray">���݈ʒu�����̍��ڂɈړ����܂��B</td>
 *     <td align="center">{@link #moveNext() PatternEnumerator.moveNext()}</td>
 *     <td align="center">{@link StringEnumerator#moveNext()}</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * {@link PatternEnumerator} ���g���āA���^�������舵��
 * �v���O�����́A�ȉ��̂悤�Ȍ`�ɂȂ�܂��B
 * <pre>
        PatternEnumerator patEnum;
        while (patEnum.hasCurrent()) {                    // ���^������ԁA�J��Ԃ��B
            RegexPattern pattern = patEnum.getCurrent();  // ���݈ʒu�̕��^���擾����B
            // ���̕��^��������ƈ�v���邩���ׂ�A�A�A
            patEnum.moveNext();                           // ���̕��^�Ɉړ�����B 
        }
 * </pre>
 */
class PatternEnumerator extends Enumerator<RegexPattern> {

	// �����o�[�ϐ�: �񋓂��鐳�K�\�����^�̔z��ł��B
	private final RegexPattern[] _patterns;
	
	/**
	 * �w��̐��K�\�����^��񋓂���񋓎q���쐬���܂��B
	 * 
	 * @param patterns �񋓂��鐳�K�\�����^���i�[����z��ł��B
	 */
	PatternEnumerator(RegexPattern[] patterns) {
		super(patterns.length);
		_patterns = patterns;
	}
	
	@Override
	protected RegexPattern getElementAt(int index) {
		return _patterns[index];
	}
	
	@Override
	protected RegexPattern getNullElement() {
		return null;
	}
	
	/**
	 * {@link PatternEnumerator} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		RegexPattern current = getCurrent();
		int currentIndex = getCurrentIndex();
		String currentStr = (current == null) ? "NULL" : current.toString();
		return String.format("_index=%d, current=%s", currentIndex, currentStr);
	}
}
