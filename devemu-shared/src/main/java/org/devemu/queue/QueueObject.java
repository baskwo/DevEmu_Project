package org.devemu.queue;

public class QueueObject {
	public String annot;
	public Class<? extends QueueListener> classe;
	
	public QueueObject(String annot, Class<? extends QueueListener> classe) {
		this.annot = annot;
		this.classe = classe;
	}
}
