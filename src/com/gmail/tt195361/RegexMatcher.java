package com.gmail.tt195361;

public class RegexMatcher {
	
	public static boolean match(ElementBuffer elemBuffer, StringBuffer strBuffer) {
		while (elemBuffer.hasCurrent()) {
			System.out.println(elemBuffer.toString());
			System.out.println(strBuffer.toString());
			
			if (!strBuffer.hasCurrent()) {
				return false;
			}
			
			RegexElement element = elemBuffer.getCurrent();
			if (!element.oneMatch(elemBuffer, strBuffer)) {
				return false;
			}
			
			elemBuffer.moveNext();
		}

		return true;
	}
}
