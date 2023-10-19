import java.util.Arrays;
import ij.process.FloatProcessor;
import java.util.Random;
import java.awt.Polygon;


public class Project_model {
	public float [][][] model = new float [500][250][250];
	Random r = new Random();
	int []  crack_location = {r.nextInt(200)+150, 125, (int)(r.nextInt(50)/2)*2+201, (int)(r.nextInt(3)/2)*2+11, (int)(r.nextInt(20)/2)*2+121};
	float absorption_water = 0.2f * 0.005f ; //absoption * depth_pixel
	float absorption_bone = 0.31f * 0.005f ;
	
	public Project_model(){
		Polygon [] crack = new Polygon[crack_location[2]];
		for (int i = 0; i < crack_location[2]; i++){
			if (i < crack_location[2]/2){
				int [] x_points = {(int)(crack_location[1] - (crack_location[3] * i/crack_location[2])),
					(int) (crack_location[1] + (crack_location[3] * i/crack_location[2])), crack_location[1]};
				int [] y_points = {45 , 45 ,45 + crack_location[4]* i/crack_location[2]};
				crack[i] = new Polygon(x_points, y_points ,3);
			}
			if (i >= crack_location[2]/2){
				int [] x_points = {(int)(crack_location[1] - (crack_location[3] * (crack_location[2]-i)/crack_location[2])),
					(int) (crack_location[1] + (crack_location[3] * (crack_location[2]-i)/crack_location[2])), crack_location[1]};
				int [] y_points = {45 , 45, 45 + crack_location[4]* (crack_location[2]-i)/crack_location[2]};
				crack[i] = new Polygon(x_points, y_points ,3);
			}	
		}
		int counter = 0;
		for (float [][] ebene: model){
			for (float []row :ebene){
				Arrays.fill(row,  absorption_water); 
			}
			FloatProcessor slice = new FloatProcessor(ebene);
			slice.setColor(absorption_bone);
			slice.fillOval(45, 45 , 160, 160);
			if ((counter >= crack_location[0] - (int)(crack_location[2]/2)) && (counter < crack_location[0] + (int)(crack_location[2]/2))){
				slice.setColor(absorption_water);
				slice.fillPolygon(crack[counter - crack_location[0] + (int)(crack_location[2]/2)]);
			}
			ebene = slice.getFloatArray();
			model[counter] = ebene;

			counter++;
			
		}
	}
	
}
