package com.whty.euicc.profile.genericFileManagement.bean;

import com.whty.euicc.profile.parent.JavaBean;


public class Identifier extends JavaBean {
		private String tag ="81";
		private String length;
		private String value;
		
		public Identifier() {
			
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public String getLength() {
			return length;
		}

		public void setLength(String length) {
			this.length = length;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "Identifier [length=" + length + ", tag=" + tag + ", value="
					+ value + "]";
		}

}
