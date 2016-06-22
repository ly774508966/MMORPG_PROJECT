package fr.technicalgames.math;

public class Mathf {
	
	public static final float PI = 3.14159265358979323846f;

	public static float linearInterpolate(float y1,float y2,float b){
		return y1 * (1 - b) + y2 * b;
	}
	
	public static float cos(float angle){
		return (float)Math.cos(angle);
	}
	
	public static float cosineInterpolate(float y1,float y2,float b){
		float ft = b * PI;
		float f = (1 - cos(ft)) * .5f;
		return y1 * (1-f) + y2*f;
	}
	
}
