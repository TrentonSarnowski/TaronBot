package com.company.TaronBot.NEAT.Mating;

public class AlterNetworkHelper {
	private double numberOfNewNodesPercentage;
	private int minimumNumberOfNewNodes;
	private int maximumNumberOfNewNodes;
	
	private double chanceToChangeCurrentInputLinkage;
	private double chanceToAddLinkagePerNode;
	private double chanceToRemoveLinkagePerNode;
	
	private double chanceToReplaceLinkageWithNewNode;
	
	private double chanceToChangeMutatorValue;
	private double maximumChangeToMutator;
	private double maximumIncreaseInMutator;
	private double maximumDecreaseInMutator;
	
	private double chanceToReRandomizeMutatorValue;

	
	
	public AlterNetworkHelper(){
		numberOfNewNodesPercentage = 0.01;
		minimumNumberOfNewNodes = 0;
		maximumNumberOfNewNodes = 100;
		
		chanceToChangeCurrentInputLinkage = 0.01;
		chanceToAddLinkagePerNode = 0.01;
		chanceToRemoveLinkagePerNode = 0.01;
		
		chanceToReplaceLinkageWithNewNode = 0.001;
		
		chanceToChangeMutatorValue = 0.01;
		maximumChangeToMutator = 0.1;
		maximumIncreaseInMutator = 0.1;
		maximumDecreaseInMutator = 0.1;
		
		chanceToReRandomizeMutatorValue = 0.003;
	}
	
	
	public double getNumberOfNewNodesPercentage() {
		return numberOfNewNodesPercentage;
	}

	public int getMinimumNumberOfNewNodes() {
		return minimumNumberOfNewNodes;
	}

	public int getMaximumNumberOfNewNodes() {
		return maximumNumberOfNewNodes;
	}

	public double getChanceToChangeCurrentInputLinkage() {
		return chanceToChangeCurrentInputLinkage;
	}

	public double getChanceToAddLinkagePerNode() {
		return chanceToAddLinkagePerNode;
	}

	public double getChanceToRemoveLinkagePerNode() {
		return chanceToRemoveLinkagePerNode;
	}

	public double getChanceToReplaceLinkageWithNewNode() {
		return chanceToReplaceLinkageWithNewNode;
	}

	public double getChanceToChangeMutatorValue() {
		return chanceToChangeMutatorValue;
	}

	public double getMaximumChangeToMutator() {
		return maximumChangeToMutator;
	}

	public double getMaximumIncreaseInMutator() {
		return maximumIncreaseInMutator;
	}

	public double getMaximumDecreaseInMutator() {
		return maximumDecreaseInMutator;
	}

	public double getChanceToReRandomizeMutatorValue() {
		return chanceToReRandomizeMutatorValue;
	}

	
	
	public void setNumberOfNewNodesPercentage(double numberOfNewNodesPercentage) {
		if((numberOfNewNodesPercentage <= 1 && numberOfNewNodesPercentage >= 0)){
			this.numberOfNewNodesPercentage = numberOfNewNodesPercentage;
		}
	}

	public void setMinimumNumberOfNewNodes(int minimumNumberOfNewNodes) {
		if(minimumNumberOfNewNodes > 0 && minimumNumberOfNewNodes < maximumNumberOfNewNodes){
			this.minimumNumberOfNewNodes = minimumNumberOfNewNodes;
		}
	}

	public void setMaximumNumberOfNewNodes(int maximumNumberOfNewNodes) {
		if(maximumNumberOfNewNodes > minimumNumberOfNewNodes){
			this.maximumNumberOfNewNodes = maximumNumberOfNewNodes;
		}
	}

	public void setChanceToChangeCurrentInputLinkage(double chanceToChangeCurrentInputLinkage) {
		if(chanceToChangeCurrentInputLinkage <= 1 && chanceToChangeCurrentInputLinkage >= 0){
			this.chanceToChangeCurrentInputLinkage = chanceToChangeCurrentInputLinkage;
		}
	}

	public void setChanceToAddLinkagePerNode(double chanceToAddLinkagePerNode) {
		if(chanceToAddLinkagePerNode <= 1 && chanceToAddLinkagePerNode >= 0){
			this.chanceToAddLinkagePerNode = chanceToAddLinkagePerNode;
		}	
	}

	public void setChanceToRemoveLinkagePerNode(double chanceToRemoveLinkagePerNode) {
		if(chanceToRemoveLinkagePerNode <= 1 && chanceToRemoveLinkagePerNode >= 0){
			this.chanceToRemoveLinkagePerNode = chanceToRemoveLinkagePerNode;
		}		
	}

	public void setChanceToReplaceLinkageWithNewNode(double chanceToReplaceLinkageWithNewNode) {
		if(chanceToReplaceLinkageWithNewNode <= 1 && chanceToReplaceLinkageWithNewNode >= 0){
			this.chanceToReplaceLinkageWithNewNode = chanceToReplaceLinkageWithNewNode;
		}		
	}

	public void setChanceToChangeMutatorValue(double chanceToChangeMutatorValue) {
		if(chanceToChangeMutatorValue <= 1 && chanceToChangeMutatorValue >= 0){
			this.chanceToChangeMutatorValue = chanceToChangeMutatorValue;
		}		
	}

	//overrides increase and decrease in mutators. 
	public void setMaximumChangeToMutator(double maximumChangeToMutator) {
		if(maximumChangeToMutator <= 1 && maximumChangeToMutator >= 0){
			this.maximumChangeToMutator = maximumChangeToMutator;
			setMaximumIncreaseInMutator(maximumChangeToMutator);
			setMaximumDecreaseInMutator(maximumChangeToMutator);
		}			
	}

	public void setMaximumIncreaseInMutator(double maximumIncreaseInMutator) {
		if(maximumIncreaseInMutator <= 1 && maximumIncreaseInMutator >= 0){
			this.maximumIncreaseInMutator = maximumIncreaseInMutator;
		}		
	}

	public void setMaximumDecreaseInMutator(double maximumDecreaseInMutator) {
		if(maximumDecreaseInMutator <= 1 && maximumDecreaseInMutator >= 0){
			this.maximumDecreaseInMutator = maximumDecreaseInMutator;
		}
	}

	public void setChanceToReRandomizeMutatorValue(double chanceToReRandomizeMutatorValue) {
		if(chanceToReRandomizeMutatorValue <= 1 && chanceToReRandomizeMutatorValue >= 0){
			this.chanceToReRandomizeMutatorValue = chanceToReRandomizeMutatorValue;
		}			
	}
	
	
	
	
}
