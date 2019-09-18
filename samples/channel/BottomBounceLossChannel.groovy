import org.arl.unet.sim.channels.*
import org.arl.unet.sim.channels.UrickAcousticModel
import org.arl.unet.sim.Reception;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;



class BottomBounceLossChannel extends AbstractAcousticChannel{
  @Delegate UrickAcousticModel acoustics = new BottomBounceLossModel(this)
  @Delegate BPSKFadingModel comms = new BPSKFadingModel(this)

}


class BottomBounceLossModel extends UrickAcousticModel {

  	BottomBounceLossModel(AbstractAcousticChannel parent) {
    super(parent)
  }


   public double getRxPower(Reception rx){
       float freq = this.carrierFrequency;  
       float fsqr = freq*freq
       float factor = 0.11*(fsqr/(1+fsqr)) + 44*(fsqr/(4100 + fsqr)) + 2.75*0.0001*fsqr + 0.003
       float TL = this.spreading*10*Math.log10(100) + 100*factor

       float mfact = 1.95 
       float nfact = 0.86
       float theta = 35
       theta = Math.toRadians(theta)
       float sini = Math.sin(theta) 
       float cosi = Math.cos(theta)

       float intenRefl = mfact*sini - Math.sqrt(nfact*nfact - cosi*cosi)
       float intenInci = mfact*sini + Math.sqrt(nfact*nfact - cosi*cosi)

       float TL_bottom = Math.abs(2*10*Math.log10(intenRefl/intenInci))
                
       float AttenCoeff = 0.5*freq*1000

       TL = TL + AttenCoeff + TL_bottom
    
       double rl = (double)rx.getSourceLevel()-(double)TL;

       //println sprintf('%5.2f',TL)

       return Math.pow((double)10, rl / (double)10);
      }



}
     

      