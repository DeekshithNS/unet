import org.arl.unet.sim.channels.*
import org.arl.unet.sim.channels.UrickAcousticModel
import org.arl.unet.sim.Reception;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;



class AbsorptionSpreadLossChannel extends AbstractAcousticChannel{
  @Delegate UrickAcousticModel acoustics = new AbsorptionSpreadLossModel(this)
  @Delegate BPSKFadingModel comms = new BPSKFadingModel(this)

}


class AbsorptionSpreadLossModel extends UrickAcousticModel {

  	AbsorptionSpreadLossModel(AbstractAcousticChannel parent) {
    super(parent)
  }


   public double getRxPower(Reception rx){
       double freq = this.carrierFrequency;  
       
       double fsqr = freq*freq;
       double factor = 0.11*(fsqr/(1+fsqr)) + (44*(fsqr/(4100 + fsqr)) + 2.75*0.0001*fsqr + 0.003;
       double TL = 1.5*10*Math.log10(100) + 100*factor;
    
       double rl = (double)rx.getSourceLevel()-(double)TL;

       //println sprintf('%5.2f',TL)

       return Math.pow((double)10, rl / (double)10);
      }



}
     

      