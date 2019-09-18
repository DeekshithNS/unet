import org.arl.fjage.Message
import org.arl.unet.*
import org.arl.unet.phy.*
import org.arl.fjage.*
import static org.arl.unet.Services.*
import static org.arl.unet.phy.Physical.*
import org.arl.unet.sim.*

class LossModel extends UnetAgent {

  
    class LossModelCalculation{

    private float frequency

    public LossModelCalculation(String freq)
    {
      this.frequency = Float.valueOf(freq)
    }

    public float absorptionSpreadLoss()
    {
      float freq = this.frequency
      float fsqr = freq*freq
          float factor = 0.11*(fsqr/(1+fsqr)) + 44*(fsqr/(4100 + fsqr)) + 2.75*0.0001*fsqr + 0.003
          float TL = 1.5*10*Math.log10(100) + 100*factor
          return TL
    }

    public float surfaceReflection()
    {
      float freq = this.frequency
      float fsqr = freq*freq
          float factor = 0.11*(fsqr/(1+fsqr)) + 44*(fsqr/(4100 + fsqr)) + 2.75*0.0001*fsqr + 0.003
          float TL = 1.5*10*Math.log10(100) + 100*factor

          float windspd = 1
          float freq2 = 378/(windspd * windspd)
          float freq1 = Math.sqrt(10) * freq2
          float theta = 50

          float f1sqr = (freq/freq1)*(freq/freq1)
          float f2sqr = (freq/freq2)*(freq/freq2)

          float TL_SR = 10 * Math.log10((1+f1sqr)/(1+f2sqr)) - (1 + (90-windspd)/60)*(theta/30)*(theta/30)

          TL = TL + TL_SR

          return TL
    }

    public float surfaceDuct()
    {
      float freq = this.frequency
      float fsqr = freq*freq
          float factor = 0.11*(fsqr/(1+fsqr)) + 44*(fsqr/(4100 + fsqr)) + 2.75*0.0001*fsqr + 0.003
          float TL = 1.5*10*Math.log10(100) + 100*factor

          float alp = 100*26.6*freq/Math.sqrt((1452 + 3.5*22)*0.091)

          TL+=alp

          return TL
    }

    public float bottomBounce()
    {
      float freq =  this.frequency
      float fsqr = freq*freq
          float factor = 0.11*(fsqr/(1+fsqr)) + 44*(fsqr/(4100 + fsqr)) + 2.75*0.0001*fsqr + 0.003
          float TL = 1.5*10*Math.log10(100) + 100*factor

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

          return TL
    }

    public float ambientNoise()
    {
      float freq = this.frequency

      float Nt = Math.pow(10,(1.7 - (3*Math.log10(freq))))
        float Ns = Math.pow(10,(4 + 2.6*Math.log10(freq) - 6*Math.log10(freq+0.03)))
        float Nw = 5 + 2*Math.log10(freq) - 4*Math.log10(freq+0.4)
        float Nth = -1.5 + 2*Math.log10(freq)

        float TL = Nt + Ns + Nw + Nth

        return TL
    }

  } 
  private AgentID node
  private AgentID phy
  public int addr

  void startup() {
    phy = agentForService Services.PHYSICAL
    subscribe topic(phy)
    node = agentForService Services.NODE_INFO
    addr = node.address    
  }

  void processMessage(Message msg) {   
    if(addr == 2 && msg instanceof RxFrameNtf)
    {
      System.out.println "\n_______________________________________________________\n"    

      String rec = ""
      
      for(int ss: msg.data)
      rec = rec + (char)ss

      System.out.println "The Frequency is:" + rec

      LossModelCalculation l = new LossModelCalculation(rec)

      float absorptionSpreadLoss = l.absorptionSpreadLoss()
      float surfaceReflection = l.surfaceReflection()
      float surfaceDuct = l.surfaceDuct()
      float bottomBounce = l.bottomBounce()
      float ambientNoise = l.ambientNoise()

      System.out.println "\n------LOSS MODELS-----\n"

      System.out.println "\nSpread and Absorption Loss : " + absorptionSpreadLoss
      System.out.println "\nSurface Reflection : " + surfaceReflection
      System.out.println "\nSurface Duct : " + surfaceDuct
      System.out.println "\nBottom Bounce : " + bottomBounce
      System.out.println "\nAmbient Noise : " + ambientNoise

      
    }
  }
}
