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


File fl = new File("/home/deekshith/unetsim-1.3/samples/rt/al.txt")


   public double getRxPower(Reception rx){
       double freq = this.carrierFrequency;  

       float Nt = Math.pow(10,(1.7 - (3*Math.log10(freq))));
       float Ns = Math.pow(10,(4 + 2.6*Math.log10(freq) - 6*Math.log10(freq+0.03)));
       float Nw = 5 + 2*Math.log10(freq) - 4*Math.log10(freq+0.4);
       float Nth = -1.5 + 2*Math.log10(freq);

       double aTL = (double)Nt + (double)Ns + (double)Nw + (double)Nth;
    
       double rl = (double)rx.getSourceLevel()-(double)aTL;

       //println sprintf('%5.2f',TL)
                     fl.append(" "+aTL+" ");

       double v = Math.pow((double)10, rl / (double)10);

       println sprintf("power ="+v+"     "+rx.getSourceLevel()+"    "+aTL);
       
        double vvv=(Math.log(v)*10)/(Math.log10);
        
        println sprintf("after log "+vvv);
        return v;

      }



}
     

      