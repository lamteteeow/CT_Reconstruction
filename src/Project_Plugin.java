import java.util.Arrays;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import java.util.Random;
/*
Name: Thanh Lam, Tran
Gruppe: RUEB
*/

public class Project_Plugin implements PlugIn {
	
	public float[] projection(float[][] dataSlice) {
		if (dataSlice.length == 0) {
			System.err.print("Invalid Model");
			System.exit(0);
		}
		float[] strahlungintensitaet = new float[dataSlice.length];
		for (int i = 0; i < strahlungintensitaet.length; i++) {
			strahlungintensitaet[i] = 100;
		}
		for (int x = 0; x < dataSlice[0].length; x++) {
			for (int y = 0; y < dataSlice.length; y++) {
				strahlungintensitaet[x] *= (float) Math.exp(-absorption_water);
			}
		}
		return strahlungintensitaet;
	}
	
	
    public void run(String arg0){
    	Project_model bone = new Project_model();
    	float[][] dim1_Projection = new float[bone.model.length][bone.model[0].length];
    	for (int dim1 = 0; dim1 < dim1_Projection.length; dim1++) {
    		dim1_Projection[dim1] = projection(bone.model.[dim1]);
    	}
    	ImageProcessor projection = new FloatProcessor(dim1_Projection);
    	ImagePlus image = new ImageJ("Projection of model on the 1st dimension", projection);
    	image.show;
    }

	public static void main(String[] args) {
		Class<?> clazz = Project_Plugin.class;
		String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString().replace("%20", " ");
		String pluginsDir = url.substring("file:".length(),
				url.length() - clazz.getName().length() - ".class".length());
		System.setProperty("plugins.dir", pluginsDir);

		// start ImageJ
		new ij.ImageJ();
	}
}
	