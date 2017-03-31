package com.company.TaronBot.Network;

import java.io.Serializable;

/**
 * simple interface for passing functions through the Networks.
 * 
 * @author deef0000dragon1
 *
 */
public interface nonLinearFunction extends Serializable {
	double operation(double input);

}
