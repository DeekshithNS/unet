import org.arl.unet.sim.channels.*
import org.arl.unet.sim.channels.UrickAcousticModel
import org.arl.unet.sim.Reception;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;



class SurfaceReflectionLossChannel extends AbstractAcousticChannel{
  @Delegate UrickAcousticModel acoustics = new SurfaceReflectionLossModel(this)
  @Delegate BPSKFadingModel comms = new BPSKFadingModel(this)

}


class SurfaceReflectionLossModel extends UrickAcousticModel {

        SurfaceReflectionLossModel(AbstractAcousticChannel parent) {
    super(parent)
  }

File fl = new File("/home/deekshith/unetsim-1.3/samples/rt/sr.txt")

   public double getRxPower(Reception rx){
       double freq = this.carrierFrequency;  
       double r = rx.getRange();
       //float freq = this.frequency
      float fsqr = freq*freq
          float factor = 0.11*(fsqr/(1+fsqr)) + 44*(fsqr/(4100 + fsqr)) + 2.75*0.0001*fsqr + 0.003
          float TL = 1.5*10*Math.log10(r) + r*factor

          float windspd = 1
          float freq2 = 378/(windspd * windspd)
          float freq1 = Math.sqrt(10) * freq2
          float theta = 50

          float f1sqr = (freq/freq1)*(freq/freq1)
          float f2sqr = (freq/freq2)*(freq/freq2)

          float TL_SR = 10 * Math.log10((1+f1sqr)/(1+f2sqr)) - (1 + (90-windspd)/60)*(theta/30)*(theta/30)

          TL = TL + TL_SR;

       double rl = (double)rx.getSourceLevel()-(double)TL;
       fl.append(","+TL+",");

       println sprintf('%5.2f',TL)

       double v = Math.pow((double)10, rl / (double)10);
      
       return v;
      }



}
     

      