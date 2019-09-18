import org.arl.unet.sim.channels.*
import org.arl.unet.sim.channels.UrickAcousticModel
import org.arl.unet.sim.Reception;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import org.arl.unet.sim.Reception;
import org.codehaus.groovy.runtime.dgmimpl.NumberNumberDiv;
import org.codehaus.groovy.runtime.dgmimpl.NumberNumberMinus;
import org.codehaus.groovy.runtime.dgmimpl.NumberNumberPlus;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;



class Mchannel extends AbstractAcousticChannel{
  @Delegate UrickAcousticModel acoustics = new MyAcousticModel(this)
  @Delegate BPSKFadingModel comms = new BPSKFadingModel(this)




}


class MyAcousticModel extends UrickAcousticModel {

  	MyAcousticModel(AbstractAcousticChannel parent) {
    super(parent)
  }



public double getRxPower(Reception rx) {
        double r = rx.getRange();
        double depth = (double)this.waterDepth / (double)2;

        println sprintf('%5.2f',DefaultTypeTransformation.doubleUnbox($const$0))
        
        double fT = DefaultTypeTransformation.doubleUnbox($const$0) * Math.pow((double)10, (double)6 - (double)1520 / ((double)this.temperature + (double)273));
        double f2 = (double)this.carrierFrequency * (double)this.carrierFrequency * DefaultTypeTransformation.doubleUnbox($const$1);
        double a = DefaultTypeTransformation.doubleUnbox(NumberNumberDiv.div(NumberNumberPlus.plus(NumberNumberDiv.div(DefaultTypeTransformation.doubleUnbox($const$2) * (double)this.salinity * fT * f2, fT * fT + f2), NumberNumberDiv.div(DefaultTypeTransformation.doubleUnbox($const$3) * f2, fT)), $const$4));
        double TL = (double)10 * (double)this.spreading * Math.log10(r) - a * r / (double)1000;

              println sprintf('%5.2f',TL)

        double rl = (double)rx.getSourceLevel() - TL;
        return Math.pow((double)10, rl / (double)10);
    }

    }
