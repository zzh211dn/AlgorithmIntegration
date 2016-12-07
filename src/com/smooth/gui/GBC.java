package com.smooth.gui;

import java.awt.GridBagConstraints;

public class GBC extends GridBagConstraints {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public GBC(int gridx, int gridy)
	{
		this.gridx = gridx;
		this.gridy = gridy;
	}

	public GBC setAnchor(int anchor)
	{
		this.anchor = anchor;
		return this;
	}
}
