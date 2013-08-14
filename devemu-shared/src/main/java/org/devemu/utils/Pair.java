package org.devemu.utils;

public class Pair<I,O> {
	private I o1;
	private O o2;
	
	public Pair(I o1, O o2) {
		this.o1 = o1;
		this.o2 = o2;
	}
	
	public I getO1() {
		return o1;
	}
	public void setO1(I o1) {
		this.o1 = o1;
	}
	public O getO2() {
		return o2;
	}
	public void setO2(O o2) {
		this.o2 = o2;
	}
}
