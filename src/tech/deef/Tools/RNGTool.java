package tech.deef.Tools;

public class RNGTool {
	public static int RNGInput(int in){
		int input = in & 0xFFFF;
		
		if(input == 0x560a){
			input = 0;
		}
		
		int s0 = ((input & 0xff) << 8)& 0xFFFF;
		
		s0 = (s0 ^ input)& 0xFFFF;
		
		input =( ((s0 & 0xff) << 8) | ((s0 & 0xff00) >>8))& 0xFFFF;
		
		s0 = (((0xff&s0) << 1) ^ input)& 0xFFFF;
		int s1 = ((s0>>1) ^ 0xff80)& 0xFFFF;
		
		
		if((s0 &1) == 0){
			if(s1 == 0xaa55){
				input = 0;	
			}else{
				input = (s1 ^ 0x1ff4)& 0xFFFF;
			}
		}else{
			input = (s1 ^ 0x8180)& 0xFFFF;
		}
		

		return (input)& 0xFFFF;
	}
}
