package com.company.TaronBot.Network;

import java.util.ArrayList;
import java.util.Random;

import tech.deef.Tools.Tools;

public class NetworkLayer {
	int[] previousLayerDimensions, outputLayerDimensions;
	nonLinearFunction function;

	double[][][][] inputLayer, outputLayer;
	ArrayList<ArrayList<ArrayList<ArrayList<double[][][][]>>>> mutators;

	public NetworkLayer(int[] previousLayerDimensions, int[] outputLayerDimensions, nonLinearFunction function) {

		this.previousLayerDimensions = previousLayerDimensions;
		this.outputLayerDimensions = outputLayerDimensions;
		this.function = function;

		// TODO error handeling for less than 4 dimensions

		inputLayer = new double[previousLayerDimensions[0]][previousLayerDimensions[1]][previousLayerDimensions[2]][previousLayerDimensions[3]];
		outputLayer = new double[outputLayerDimensions[0]][outputLayerDimensions[1]][outputLayerDimensions[2]][outputLayerDimensions[3]];

		// the problem here is that the program is not creating the correct
		// series of things to add.

		mutators = new ArrayList<ArrayList<ArrayList<ArrayList<double[][][][]>>>>(outputLayerDimensions[0]);

		
		for (int j = 0; j < outputLayerDimensions[0]; j++) {

			ArrayList<ArrayList<ArrayList<double[][][][]>>> m1 = new ArrayList<ArrayList<ArrayList<double[][][][]>>>(outputLayerDimensions[1]);
			for (int k = 0; k < outputLayerDimensions[1]; k++) {
				ArrayList<ArrayList<double[][][][]>> m2 = new ArrayList<ArrayList<double[][][][]>>(outputLayerDimensions[2]);
				for (int l = 0; l < outputLayerDimensions[2]; l++) {
					ArrayList<double[][][][]> m3 = new ArrayList<double[][][][]>(outputLayerDimensions[3]);
					for (int m = 0; m < outputLayerDimensions[3]; m++) {
						double[][][][] d = new double[previousLayerDimensions[0]][previousLayerDimensions[1]][previousLayerDimensions[2]][previousLayerDimensions[3]];
						m3.add(d);
					}
					m2.add(m3);	
				}
				m1.add(m2);
			}
			mutators.add(m1);
		}

	}

	/**
	 * 
	 * @param input
	 *            double[][][][] the input values from the last layer.
	 * @return
	 */
	public double[][][][] calculate(double[][][][] input) {
		// TODO error checking to confirm that the input is the right size.
		double[][][][] output = new double[outputLayerDimensions[0]][outputLayerDimensions[1]][outputLayerDimensions[2]][outputLayerDimensions[3]];

		for (int i = 0; i < outputLayerDimensions[0]; i++) {
			for (int j = 0; j < outputLayerDimensions[1]; j++) {
				for (int k = 0; k < outputLayerDimensions[2]; k++) {
					for (int l = 0; l < outputLayerDimensions[3]; l++) {

						output[i][j][k][l] = function.operation(corolate(mutators.get(i).get(j).get(k).get(l), input));
					}
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * @param weights
	 *            double [][][][] that holds the weighting numbers to be applied
	 *            to the inputs
	 * @param input
	 *            double[][][][] that holds the input values from the previous
	 *            layer
	 * @return the sum for a specific location (handled externally) based on the
	 *         weights and the inputs.
	 */
	private double corolate(double[][][][] weights, double[][][][] input) {

		double sum = 0;

		for (int i = 0; i < previousLayerDimensions[0]; i++) {
			for (int j = 0; j < previousLayerDimensions[1]; j++) {
				for (int k = 0; k < previousLayerDimensions[2]; k++) {
					for (int l = 0; l < previousLayerDimensions[3]; l++) {

						sum += weights[i][j][k][l] * input[i][j][k][l];
					}
				}
			}
		}

		return sum;
	}

	// randomizes fully the weights of the layer
	public void randomize(Random rand) {
		// TODO Auto-generated method stub

		for (int i = 0; i < outputLayerDimensions[0]; i++) {
			for (int j = 0; j < outputLayerDimensions[1]; j++) {
				for (int k = 0; k < outputLayerDimensions[2]; k++) {
					for (int l = 0; l < outputLayerDimensions[3]; l++) {
						//Tools.PrintColor(("calculating mutator for input: " + i + ":" + j + ":" + k + ":" + l + "\n"), "green");
						for (int m = 0; m < previousLayerDimensions[0]; m++) {
							for (int n = 0; n < previousLayerDimensions[1]; n++) {
								for (int o = 0; o < previousLayerDimensions[2]; o++) {
									for (int p = 0; p < previousLayerDimensions[3]; p++) {
										mutators.get(i).get(j).get(k).get(l)[m][n][o][p] = rand.nextDouble() * 2 - 1;
									}
								}
							}
						}

					}
				}
			}
		}

	}

	public void mutate(Random rand, double changePrecentage) {
		// TODO Auto-generated method stub

		for (int i = 0; i < outputLayerDimensions[0]; i++) {
			for (int j = 0; j < outputLayerDimensions[1]; j++) {
				for (int k = 0; k < outputLayerDimensions[2]; k++) {
					for (int l = 0; l < outputLayerDimensions[3]; l++) {

						for (int m = 0; m < previousLayerDimensions[0]; m++) {
							for (int n = 0; n < previousLayerDimensions[1]; n++) {
								for (int o = 0; o < previousLayerDimensions[2]; o++) {
									for (int p = 0; p < previousLayerDimensions[3]; p++) {

										if (rand.nextDouble() <= changePrecentage) {
											mutators.get(i).get(j).get(k).get(l)[m][n][o][p] = rand.nextDouble() * 2
													- 1;
										}
									}
								}
							}
						}

					}
				}
			}
		}

	}
}
