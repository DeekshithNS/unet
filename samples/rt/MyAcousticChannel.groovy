import org.arl.unet.sim.channels.*
import org.arl.unet.sim.channels.UrickAcousticModel
import org.arl.unet.sim.Reception;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;



class MyAcousticChannel extends AbstractAcousticChannel{
  @Delegate UrickAcousticModel acoustics = new MyAcousticModel(this)
  @Delegate BPSKFadingModel comms = new BPSKFadingModel(this)




}


class MyAcousticModel extends UrickAcousticModel {

  	MyAcousticModel(AbstractAcousticChannel parent) {
    super(parent)
  }

  	



 //	float this.frequency = channel.carrierFrequency;

  //double getRxPower(Reception rx) {
		//float freq = 24;
    //double getNoisePower()


   public double getRxPower(Reception rx){
  float freq = this.carrierFrequency;  
   float fsqr = freq*freq
             float factor = 0.11*(fsqr/(1+fsqr)) + 44*(fsqr/(4100 + fsqr)) + 2.75*0.0001*fsqr + 0.003
     float TL = 1.5*10*Math.log10(100) + 100*factor
     //    println "heloowwdxin scnlscn sclnslkcnsk skcnslnclsnc sxnsxnsklnxksl xanlsxksnxkn"
//
       double rl = (double)rx.getSourceLevel()-(double)TL;
//
        //double aaa=
//
      println sprintf('%5.2f',TL)

      //this.myprintinclass();

        return Math.pow((double)10, rl / (double)10);
         // return TL
       


       }



}
     

      