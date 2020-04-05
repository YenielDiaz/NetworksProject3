package actualProject;

import java.net.DatagramPacket;

public class Frame {
	
	private int sequenceNumber;
	private DatagramPacket packet;
	private int ackNumber;
	
	public Frame(int sNumber, DatagramPacket p, int aNumber) {
		setSequenceNumber(sNumber);
		setPacket(p);
		setAckNumber(aNumber);
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public DatagramPacket getPacket() {
		return packet;
	}

	public void setPacket(DatagramPacket packet) {
		this.packet = packet;
	}

	public int getAckNumber() {
		return ackNumber;
	}

	public void setAckNumber(int ackNumber) {
		this.ackNumber = ackNumber;
	}
	

}
