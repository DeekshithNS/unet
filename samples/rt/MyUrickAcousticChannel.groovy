import org.arl.unet.sim.channels.*
import org.arl.unet.sim.channels.UrickAcousticModel
import org.arl.unet.sim.Reception;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import org.arl.unet.sim.*
import java.util.logging.Logger;



class MyUrickAcousticChannel extends AbstractAcousticChannel{
  @Delegate UrickAcousticModel acoustics = new MyUrickAcousticModel(this)
  @Delegate BPSKFadingModel comms = new BPSKFadingModel(this)

}

public class MyUrickAcousticModel extends org.arl.unet.sim.channels.UrickAcousticModel {


  MyUrickAcousticModel(AbstractAcousticChannel parent) {
    super(parent)
  }

  File fl = new File("/home/deekshith/unetsim-1.3/samples/rt/urick.txt")


  protected Logger log = Logger.getLogger(getClass().getName());

  @Override
  public double getRxPower(org.arl.unet.sim.Reception rx) {
    double v = super.getRxPower(rx);

    println sprintf ("power = "+v);


    double vvv=(Math.log(v)*10)/(Math.log(10));
        double vava=175-vvv;        
        println("after log "+vvv+"loss =  "+vava+"    ");


          fl.append(","+vava+",")

    log.info("getRxPower returned "+v);
    return v;
  }

}
     

      