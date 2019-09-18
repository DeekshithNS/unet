import org.arl.unet.sim.channels.*
import org.arl.unet.sim.channels.UrickAcousticModel
import org.arl.unet.sim.Reception;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;



class SurfaceDuctLossChannel extends AbstractAcousticChannel{
  @Delegate UrickAcousticModel acoustics = new SurfaceDuctLossModel(this)
  @Delegate BPSKFadingModel comms = new BPSKFadingModel(this)

}


class SurfaceDuctLossModel extends UrickAcousticModel {

        SurfaceDuctLossModel(AbstractAcousticChannel parent) {
    super(parent)
  }


File fl = new File("/home/deekshith/unetsim-1.3/samples/rt/sd.txt")

   public double getRxPower(Reception rx){
       double freq = this.carrierFrequency;  
       double r = rx.getRange();
       double fsqr = freq*freq;
          double factor = 0.11*(fsqr/(1+fsqr)) + 44*(fsqr/(4100 + fsqr)) + 2.75*0.0001*fsqr + 0.003;
          double TL = 1.5*10*Math.log10(r) + r*factor;

          double alp = r*26.6*freq/Math.sqrt((1452 + 3.5*22)*0.091);


          double al=TL+alp;
    
       double rl = (double)rx.getSourceLevel()-(double)al;

       //println sprintf('%5.2f',TL)

       fl.append(","+al+",");
       double v = Math.pow((double)10, rl / (double)10);

       println ("power ="+v+"     "+rx.getSourceLevel()+"    "+al+"  "+al);
       return v;
      }

      

}
     

      