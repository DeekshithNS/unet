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

        SurfaceReflectionModel(AbstractAcousticChannel parent) {
    super(parent)
  }


   public double getRxPower(Reception rx){
       float freq = this.carrierFrequency;  
       
       
       float fsqr = freq*freq
       float factor = 0.11*(fsqr/(1+fsqr)) + 44*(fsqr/(4100 + fsqr)) + 2.75*0.0001*fsqr + 0.003
       float TL = this.spreading*10*Math.log10(100) + 100*factor

       float alp = 100*26.6*freq/Math.sqrt((1452 + 3.5*22)*0.091)

       TL+=alp
    
       double rl = (double)rx.getSourceLevel()-(double)TL;

       //println sprintf('%5.2f',TL)

       return Math.pow((double)10, rl / (double)10);
      }



}
     

      