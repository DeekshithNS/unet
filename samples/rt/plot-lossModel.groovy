import org.arl.unet.utils.GPlot
import java.awt.Color
import java.lang.Math

// read trace.nam to get simulation results
x = []
y = []
new File("/home/deekshith/unetsim-1.3/samples/absorptionSpreadLoss/model.txt").eachLine { s ->
  String[] arr = s.split(',')
  x << Float.valueOf(arr[0])
  y << Float.valueOf(arr[1])
}

// plot the results against theory
new GPlot('Loss model 1', 800, 600).with {
  xlabel('Frequency')
  ylabel('Transmission Loss')
  def xrange = range(0, 10.0, 0.001)

  plot('Model 1 continuous curve', xrange, { x -> 1.5*10*Math.log10(100) + 100*(0.11*(x*x/(1+x*x)) + 44*(x*x/(4100 + x*x)) + 2.75*0.0001*x*x + 0.003) }, Color.blue)
  plot('Model 1 simulation', x as double[], y as double[], Color.red, true, false)
  drawnow()
}
