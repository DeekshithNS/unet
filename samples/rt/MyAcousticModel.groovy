import org.arl.unet.sim.channels.UrickAcousticModel

  class MyAcousticModel extends UrickAcousticModel() {

  	MyAcousticModel(AbstractAcousticChannel parent) {
    super(parent)
  }

  	
 // map of sea state to noise power (dB re uPa^2/Hz)
  private final def noiseLevel = [ 0: 20, 1: 30, 2: 35, 3: 40, 4: 42, 5: 44, 6: 46 ]

  // sea state parameter
  float seaState = 2

  double getNoisePower() {
    return Math.pow(10, noiseLevel[seaState]/10) * model.bandwidth
  }
}