package com.jrey.util;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternLookup {

	public static List<PatternMatch> getMatches(String regex, String text) {
		List<PatternMatch> matches = new LinkedList<PatternMatch>();
		Pattern p;
		Matcher m;
		p = Pattern.compile(regex);
		m = p.matcher(text);
		while (m.find()) {
			String found = text.substring(m.start(),m.end());
			matches.add(new PatternLookup.PatternMatch(found, text, m.start(),m.end()));
		}

		return matches;

	}

	public static class PatternMatch {

		String text;
		String holder;
		int startPos;
		int endPos;

		public PatternMatch(String text, String holder, int startPos, int endPos) {
			super();
			this.text = text;
			this.holder = holder;
			this.startPos = startPos;
			this.endPos = endPos;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getHolder() {
			return holder;
		}

		public void setHolder(String holder) {
			this.holder = holder;
		}

		public int getStartPos() {
			return startPos;
		}

		public void setStartPos(int startPos) {
			this.startPos = startPos;
		}

		public int getEndPos() {
			return endPos;
		}

		public void setEndPos(int endPos) {
			this.endPos = endPos;
		}

	}

}
