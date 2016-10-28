package com.company.TaronBot.network;

public class NetworkLayer {
	int previousLayerWidth, previousLayerHeight, outputWidth, outputHeight;
	nonLinearFunction function;
	
	
	public NetworkLayer(int previousLayerWidth, int previousLayerHeight, int outputWidth, int outputHeight, nonLinearFunction function){
		this.previousLayerHeight = previousLayerHeight;
		this.previousLayerWidth = previousLayerWidth;
		this.outputHeight = outputHeight;
		this.outputWidth = outputWidth;
		
		this.function = function;
	}
	
}
