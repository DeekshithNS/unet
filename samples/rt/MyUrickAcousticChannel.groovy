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

  protected Logger log = Logger.getLogger(getClass().getName());

  @Override
  public double getRxPower(org.arl.unet.sim.Reception rx) {
    double v = super.getRxPower(rx);

    println sprintf ("power = "+v);

    log.info("getRxPower returned "+v);
    return v;
  }

}
     

      