//! Simulation: Implementation of Rate Error Model
///////////////////////////////////////////////////////////////////////////////
///
/// 
/// 
///
/// Output trace file: fl.txt
///
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
import java.util.Random;

import java.lang.Math.*

channel = [
  //model: SurfaceDuctLossChannel,
 //model: SurfaceReflectionLossChannel,
  //model: AbsorptionSpreadLossChannel,
  //model: BottomBounceLossChannel,
  //model: AmbientLossChannel,
  model:MyUrickAcousticChannel,
  bandwidth:            5020.Hz,
  spreading:            1.5,
  temperature:          15.C,
  salinity:             35.ppt,
  noiseLevel:           60.dB,
  waterDepth:           20.m,
  //carrierFrequency:     2.Hz,
]

println '''
============================
Simulation bit rate error for 10 bit input
'''
Random rand = new Random();

File fl = new File("/home/deekshith/unetsim-1.3/samples/rt/urick.txt")

float error = 0;
float count = 0;
float rateErrorpercentage = 0;
for(int f=1;f<=10;f+=1)
{ 
  channel.carrierFrequency = f.kHz

  //for(int f2=1;f2<=10;f2+=1){
  fl.append(f+","+(0+(f*10)))
  if(f!=11){
  simulate 8.seconds, {
      def n = []
      n << node('1', address: 1, location: [0,0,0])
      n << node('2', address: 2, location: [100,0,0])
      //n << node('2', address: 2, location: [20,0,0])
      n.eachWithIndex { n2, i ->
        n2.startup = {
          def phy = agentForService PHYSICAL
          def node = agentForService NODE_INFO
          subscribe phy

          if(node.address == 1)
          { // do it only if this is node 2
            add new TickerBehavior(5000, { //every 5 seconds
              String dat = rand.nextInt(2)
              phy << new TxFrameReq(to: 2, type: DATA, data: dat.bytes)
           
            })
          }
          if(node.address == 2)
          { // do it only if this is node 1
            
            add new TickerBehavior(5000, { //every 5 minutes
              def rxNtf = receive({ it instanceof RxFrameNtf }, 1000)
              
              if(rxNtf){
                count++;
                
                def rec = new String(rxNtf.data)
                
                int temp = rand.nextInt(2);
                if(temp){
                  if(rec == '1')
                  data = 0;else data = 1;
                  
                  error++;
                }
                else{if(rec == '0')
                  data = 0;else data = 1;
              }  
                
                rateErrorpercentage = (error/count)*100;
                
                }
            })
          }
        }// simulate
      } 
    }println sprintf('Bit Rate error percentage for trial %5.2f :%5.2f\n',count, rateErrorpercentage)
  }else println sprintf('Overall Bit Rate error percentage:%5.2f\n', rateErrorpercentage)
    fl.append("\n")
  }


