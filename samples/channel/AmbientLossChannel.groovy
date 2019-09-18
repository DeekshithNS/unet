import org.arl.unet.sim.channels.*
import org.arl.unet.sim.channels.UrickAcousticModel
import org.arl.unet.sim.Reception;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;



class AmbientLossChannel extends AbstractAcousticChannel{
  @Delegate UrickAcousticModel acoustics = new AmbientLossModel(this)
  @Delegate BPSKFadingModel comms = new BPSKFadingModel(this)

}


class AmbientLossModel extends UrickAcousticModel {

  	AmbientLossModel(AbstractAcousticChannel parent) {
    super(parent)
  }


   public double getRxPower(Reception rx){
       double freq = (float)this.carrierFrequency;  
       float Nt = Math.pow(10,(1.7 - (3*Math.log10(freq))));
        float Ns = Math.pow(10,(4 + 2.6*Math.log10(freq) - 6*Math.log10(freq+0.03)));
        float Nw = 5 + 2*Math.log10(freq) - 4*Math.log10(freq+0.4);
        float Nth = -1.5 + 2*Math.log10(freq);

        float TL = Nt + Ns + Nw + Nth;
    
       double rl = (double)rx.getSourceLevel()-(double)TL;

       //println sprintf('%5.2f',TL)

       return Math.pow((double)10, rl / (double)10);
      }



}
     

      