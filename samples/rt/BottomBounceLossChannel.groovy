import org.arl.fjage.*
import org.arl.unet.*
import org.arl.unet.phy.*
import org.arl.unet.bb.*
import org.arl.unet.sim.*
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

File fl = new File("/home/deekshith/unetsim-1.3/samples/rt/bb.txt")


   public double getRxPower(Reception rx){
       float freq = this.carrierFrequency;  
       float r = rx.getRange();
       float fsqr = freq*freq;
        float factor = 0.11*(fsqr/(1+fsqr)) + 44*(fsqr/(4100 + fsqr)) + 2.75*0.0001*fsqr + 0.003;
        float TL = this.spreading*10*Math.log10(r) + r*factor;

                float mfact = 1.95; 
                float nfact = 0.86;
                float theta = 35;
                theta = Math.toRadians(theta);
                float sini = Math.sin(theta); 
                float cosi = Math.cos(theta);

                float intenRefl = mfact*sini - Math.sqrt(nfact*nfact - cosi*cosi);
                float intenInci = mfact*sini + Math.sqrt(nfact*nfact - cosi*cosi);

                float aL_bottom = Math.abs(2*10*Math.log10(intenRefl/intenInci));
                
                float attenCoeff = 0.5*freq*r;

                //TL = TL + AttenCoeff + TL_bottom
          double aL = (double)TL + (double)attenCoeff + (double)aL_bottom;

       double rl = (double)rx.getSourceLevel()-(double)aL;

       //println sprintf('%al2f',TL)
              fl.append(","+aL+",");

       double v = Math.pow((double)10, rl / (double)10);

       println sprintf("power ="+v+"     "+rx.getSourceLevel()+"    "+TL);
       return v;
      }



}
     

      