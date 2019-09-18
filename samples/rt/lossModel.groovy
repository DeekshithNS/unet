//! Simulation: Acoustic Transmission loss due to Absorption and Spreading
///////////////////////////////////////////////////////////////////////////////
///
/// To run simulation:
///   bin/unet samples/absorptionSpreadLoss/loseModel
///
/// Output trace file: model.txt
/// Plot results: bin/unet samples/absorptionSpreadLoss/plot-lossModel
///
///////////////////////////////////////////////////////////////////////////////

import org.arl.fjage.*
import org.arl.unet.*
import org.arl.unet.phy.*
import org.arl.unet.bb.*
import org.arl.unet.sim.*
import org.arl.unet.sim.channels.*
import static org.arl.unet.Services.*
import static org.arl.unet.phy.Physical.*

import java.lang.Math.*

channel = [
  model: BasicAcousticChannel,
  bandwidth:            4096.Hz,
  spreading:            1.5,
  temperature:          15.C,
  salinity:             35.ppt,
  noiseLevel:           60.dB,
  waterDepth:           20.m
]

println '''
============================
Simulation for Acoustic Transmission loss due to Absorption and Spreading

Freq(kHz)\tDist\tTL
============================

'''


fl = new File("/home/deekshith/unetsim-1.3/samples/absorptionSpreadLoss/model.txt")

for(int f=1000;f<10000;f+=1000)
{
  simulate 15.seconds, {
      def n = []
      n << node('1', address: 1, location: [0,0,0])
      n << node('2', address: 2, location: [0,0,0])
      n.eachWithIndex { n2, i ->
        n2.startup = {
          def phy = agentForService PHYSICAL
          def node = agentForService NODE_INFO
          subscribe phy

          if(node.address == 1)
          { // do it only if this is node 2
            add new TickerBehavior(5000, { //every 5 seconds
              def dat = f.toString()
              phy << new TxFrameReq(to: 2, type: DATA, data: dat.bytes)
            })
          }
          if(node.address == 2)
          { // do it only if this is node 1
            add new TickerBehavior(5000, { //every 5 minutes
              def rxNtf = receive({ it instanceof RxFrameNtf }, 1000)
              if(rxNtf){
                def rec = new String(rxNtf.data)
                String[] arr = rec.split(',')
                float freq = Float.valueOf(arr[0])/1000

                float fsqr = freq*freq
                float factor = 0.11*(fsqr/(1+fsqr)) + 44*(fsqr/(4100 + fsqr)) + 2.75*0.0001*fsqr + 0.003
                float TL = channel.spreading*10*Math.log10(100) + 100*factor

                fl.append(freq+","+TL+"\n")

                println sprintf('%5.2f\t%s\t%5.2f', freq, "100km",TL)


                
              }
            })
          }
        }// simulate


      }
    }



}
