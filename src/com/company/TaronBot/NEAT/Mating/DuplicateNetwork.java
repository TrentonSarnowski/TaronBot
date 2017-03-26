package com.company.TaronBot.NEAT.Mating;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.company.TaronBot.NEAT.Network.Network;

public class DuplicateNetwork {
	
	public static Network Duplicate(Network inputNetwork) throws OperationFailedException{
		return ByteStreamDuplicate(inputNetwork);
	}
	
	public static Network ByteStreamDuplicate(Network inputNetwork) throws OperationFailedException{
		
		if(inputNetwork == null){
			return null;
		}
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
		
			oos.writeObject(inputNetwork);
		
			byte[] byteData = bos.toByteArray();
			oos.flush();
			oos.close();
			bos.close();
			
			
			ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
			Network copy = (Network) new ObjectInputStream(bais).readObject();
			
			return copy;
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		throw new OperationFailedException("Copy failed to execute");
	}
}
