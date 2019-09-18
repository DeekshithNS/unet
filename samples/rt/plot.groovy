import org.arl.unet.utils.GPlot
import java.awt.Color
import java.lang.Math

// read trace.nam to get simulation results
x = []
y = []
new File("/home/deekshith/unetsim-1.3/samples/rt/fl.txt").eachLine { s ->
  String[] arr = s.split(" ")
  x << Float.valueOf(arr[0])
  y << Float.valueOf(arr[1])
}

println (x[2]);

new GPlot('Loss model 1', 800, 600).with {
 xlabel('Frequency')
  ylabel('Transmission Loss')

plot('Simulation', x as double[], y as double[], Color.red, true, false)

drawnow()}