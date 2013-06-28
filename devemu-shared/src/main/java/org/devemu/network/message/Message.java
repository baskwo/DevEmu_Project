package org.devemu.network.message;

public abstract class Message {
	public abstract void serialize();
	public abstract void deserialize();
	
	public String input = "";
	public String output = "";

	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
}
